package stan.ripto.materialgenerators.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.recipe.IngredientLoader;

@Mod.EventBusSubscriber(modid = MaterialGenerators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaterialGeneratorsCommonForgeEvents {
    private static boolean hasReloaded = false;

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        IngredientLoader.tryGetCenterIngredient();
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        hasReloaded = false;
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if ((event.getEntity().level() instanceof ServerLevel level) && !hasReloaded) {
            hasReloaded = true;
            MinecraftServer server = level.getServer();
            server.reloadResources(server.getPackRepository().getSelectedIds());
        }
    }
}
