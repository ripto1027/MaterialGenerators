package stan.ripto.materialgenerators.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import stan.ripto.materialgenerators.util.GenerateItemHandler;

public class GeneratorBlockEntity extends BlockEntity {
    private Item generateItem = null;
    private int generateCount = 1;
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
            return stack.is(generateItem);
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
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.generateItem);
        if (location != null) {
            return location.getPath();
        }
        return null;
    }

    public Item getGenerateItem() {
        return this.generateItem;
    }

    public void setGenerateItem(Item item) {
        this.generateItem = item;
    }

    public int getGenerateCount() {
        return this.generateCount;
    }

    public int setGenerateCount(ItemStack usedItem) {
        if (this.generateCount == 64) return 0;

        int i = 0;
        int count = usedItem.getCount();
        int value = usedItem.is(Items.NETHER_STAR) ? 1 : 2;

        while (true) {
            i++;
            this.generateCount += value;

            if (i == count) break;

            if (this.generateCount >= 64) {
                this.generateCount = 64;
                break;
            }
        }

        setChanged();
        return i;
    }

    public int getCoolTime() {
        return this.coolTime;
    }

    public int setCoolTime(ItemStack usedItem) {
        if (this.coolTime == 20) return 0;

        int i = 0;
        int count = usedItem.getCount();
        int value = usedItem.is(this.generateItem) ? 1 : 200;

        while (true) {
            i++;
            this.coolTime -= value;

            if (i == count) break;

            if (this.coolTime <= 20) {
                this.coolTime = 20;
                break;
            }
        }

        setChanged();
        return i;
    }

    public void tick(Level level, BlockPos pos) {
        if (level.isClientSide || !level.isLoaded(pos)) return;
        if (this.coolTimeCopy > this.coolTime) this.coolTimeCopy = this.coolTime;

        ItemStack stack = this.inventory.getStackInSlot(0);
        this.coolTimeCopy--;
        if (this.coolTimeCopy <= 0) {
            this.coolTimeCopy = this.coolTime;
            if (this.generateItem != null) {
                int count = stack.getCount();
                if (stack.isEmpty()) {
                    this.inventory.setStackInSlot(0, new ItemStack(this.generateItem, this.generateCount));
                } else if (count < 64) {
                    if (count + this.generateCount > 64) {
                        stack.grow(64 - count);
                    } else {
                        stack.grow(this.generateCount);
                    }
                }
            }
        }

        if (!stack.isEmpty()) {
            BlockEntity down = level.getBlockEntity(pos.relative(Direction.DOWN));
            if (down != null) {
                down.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    ItemStack extract = ItemHandlerHelper.insertItem(handler, stack, false);
                    this.inventory.setStackInSlot(0, extract);
                });
            }
            ItemStack stack1 = this.inventory.getStackInSlot(0);
            if (!stack1.isEmpty()) {
                BlockEntity up = level.getBlockEntity(pos.relative(Direction.UP));
                if (up != null) {
                    up.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                        ItemStack extract = ItemHandlerHelper.insertItem(handler, stack1, false);
                        this.inventory.setStackInSlot(0, extract);
                    });
                }
            }
            ItemStack stack2 = this.inventory.getStackInSlot(0);
            if (!stack2.isEmpty()) {
                GenerateItemHandler.generateItemHandler(this, stack2);
            }
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.generateItem != null) {
            ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.generateItem);
            if (location != null) {
                tag.putString(NbtKeys.GENERATE_ITEM, location.toString());
            }
        }
        if (this.generateCount > 1 || this.coolTime < 6000) {
            tag.putInt(NbtKeys.GENERATE_COUNT, this.generateCount);
            tag.putInt(NbtKeys.COOL_TIME, this.coolTime);
        }
        setChanged();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(NbtKeys.GENERATE_ITEM)) {
            ResourceLocation location = ResourceLocation.tryParse(tag.getString(NbtKeys.GENERATE_ITEM));
            if (location != null) {
                this.generateItem = ForgeRegistries.ITEMS.getValue(location);
            }
        }
        if (tag.contains(NbtKeys.GENERATE_COUNT)) {
            this.generateCount = tag.getInt(NbtKeys.GENERATE_COUNT);
        }
        if (tag.contains(NbtKeys.COOL_TIME)) {
            this.coolTime = tag.getInt(NbtKeys.COOL_TIME);
        }
        setChanged();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
