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
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.event.MaterialGeneratorsServerStarting;
import stan.ripto.materialgenerators.nbt.NbtKeys;

@SuppressWarnings("NullableProblems")
public class GeneratorRecipe implements CraftingRecipe, IShapedRecipe<CraftingContainer> {
    private final ResourceLocation id;
    private final ItemStack result = new ItemStack(MaterialGeneratorsBlocks.GENERATOR.get());

    private static final RecipeSerializer<GeneratorRecipe> SERIALIZER = new Serializer();
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private static final int SIZE = WIDTH * HEIGHT;
    private static final String GROUP = "generator";
    private static final CraftingBookCategory CATEGORY = CraftingBookCategory.MISC;

    private NonNullList<Ingredient> ingredients = null;

    public GeneratorRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public String getGroup() {
        return GROUP;
    }

    @Override
    public CraftingBookCategory category() {
        return CATEGORY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result.copy();
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
    public NonNullList<Ingredient> getIngredients() {
        if (this.ingredients == null) {
            this.ingredients = NonNullList.withSize(SIZE, Ingredient.EMPTY);

            for (int i = 0; i < SIZE; i++) {
                switch (i) {
                    case 0, 2, 6, 8 -> this.ingredients.set(i, Ingredient.of(Items.REDSTONE_BLOCK));
                    case 1, 3, 5, 7 -> this.ingredients.set(i, Ingredient.of(Items.GLASS));
                    case 4 -> this.ingredients.set(i, Ingredient.of(MaterialGeneratorsServerStarting.getStackStream()));
                }
            }
        }
        return this.ingredients;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        if (this.ingredients == null) return false;

        for (int i = 0; i < SIZE; i++) {
            if (!this.ingredients.get(i).test(container.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack resultCopy = this.result.copy();

        Item generateItem = container.getItem(4).getItem();
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(generateItem);
        if (location != null) {
            CompoundTag tag = resultCopy.getOrCreateTag();
            tag.putString(NbtKeys.GENERATE_ITEM, location.toString());
        }

        return resultCopy;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return WIDTH == width && HEIGHT == height;
    }

    public static class Serializer implements RecipeSerializer<GeneratorRecipe> {
        @Override
        public GeneratorRecipe fromJson(ResourceLocation id, JsonObject serializedRecipe) {
            return new GeneratorRecipe(id);
        }

        @Override
        public @Nullable GeneratorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new GeneratorRecipe(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, GeneratorRecipe recipe) {}
    }
}