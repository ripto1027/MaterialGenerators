package stan.ripto.materialgenerators.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public enum Cards {
    C_CARD("c_card", MaterialGeneratorsItems.C_CARD, 0.06F),
    I_CARD("i_card", MaterialGeneratorsItems.I_CARD, 0.015F);

    private final String name;
    private final RegistryObject<Item> item;
    private final float rate;

    Cards(String name, RegistryObject<Item> item, float rate) {
        this.name = name;
        this.item = item;
        this.rate = rate;
    }

    public String getName() {
        return this.name;
    }

    public RegistryObject<Item> getRegistryItem() {
        return this.item;
    }

    public Item getItem() {
        return this.item.get();
    }

    public float getRate() {
        return this.rate;
    }
}
