package stan.ripto.materialgenerators.datagen.server.loot.loot_table;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;

import java.util.List;
import java.util.Set;

public class MaterialGeneratorsLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(MaterialGeneratorsSubProvider::new, LootContextParamSets.BLOCK)));
    }

    private static class MaterialGeneratorsSubProvider extends BlockLootSubProvider {
        protected MaterialGeneratorsSubProvider() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropSelf(MaterialGeneratorsBlocks.GENERATOR.get());
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return MaterialGeneratorsBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }
}
