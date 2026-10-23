package stan.ripto.materialgenerators.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public enum Cards {
    C_CARD(MaterialGeneratorsItems.C_CARD),
    I_CARD(MaterialGeneratorsItems.I_CARD);

    private final RegistryObject<Item> item;

    Cards(RegistryObject<Item> item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item.get();
    }
}
