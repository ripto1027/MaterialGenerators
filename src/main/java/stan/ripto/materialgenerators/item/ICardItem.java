package stan.ripto.materialgenerators.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;
import stan.ripto.materialgenerators.datagen.client.lang.TranslateKeys;

public class ICardItem extends AbstractCardItem {
    public ICardItem(Properties properties) {
        super(properties, TranslateKeys.C_CARD_TOOLTIP);
    }

    @Override
    public void useOnGenerator(ItemStack heldCard, GeneratorBlockEntity blockentity, Player player) {
        int remaining = blockentity.incGenerateCount(heldCard.getCount());
        heldCard.setCount(remaining);
    }
}
