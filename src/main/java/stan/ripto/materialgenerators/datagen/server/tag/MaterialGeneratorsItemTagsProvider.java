package stan.ripto.materialgenerators.datagen.server.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.item.Cards;
import stan.ripto.materialgenerators.tag.MaterialGeneratorsTags;

import java.util.concurrent.CompletableFuture;

public class MaterialGeneratorsItemTagsProvider extends ItemTagsProvider {
    public MaterialGeneratorsItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper efh) {
        super(output, lookupProvider, blockTags, MaterialGenerators.MOD_ID, efh);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (Cards card : Cards.values()) {
            tag(MaterialGeneratorsTags.Items.CARDS).add(card.getItem());
        }
    }
}
