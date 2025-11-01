package stan.ripto.materialgenerators.datagen.server.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;

import java.util.function.Consumer;

public class MaterialGeneratorsRecipeProvider extends RecipeProvider {
    public MaterialGeneratorsRecipeProvider(PackOutput output) {
        super(output);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        String groupName = "cards";

        Item db = Items.DIAMOND_BLOCK;
        Item nb = Items.NETHERITE_BLOCK;
        Item nust = Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE;
        Item cc = MaterialGeneratorsItems.C_CARD.get();

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, cc)
                .define('A', db)
                .define('B', nb)
                .define('C', nust)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .group(groupName + "_c_card")
                .unlockedBy(getHasName(db), has(db))
                .unlockedBy(getHasName(nb), has(nb))
                .unlockedBy(getHasName(nust), has(nust))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialGeneratorsItems.I_CARD.get())
                .define('A', cc)
                .define('B', nb)
                .pattern("BAB")
                .pattern("AAA")
                .pattern("BAB")
                .group(groupName + "_i_card")
                .unlockedBy(getHasName(cc), has(cc))
                .unlockedBy(getHasName(nb), has(nb))
                .save(writer);
    }
}
