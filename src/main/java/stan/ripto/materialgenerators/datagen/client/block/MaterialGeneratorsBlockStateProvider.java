package stan.ripto.materialgenerators.datagen.client.block;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;

import java.util.function.BiConsumer;

public class MaterialGeneratorsBlockStateProvider extends BlockStateProvider {
    public MaterialGeneratorsBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MaterialGenerators.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath("minecraft", "block/glass");
        ResourceLocation endLocation = ResourceLocation.fromNamespaceAndPath(MaterialGenerators.MOD_ID, "block/generator_end");
        BlockModelBuilder blockModel = models().cubeColumnHorizontal("generator", location, endLocation).renderType("translucent");

        register(MaterialGeneratorsBlocks.GENERATOR.get(), blockModel, this::horizontalBlock, this::simpleBlockItem);
    }

    private void register(
            Block block,
            BlockModelBuilder model,
            BiConsumer<Block, BlockModelBuilder> blockProvider,
            BiConsumer<Block, BlockModelBuilder> blockitemProvider
    ) {
        blockProvider.accept(block, model);
        blockitemProvider.accept(block, model);
    }
}
