package stan.ripto.materialgenerators.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.server.ServerLifecycleHooks;
import stan.ripto.materialgenerators.recipe.GeneratorRecipe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CenterItems {
    private static final Set<Item> CENTER_INGREDIENTS = new HashSet<>();
    private static boolean shouldReload = false;

    public static List<Ingredient> getCenterIngredients(MinecraftServer server) {
        return server.getRecipeManager().getRecipes()
                .stream()
                .filter(recipe -> recipe instanceof GeneratorRecipe)
                .map(recipe -> ((GeneratorRecipe) recipe).getCenter())
                .toList();
    }

    public static void setCenterIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            for (ItemStack stack : ingredient.getItems()) {
                CENTER_INGREDIENTS.add(stack.getItem());
            }
        }
    }

    public static void setShouldReload(boolean value) {
        shouldReload = value;
    }

    public static boolean isCenterIngredient(Item item) {
        if (shouldReload) {
            List<Ingredient> ingredients = getCenterIngredients(ServerLifecycleHooks.getCurrentServer());
            setCenterIngredients(ingredients);
        }

        return CENTER_INGREDIENTS.contains(item);
    }
}
