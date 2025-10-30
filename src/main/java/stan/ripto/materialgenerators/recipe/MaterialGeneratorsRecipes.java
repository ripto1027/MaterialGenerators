package stan.ripto.materialgenerators.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;

public class MaterialGeneratorsRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MaterialGenerators.MOD_ID);

    @SuppressWarnings("unused")
    public static final RegistryObject<RecipeSerializer<GeneratorRecipe>> GENERATOR_RECIPE =
            RECIPES.register("generator_recipe", GeneratorRecipe.Serializer::new);

    public static void register(IEventBus bus) {
        RECIPES.register(bus);
    }
}
