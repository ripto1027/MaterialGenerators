package stan.ripto.materialgenerators.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.MaterialGenerators;

import java.util.Objects;
import java.util.function.Consumer;

public class GeneratorRecipeBuilder {
    private static final ResourceLocation ROOT_RECIPE_ADVANCEMENT = ResourceLocation.fromNamespaceAndPath("minecraft", "recipes/root");

    private final ResourceLocation id;
    private final Ingredient center;
    private final Advancement.Builder advancement;

    public GeneratorRecipeBuilder(ResourceLocation id, Ingredient center) {
        this.id = id;
        this.center = center;

        this.advancement = Advancement.Builder.recipeAdvancement();
        this.advancement.addCriterion(getHasName(Items.GLASS), has(Tags.Items.GLASS));
        this.advancement.addCriterion(getHasName(Items.REDSTONE_BLOCK), has(Tags.Items.STORAGE_BLOCKS_REDSTONE));
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
    }

    private static String getHasName(ItemLike item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.asItem())).getPath();
    }

    private static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
    }

    private static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... predicate) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicate);
    }

    public static GeneratorRecipeBuilder create(String path, ItemLike item) {
        return new GeneratorRecipeBuilder(ResourceLocation.fromNamespaceAndPath(MaterialGenerators.MOD_ID, "generator_from_" + path), Ingredient.of(item));
    }

    public static GeneratorRecipeBuilder create(String path, TagKey<Item> tag) {
        return new GeneratorRecipeBuilder(ResourceLocation.fromNamespaceAndPath(MaterialGenerators.MOD_ID, "generator_from_" + path), Ingredient.of(tag));
    }

    public void save(Consumer<FinishedRecipe> writer) {
        writer.accept(new Result(this.id, this.center, this.advancement, this.id.withPrefix("recipes/" + RecipeCategory.MISC.getFolderName() + "/")));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient center;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient center, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.center = center;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("type", "materialgenerators:generator");
            json.add("center", this.center.toJson());
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return MaterialGeneratorsRecipeSerializers.GENERATOR.get();
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
