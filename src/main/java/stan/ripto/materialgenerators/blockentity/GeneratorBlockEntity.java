package stan.ripto.materialgenerators.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.nbt.NbtKeys;
import stan.ripto.materialgenerators.util.GenerationData;

import java.util.Optional;

public class GeneratorBlockEntity extends BlockEntity {
    private final GenerationData data = new GenerationData(ItemStack.EMPTY, 1);
    private int generateTime = 0;
    private int coolTime = 6000;
    private int coolTimeCopy = this.coolTime;

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return data.item().is(stack.getItem());
        }
    };

    private final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> this.inventory);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optional.invalidate();
    }

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(MaterialGeneratorsBlockEntities.GENERATOR.get(), pos, state);
    }

    public String getGenerateItemName() {
        if (!this.data.isEmpty()) {
            ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.data.item().getItem());
            if (location != null) {
                return location.getPath();
            }
        }
        return "unknown";
    }

    public ItemStack getGenerateItem() {
        return this.data.item();
    }

    public boolean setGenerateItem(ItemStack stack) {
        boolean success = this.data.swapGenerationItem(stack);
        if (success) setChanged();
        return success;
    }

    public int getGenerateCount() {
        return this.data.count();
    }

    public int incGenerateCount(int value) {
        int current = this.data.incRate(value);
        if (current == value) setChanged();
        return current;
    }

    public int getCoolTime() {
        return this.coolTime;
    }

    public int decCoolTime(int value) {
        this.coolTime -= value;

        if (this.coolTime <= 20) {
            int ret = 20 - this.coolTime;
            this.coolTime = 20;
            setChanged();
            return ret;
        }

        setChanged();
        return 0;
    }

    public void coolTimeReset() {
        this.coolTimeCopy = this.coolTime;
    }

    public void tick(Level level, BlockPos pos) {
        if (level.isClientSide() || !level.isLoaded(pos)) return;
        if (this.data.isEmpty()) return;
        if (this.coolTimeCopy > this.coolTime) this.coolTimeReset();

        this.coolTimeCopy--;

        if (this.coolTimeCopy <= 0) {
            this.generateTime++;
            this.coolTimeReset();

            ItemStack stack = this.inventory.getStackInSlot(0);
            int count = stack.getCount();

            if (stack.isEmpty()) {
                ItemStack copy = this.data.item().copy();
                copy.setCount(this.data.count());
                this.inventory.setStackInSlot(0, copy);

            } else if (count < stack.getMaxStackSize()) {
                if (count + this.data.count() > stack.getMaxStackSize()) {
                    stack.grow(stack.getMaxStackSize() - count);

                } else {
                    stack.grow(this.data.count());
                }
            }

            if (!this.tryExtractItem(level, pos, Direction.DOWN)) {
                this.tryExtractItem(level, pos, Direction.UP);
            }

            if (this.coolTime > 20) {
                this.coolTime = Math.max(20, this.coolTime - this.data.rate());
            }

            if (this.data.rate() < GenerationData.BASE_MAX_STACK_SIZE && this.generateTime >= 64) {
                this.generateTime = 0;
                this.data.incRate(1);
            }

            setChanged();
        }
    }

    private boolean tryExtractItem(Level level, BlockPos pos, Direction direction) {
        ItemStack stack = this.inventory.getStackInSlot(0);

        if (!stack.isEmpty()) {
            BlockEntity tile = level.getBlockEntity(pos.relative(direction));

            if (tile != null) {
                LazyOptional<IItemHandler> cap = tile.getCapability(ForgeCapabilities.ITEM_HANDLER);

                if (cap.isPresent()) {
                    ItemStack remaining = cap.map(handler -> ItemHandlerHelper.insertItem(handler, stack, false)).orElse(stack);
                    this.inventory.setStackInSlot(0, remaining);
                    return true;
                }
            }
        }
        return false;
    }

    public Optional<ItemStack> getStored() {
        ItemStack stack = this.inventory.getStackInSlot(0);

        if (stack.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(stack);
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.data.isEmpty()) {
            tag.put(NbtKeys.GENERATE_ITEM, this.data.item().save(new CompoundTag()));
        }
        tag.putInt(NbtKeys.GENERATE_RATE, this.data.rate());
        tag.putInt(NbtKeys.GENERATE_COUNT, this.data.count());
        tag.putInt(NbtKeys.GENERATE_TIME, this.generateTime);
        tag.putInt(NbtKeys.COOL_TIME, this.coolTime);
        tag.putInt(NbtKeys.COOL_TIME_COPY, this.coolTimeCopy);
        tag.put(NbtKeys.INVENTORY, this.inventory.serializeNBT());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(NbtKeys.GENERATE_ITEM)) {
            this.data.item(ItemStack.of(tag.getCompound(NbtKeys.GENERATE_ITEM)));
        }
        if (tag.contains(NbtKeys.GENERATE_RATE)) {
            this.data.rate(tag.getInt(NbtKeys.GENERATE_RATE));
        }
        if (tag.contains(NbtKeys.GENERATE_TIME)) {
            this.generateTime = tag.getInt(NbtKeys.GENERATE_TIME);
        }
        if (tag.contains(NbtKeys.COOL_TIME)) {
            this.coolTime = tag.getInt(NbtKeys.COOL_TIME);
        }
        if (tag.contains(NbtKeys.COOL_TIME_COPY)) {
            this.coolTimeCopy = tag.getInt(NbtKeys.COOL_TIME_COPY);
        }
        if (tag.contains(NbtKeys.INVENTORY)) {
            this.inventory.deserializeNBT(tag.getCompound(NbtKeys.INVENTORY));
        }
        setChanged();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
