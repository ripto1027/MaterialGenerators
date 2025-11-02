package stan.ripto.materialgenerators.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.datagen.client.lang.TranslateKeys;

import java.util.function.BiFunction;

public class MaterialGeneratorsItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MaterialGenerators.MOD_ID);
    private static final Item.Properties DEFAULT_PROPERTY = new Item.Properties();

    public static final RegistryObject<Item> I_CARD = registerCards("i_card", CardItem::new, DEFAULT_PROPERTY, TranslateKeys.I_CARD_TOOLTIP);
    public static final RegistryObject<Item> C_CARD = registerCards("c_card", CardItem::new, DEFAULT_PROPERTY, TranslateKeys.C_CARD_TOOLTIP);

    @SuppressWarnings("SameParameterValue")
    private static <I extends Item> RegistryObject<I> registerCards(
            String name,
            BiFunction<Item.Properties, String, I> function,
            Item.Properties property,
            String translateKey
    ) {
        return ITEMS.register(name, () -> function.apply(property, translateKey));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
