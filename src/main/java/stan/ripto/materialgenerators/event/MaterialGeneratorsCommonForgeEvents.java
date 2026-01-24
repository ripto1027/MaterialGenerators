package stan.ripto.materialgenerators.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.item.Cards;
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

    @SubscribeEvent
    public static void onLivingDrop(LivingDropsEvent event) {
        Entity source = event.getSource().getEntity();
        if (!(source instanceof Player)) return;

        LivingEntity mob = event.getEntity();
        RandomSource random = mob.getRandom();

        for (int i = 0; i < event.getLootingLevel() + 1; i++) {
            if (random.nextFloat() < 0.01F) {
                event.getDrops().add(createDrop(Cards.C_CARD.getItem(), mob));
            }

            if (random.nextFloat() < 0.0025F) {
                event.getDrops().add(createDrop(Cards.I_CARD.getItem(), mob));
            }
        }
    }

    private static ItemEntity createDrop(ItemLike item, Entity mob) {
        ItemStack stack = new ItemStack(item);
        return new ItemEntity(mob.level(), mob.getX(), mob.getY(), mob.getZ(), stack);
    }
}
