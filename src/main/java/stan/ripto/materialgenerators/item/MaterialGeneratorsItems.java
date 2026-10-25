package stan.ripto.materialgenerators.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;

public class MaterialGeneratorsItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MaterialGenerators.MOD_ID);
    private static final Item.Properties DEFAULT_PROPERTY = new Item.Properties();

    public static final RegistryObject<Item> I_CARD = ITEMS.register("i_card", () -> new ICardItem(DEFAULT_PROPERTY));
    public static final RegistryObject<Item> C_CARD = ITEMS.register("c_card", () -> new CCardItem(DEFAULT_PROPERTY));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
