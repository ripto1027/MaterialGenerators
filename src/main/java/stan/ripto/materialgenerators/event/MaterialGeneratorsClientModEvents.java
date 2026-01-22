package stan.ripto.materialgenerators.event;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.blockentity.MaterialGeneratorsBlockEntities;
import stan.ripto.materialgenerators.render.GeneratorRenderer;

@Mod.EventBusSubscriber(
        modid = MaterialGenerators.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class MaterialGeneratorsClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(MaterialGeneratorsBlockEntities.GENERATOR.get(), GeneratorRenderer::new);
    }
}
