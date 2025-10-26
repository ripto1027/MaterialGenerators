package stan.ripto.materialgenerators.loot;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stan.ripto.materialgenerators.MaterialGenerators;

public class MaterialGeneratorsLootModifier {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MaterialGenerators.MOD_ID);

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register("add_item", AddItemModifier.CODEC);
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
