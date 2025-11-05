package stan.ripto.materialgenerators.plugin.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.impl.ui.ProgressElement;
import snownee.jade.impl.ui.ProgressStyle;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;
import stan.ripto.materialgenerators.nbt.NbtKeys;

import java.awt.*;

public enum GeneratorJadeProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(MaterialGenerators.MOD_ID, "generator_info");
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        CompoundTag nbt = accessor.getServerData();
        if (nbt.contains(NbtKeys.GENERATE_COUNT)) {
            tooltip.add(Component.literal("GenerateCount: " + nbt.getInt(NbtKeys.GENERATE_COUNT)));
        }
        if (nbt.contains(NbtKeys.COOL_TIME) && nbt.contains(NbtKeys.COOL_TIME_COPY)) {
            int coolTime = nbt.getInt(NbtKeys.COOL_TIME);
            tooltip.add(Component.literal("Cooldown: " + coolTime + " tick"));

            float progress = 1.0F - ((float) nbt.getInt(NbtKeys.COOL_TIME_COPY) / (float) coolTime);
            tooltip.add(new ProgressElement(
                    progress,
                    Component.literal("Progress").withStyle(ChatFormatting.WHITE),
                    new ProgressStyle().color(Color.CYAN.getRGB()),
                    BoxStyle.DEFAULT,
                    true
            ));
        }
    }

    @Override
    public void appendServerData(CompoundTag nbt, BlockAccessor accessor) {
        BlockEntity tile = accessor.getBlockEntity();
        if (tile instanceof GeneratorBlockEntity gen) {
            CompoundTag genNbt = gen.saveWithoutMetadata();
            nbt.putString(NbtKeys.GENERATE_ITEM, genNbt.getString(NbtKeys.GENERATE_ITEM));
            nbt.putInt(NbtKeys.GENERATE_COUNT, genNbt.getInt(NbtKeys.GENERATE_COUNT));
            nbt.putInt(NbtKeys.COOL_TIME, genNbt.getInt(NbtKeys.COOL_TIME));
            nbt.putInt(NbtKeys.COOL_TIME_COPY, genNbt.getInt(NbtKeys.COOL_TIME_COPY));
        }
    }
}
