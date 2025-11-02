package stan.ripto.materialgenerators.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;

public class GeneratorRenderer implements BlockEntityRenderer<GeneratorBlockEntity> {
    private static final ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

    @SuppressWarnings("unused")
    public GeneratorRenderer(BlockEntityRendererProvider.Context context) {}

    @SuppressWarnings("NullableProblems")
    @Override
    public void render(GeneratorBlockEntity tile, float partialTick, PoseStack ps, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = tile.getGenerateItem();
        if (item == null) return;

        ItemStack display = new ItemStack(item);
        Direction face = tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        ps.pushPose();
        ps.translate(0.5, 0.5, 0.5);

        switch (face) {
            case SOUTH -> ps.mulPose(Axis.YP.rotationDegrees(180));
            case WEST -> ps.mulPose(Axis.YP.rotationDegrees(90));
            case EAST -> ps.mulPose(Axis.YP.rotationDegrees(270));
            case NORTH -> {}
        }

        ps.scale(0.8F, 0.8F, 0.8F);

        renderer.renderStatic(
                display,
                ItemDisplayContext.FIXED,
                packedLight,
                packedOverlay,
                ps,
                buffer,
                tile.getLevel(),
                0
        );
        
        ps.popPose();
    }
}
