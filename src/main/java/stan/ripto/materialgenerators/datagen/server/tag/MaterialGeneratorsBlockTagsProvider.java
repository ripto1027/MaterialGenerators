package stan.ripto.materialgenerators.datagen.server.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;

import java.util.concurrent.CompletableFuture;

public class MaterialGeneratorsBlockTagsProvider extends BlockTagsProvider {
    public MaterialGeneratorsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MaterialGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MaterialGeneratorsBlocks.GENERATOR.get());
    }
}
