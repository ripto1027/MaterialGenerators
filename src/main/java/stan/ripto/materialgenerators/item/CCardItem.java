package stan.ripto.materialgenerators.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;
import stan.ripto.materialgenerators.datagen.client.lang.TranslateKeys;

public class CCardItem extends AbstractCardItem {
    private static final int DECREASE_VALUE = 20;

    public CCardItem(Properties properties) {
        super(properties, TranslateKeys.C_CARD_TOOLTIP);
    }

    @Override
    public void useOnGenerator(ItemStack heldCard, GeneratorBlockEntity blockentity, Player player) {
        int remaining = blockentity.decCoolTime(heldCard.getCount() * DECREASE_VALUE);
        heldCard.setCount((int) Math.ceil((double) remaining / DECREASE_VALUE));
    }
}
