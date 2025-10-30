package stan.ripto.materialgenerators;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.block_entity.MaterialGeneratorsBlockEntities;
import stan.ripto.materialgenerators.loot.MaterialGeneratorsLootModifier;
import stan.ripto.materialgenerators.recipe.MaterialGeneratorsRecipes;
import stan.ripto.materialgenerators.event.MaterialGeneratorsServerStarting;
import stan.ripto.materialgenerators.init.MaterialGeneratorsInit;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;
import stan.ripto.materialgenerators.render.GeneratorRenderer;
import stan.ripto.materialgenerators.tab.MaterialGeneratorsTabs;

@Mod(MaterialGenerators.MOD_ID)
public class MaterialGenerators {
    public static final String MOD_ID = "materialgenerators";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MaterialGenerators(FMLJavaModLoadingContext context) {
        IEventBus mod = context.getModEventBus();
        IEventBus forge = MinecraftForge.EVENT_BUS;

        MaterialGeneratorsBlocks.register(mod);
        MaterialGeneratorsItems.register(mod);
        MaterialGeneratorsBlockEntities.register(mod);
        MaterialGeneratorsRecipes.register(mod);
        MaterialGeneratorsTabs.register(mod);
        MaterialGeneratorsLootModifier.register(mod);

        mod.addListener(this::onClientSetup);
        mod.addListener(this::onCommonSetup);

        forge.addListener(MaterialGeneratorsServerStarting::onServerStarting);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(MaterialGeneratorsBlockEntities.GENERATOR.get(), GeneratorRenderer::new);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(MaterialGeneratorsInit::setup);
    }
}
