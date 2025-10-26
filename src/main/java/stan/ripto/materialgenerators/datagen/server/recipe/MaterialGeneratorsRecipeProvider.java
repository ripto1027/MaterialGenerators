package stan.ripto.materialgenerators.datagen.server.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.Tags;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;
import stan.ripto.materialgenerators.recipe.MaterialGeneratorsRecipeBuilder;

import java.util.function.Consumer;

public class MaterialGeneratorsRecipeProvider extends RecipeProvider {
    public MaterialGeneratorsRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        MaterialGeneratorsRecipeBuilder.init(RecipeCategory.MISC, MaterialGeneratorsBlocks.GENERATOR.get())
                .define('A', Tags.Items.COBBLESTONE)
                .define('B', MaterialGeneratorsItems.DUMMY.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .group("generator")
                .unlockedBy(getName(), has(Tags.Items.COBBLESTONE))
                .save(writer, "generator");
    }
}
