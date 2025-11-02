package stan.ripto.materialgenerators.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;
import stan.ripto.materialgenerators.datagen.client.lang.TranslateKeys;
import stan.ripto.materialgenerators.nbt.NbtKeys;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class GeneratorBlockItem extends BlockItem {
    public GeneratorBlockItem(Block block, Properties property) {
        super(block, property);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltips, flag);
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            if (hasItem(tag)) {
                addItems(tooltips, getItem(tag));
            }
            if (hasInts(tag)) {
                addInts(tooltips, getCount(tag), getCoolTime(tag));
            } else {
                addInts(tooltips, 1, 6000);
            }
        }
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof GeneratorBlockEntity gen) {
            CompoundTag tag = stack.getOrCreateTag();
            gen.load(tag);
            gen.setChanged();
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }

    private boolean hasInts(CompoundTag tag) {
        return tag.contains(NbtKeys.GENERATE_COUNT) && tag.contains(NbtKeys.COOL_TIME);
    }

    private boolean hasItem(CompoundTag tag) {
        return tag.contains(NbtKeys.GENERATE_ITEM);
    }

    private int getCount(CompoundTag tag) {
        return tag.getInt(NbtKeys.GENERATE_COUNT);
    }

    private int getCoolTime(CompoundTag tag) {
        return tag.getInt(NbtKeys.COOL_TIME);
    }

    private String getItem(CompoundTag tag) {
        return tag.getString(NbtKeys.GENERATE_ITEM);
    }

    private void addItems(List<Component> tooltips, String id) {
        ResourceLocation location = ResourceLocation.parse(id);
        tooltips.add(Component.translatable(TranslateKeys.GENERATOR_TOOLTIP1, location.getPath()).withStyle(ChatFormatting.GRAY));
    }

    private void addInts(List<Component> tooltips, int genCount, int coolTime) {
        tooltips.add(Component.translatable(TranslateKeys.GENERATOR_TOOLTIP2, genCount, coolTime).withStyle(ChatFormatting.GRAY));
    }
}
