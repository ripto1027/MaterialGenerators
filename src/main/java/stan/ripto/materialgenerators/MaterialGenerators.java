package stan.ripto.materialgenerators;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.blockentity.MaterialGeneratorsBlockEntities;
import stan.ripto.materialgenerators.loot.MaterialGeneratorsLootModifier;
import stan.ripto.materialgenerators.recipe.MaterialGeneratorsRecipeSerializers;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;
import stan.ripto.materialgenerators.tab.MaterialGeneratorsTabs;

@Mod(MaterialGenerators.MOD_ID)
public class MaterialGenerators {
    public static final String MOD_ID = "materialgenerators";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MaterialGenerators(FMLJavaModLoadingContext context) {
        IEventBus mod = context.getModEventBus();

        MaterialGeneratorsBlocks.register(mod);
        MaterialGeneratorsItems.register(mod);
        MaterialGeneratorsBlockEntities.register(mod);
        MaterialGeneratorsRecipeSerializers.register(mod);
        MaterialGeneratorsTabs.register(mod);
        MaterialGeneratorsLootModifier.register(mod);
    }
}
