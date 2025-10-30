package stan.ripto.materialgenerators.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;

import java.util.function.Function;

public class MaterialGeneratorsItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MaterialGenerators.MOD_ID);
    private static final Item.Properties DEFAULT_PROPERTY = new Item.Properties();

    public static final RegistryObject<Item> I_CARD = register("i_card", Item::new, DEFAULT_PROPERTY);
    public static final RegistryObject<Item> C_CARD = register("c_card", Item::new, DEFAULT_PROPERTY);

    @SuppressWarnings("SameParameterValue")
    private static <I extends Item> RegistryObject<I> register(
            String name,
            Function<Item.Properties, I> function,
            Item.Properties property
    ) {
        return ITEMS.register(name, () -> function.apply(property));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
