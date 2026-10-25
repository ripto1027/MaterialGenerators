package stan.ripto.materialgenerators;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.blockentity.MaterialGeneratorsBlockEntities;
import stan.ripto.materialgenerators.configs.MaterialGeneratorsServerConfig;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;
import stan.ripto.materialgenerators.tab.MaterialGeneratorsTabs;

@Mod(MaterialGenerators.MOD_ID)
public class MaterialGenerators {
    public static final String MOD_ID = "materialgenerators";

    public MaterialGenerators(FMLJavaModLoadingContext context) {
        IEventBus mod = context.getModEventBus();

        MaterialGeneratorsBlocks.register(mod);
        MaterialGeneratorsItems.register(mod);
        MaterialGeneratorsBlockEntities.register(mod);
        MaterialGeneratorsTabs.register(mod);

        context.registerConfig(ModConfig.Type.SERVER, MaterialGeneratorsServerConfig.SERVER_SPEC);
    }
}
