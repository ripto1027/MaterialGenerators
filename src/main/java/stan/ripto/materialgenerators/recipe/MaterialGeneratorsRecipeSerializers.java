package stan.ripto.materialgenerators.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;

public class MaterialGeneratorsRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MaterialGenerators.MOD_ID);

    @SuppressWarnings("unused")
    public static final RegistryObject<RecipeSerializer<GeneratorRecipe>> GENERATOR =
            SERIALIZERS.register("generator", GeneratorRecipe.Serializer::new);

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
