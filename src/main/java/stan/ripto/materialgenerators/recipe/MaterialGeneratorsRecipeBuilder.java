package stan.ripto.materialgenerators.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class MaterialGeneratorsRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;
    private boolean showNotification = true;

    public MaterialGeneratorsRecipeBuilder (RecipeCategory category, ItemLike result, int count) {
        this.category = category;
        this.result = result.asItem();
        this.count = count;
    }

    public static MaterialGeneratorsRecipeBuilder init(RecipeCategory category, ItemLike result, int count) {
        return new MaterialGeneratorsRecipeBuilder(category, result, count);
    }

    public static MaterialGeneratorsRecipeBuilder init(RecipeCategory category, ItemLike result) {
        return init(category, result, 1);
    }

    public MaterialGeneratorsRecipeBuilder define(Character pSymbol, TagKey<Item> pTag) {
        return this.define(pSymbol, Ingredient.of(pTag));
    }

    public MaterialGeneratorsRecipeBuilder define(Character pSymbol, ItemLike pItem) {
        return this.define(pSymbol, Ingredient.of(pItem));
    }

    public MaterialGeneratorsRecipeBuilder define(Character pSymbol, Ingredient pIngredient) {
        if (this.key.containsKey(pSymbol)) {
            throw new IllegalArgumentException("Symbol '" + pSymbol + "' is already defined!");
        } else if (pSymbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(pSymbol, pIngredient);
            return this;
        }
    }

    public MaterialGeneratorsRecipeBuilder pattern(String pat) {
        if (!this.rows.isEmpty() && pat.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pat);
            return this;
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public MaterialGeneratorsRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public @NotNull MaterialGeneratorsRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @SuppressWarnings("unused")
    public MaterialGeneratorsRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new MaterialGeneratorsRecipeBuilder.Result(determineBookCategory(this.category), recipeId, this.result, this.count, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.showNotification));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for generator recipe " + id + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String str : this.rows) {
                for (int i = 0; i < str.length(); ++i) {
                    char cha = str.charAt(i);
                    if (!this.key.containsKey(cha) && cha != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + cha + "'");
                    }
                    set.remove(cha);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
                throw new IllegalStateException("Generator recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            } else if (this.advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + id);
            }
        }
    }

    public static class Result extends CraftingRecipeBuilder.CraftingResult {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final boolean showNotification;

        public Result(CraftingBookCategory category, ResourceLocation id, Item result, int count, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation advancementId, boolean showNotification) {
            super(category);
            this.id = id;
            this.result = result;
            this.count = count;
            this.group = group;
            this.pattern = pattern;
            this.key = key;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.showNotification = showNotification;
        }

        @SuppressWarnings("NullableProblems")
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            JsonArray array = new JsonArray();
            for (String str : this.pattern) {
                array.add(str);
            }
            json.add("pattern", array);
            JsonObject object = new JsonObject();
            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                object.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            json.add("key", object);
            JsonObject object1 = new JsonObject();
            object1.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                object1.addProperty("count", this.count);
            }
            json.add("result", object1);
            json.addProperty("show_notification", this.showNotification);
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public RecipeSerializer<?> getType() {
            return MaterialGeneratorsRecipes.GENERATOR_RECIPE.get();
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
