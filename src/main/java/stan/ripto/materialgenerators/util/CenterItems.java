package stan.ripto.materialgenerators.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CenterItems {
    private static final Set<Item> CENTER_INGREDIENTS = new HashSet<>();

    public static void setCenterIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            for (ItemStack stack : ingredient.getItems()) {
                CENTER_INGREDIENTS.add(stack.getItem());
            }
        }
    }

    public static boolean isCenterIngredient(Item item) {
        return CENTER_INGREDIENTS.contains(item);
    }
}
