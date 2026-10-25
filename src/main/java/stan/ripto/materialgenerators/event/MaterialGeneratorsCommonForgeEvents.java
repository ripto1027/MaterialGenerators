package stan.ripto.materialgenerators.event;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.item.Cards;

@Mod.EventBusSubscriber(modid = MaterialGenerators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaterialGeneratorsCommonForgeEvents {
    @SubscribeEvent
    public static void onLivingDrop(LivingDropsEvent event) {
        Entity source = event.getSource().getEntity();
        if (!(source instanceof Player)) return;

        LivingEntity mob = event.getEntity();
        RandomSource random = mob.getRandom();

        if (random.nextFloat() < 0.005F + event.getLootingLevel() * 0.01F) {
            event.getDrops().add(createDrop(Cards.C_CARD.getItem(), mob));
        }

        if (random.nextFloat() < 0.00125F + event.getLootingLevel() * 0.003F) {
            event.getDrops().add(createDrop(Cards.I_CARD.getItem(), mob));
        }
    }

    private static ItemEntity createDrop(ItemLike item, Entity mob) {
        ItemStack stack = new ItemStack(item);
        return new ItemEntity(mob.level(), mob.getX(), mob.getY(), mob.getZ(), stack);
    }
}
