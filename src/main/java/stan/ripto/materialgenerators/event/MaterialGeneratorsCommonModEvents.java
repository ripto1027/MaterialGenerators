package stan.ripto.materialgenerators.event;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.datagen.client.block.MaterialGeneratorsBlockStateProvider;
import stan.ripto.materialgenerators.datagen.client.item.MaterialGeneratorsItemModelProvider;
import stan.ripto.materialgenerators.datagen.client.lang.MaterialGeneratorsLanguageProvider;
import stan.ripto.materialgenerators.datagen.server.loot.loot_table.MaterialGeneratorsLootTableProvider;
import stan.ripto.materialgenerators.datagen.server.recipe.MaterialGeneratorsRecipeProvider;
import stan.ripto.materialgenerators.datagen.server.tag.MaterialGeneratorsBlockTagsProvider;
import stan.ripto.materialgenerators.datagen.server.tag.MaterialGeneratorsItemTagsProvider;
import stan.ripto.materialgenerators.util.GeneratableItems;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MaterialGenerators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MaterialGeneratorsCommonModEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new MaterialGeneratorsBlockStateProvider(output, helper));
        gen.addProvider(event.includeClient(), new MaterialGeneratorsItemModelProvider(output, helper));
        gen.addProvider(event.includeClient(), new MaterialGeneratorsLanguageProvider.English(output));
        gen.addProvider(event.includeClient(), new MaterialGeneratorsLanguageProvider.Japanese(output));

        gen.addProvider(event.includeServer(), MaterialGeneratorsLootTableProvider.create(output));
        gen.addProvider(event.includeServer(), new MaterialGeneratorsRecipeProvider(output));
        MaterialGeneratorsBlockTagsProvider bTagProvider =
                gen.addProvider(event.includeServer(), new MaterialGeneratorsBlockTagsProvider(output, provider, helper));
        gen.addProvider(event.includeServer(), new MaterialGeneratorsItemTagsProvider(output, provider, bTagProvider.contentsGetter(), helper));
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getFileName().equals(MaterialGenerators.MOD_ID + "-server.toml")) {
            GeneratableItems.setShouldReload(true);
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getFileName().equals(MaterialGenerators.MOD_ID + "-server.toml")) {
            GeneratableItems.setShouldReload(true);
        }
    }
}
