package stan.ripto.materialgenerators.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.event.MaterialGeneratorsServerStarting;
import stan.ripto.materialgenerators.item.Cards;

import java.util.Set;

public class GeneratorRecipe implements CraftingRecipe, IShapedRecipe<CraftingContainer> {
    private final ResourceLocation id;
    private final ItemStack result = new ItemStack(MaterialGeneratorsBlocks.GENERATOR.get());

    private static final RecipeSerializer<GeneratorRecipe> SERIALIZER = new Serializer();
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private static final int SIZE = WIDTH * HEIGHT;
    private static final String GROUP = "generator";
    private static final CraftingBookCategory CATEGORY = CraftingBookCategory.MISC;

    private static final Set<Item> CENTER = MaterialGeneratorsServerStarting.getItemSet();

    public GeneratorRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull String getGroup() {
        return GROUP;
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CATEGORY;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return new ItemStack(MaterialGeneratorsBlocks.GENERATOR.get());
    }

    @Override
    public int getRecipeWidth() {
        return WIDTH;
    }

    @Override
    public int getRecipeHeight() {
        return HEIGHT;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(SIZE, Ingredient.EMPTY);

        for (int i = 0; i < SIZE; i++) {
            if (i == 1) {
                ingredients.set(i, Ingredient.of(Cards.I_CARD.getItem()));
            } else if (i == 3 || i == 5 || i == 7) {
                ingredients.set(i, Ingredient.of(Items.GLASS));
            } else if (i == 4) {
                ingredients.set(i, Ingredient.of(MaterialGeneratorsServerStarting.getStackStream()));
            } else {
                ingredients.set(i, Ingredient.of(Cards.C_CARD.getItem()));
            }
        }

        return ingredients;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(CraftingContainer container, Level level) {
        for (int i = 0; i < SIZE; i++) {
            ItemStack stack = container.getItem(i);
            if (i == 1) {
                if (!stack.is(Cards.I_CARD.getItem())) return false;
            } else if (i == 3 || i == 5 || i == 7) {
                if (!stack.is(Items.GLASS)) return false;
            } else if (i == 4) {
                if (!CENTER.contains(stack.getItem())) return false;
            } else {
                if (!stack.is(Cards.C_CARD.getItem())) return false;
            }
        }
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        Item generateItem = container.getItem(4).getItem();
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(generateItem);
        if (location != null) {
            CompoundTag tag = this.result.getOrCreateTag();
            tag.putString("generate_item", location.toString());
        }
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return WIDTH == width && HEIGHT == height;
    }

    public static class Serializer implements RecipeSerializer<GeneratorRecipe> {
        @SuppressWarnings("NullableProblems")
        @Override
        public GeneratorRecipe fromJson(ResourceLocation id, JsonObject serializedRecipe) {
            return new GeneratorRecipe(id);
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public @Nullable GeneratorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new GeneratorRecipe(id);
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public void toNetwork(FriendlyByteBuf buf, GeneratorRecipe recipe) {}
    }
}