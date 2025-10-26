package stan.ripto.materialgenerators.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import stan.ripto.materialgenerators.event.MaterialGeneratorsServerStarting;

import java.util.Map;
import java.util.Set;

public class GeneratorRecipe implements CraftingRecipe, IShapedRecipe<CraftingContainer> {
    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack result;
    private final ResourceLocation id;
    private final String group;
    private final CraftingBookCategory category;
    private final boolean showNotification;
    private static final Set<Item> CENTER = MaterialGeneratorsServerStarting.getStackSet();

    public GeneratorRecipe(ResourceLocation id, String group, CraftingBookCategory category, NonNullList<Ingredient> recipeItems, ItemStack result, boolean showNotification) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.recipeItems = recipeItems;
        this.result = result;
        this.showNotification = showNotification;
    }

    @SuppressWarnings("unused")
    public GeneratorRecipe(ResourceLocation id, String group, CraftingBookCategory category, NonNullList<Ingredient> recipeItems, ItemStack result) {
        this(id, group, category, recipeItems, result, true);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public int getRecipeWidth() {
        return WIDTH;
    }

    @Override
    public int getRecipeHeight() {
        return HEIGHT;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(CraftingContainer container, Level level) {
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            ItemStack stack = container.getItem(i);
            if (i == 4) {
                if (!CENTER.contains(stack.getItem())) {
                    return false;
                }
            } else {
                if (!recipeItems.get(i).test(stack)) {
                    return false;
                }
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

    static NonNullList<Ingredient> dissolvePattern(String[] pattern, Map<String, Ingredient> keys) {
        NonNullList<Ingredient> list = NonNullList.withSize(WIDTH * HEIGHT, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");

        for (int i = 0; i < pattern.length; ++i) {
            for (int j = 0; j < pattern[i].length(); ++j) {
                String str = pattern[i].substring(j, j + 1);
                Ingredient ingredient = keys.get(str);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + str + "' but it's not defined in the key.");
                }
                set.remove(str);
                list.set(j + WIDTH * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return list;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... toShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for (int ii = 0; ii < toShrink.length; ++ii) {
            String str = toShrink[ii];
            i = Math.min(i, firstNonSpace(str));
            int jj = lastNonSpace(str);
            j = Math.max(j, jj);
            if (jj < 0) {
                if (k == ii) {
                    ++k;
                }
                ++l;
            } else {
                l = 0;
            }
        }

        if (toShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[toShrink.length - l - k];

            for (int kk = 0; kk < astring.length; ++kk) {
                astring[kk] = toShrink[kk + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> list = this.getIngredients();
        return list.isEmpty() || list.stream().filter((ingredient) -> !ingredient.isEmpty()).anyMatch((ForgeHooks::hasNoElements));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static int firstNonSpace(String entry) {
        int i;
        for (i = 0; i < entry.length() && entry.charAt(i) == ' '; ++i) {}
        return i;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static int lastNonSpace(String entry) {
        int i;
        for (i = entry.length() - 1; i >= 0 && entry.charAt(i) == ' '; --i) {}
        return i;
    }

    static String[] patternFromJson(JsonArray patternArray) {
        String[] astring = new String[patternArray.size()];
        if (astring.length > HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for (int i = 0; i < astring.length; ++i) {
                String str = GsonHelper.convertToString(patternArray.get(i), "pattern[" + i + "]");
                if (str.length() > WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + WIDTH + " is maximum");
                }
                if (i > 0 && astring[0].length() != str.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }
                astring[i] = str;
            }
            return astring;
        }
    }

    static Map<String, Ingredient> keyFromJson(JsonObject keyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : keyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue(), false));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack itemStackFromJson(JsonObject pStackObject) {
        return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(pStackObject, true, true);
    }

    public static class Serializer implements RecipeSerializer<GeneratorRecipe> {
        @SuppressWarnings("NullableProblems")
        @Override
        public GeneratorRecipe fromJson(ResourceLocation id, JsonObject serializedRecipe) {
            String str = GsonHelper.getAsString(serializedRecipe, "group", "");
            CraftingBookCategory bookCategory = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(serializedRecipe, "category", null), CraftingBookCategory.MISC);
            Map<String, Ingredient> map = GeneratorRecipe.keyFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "key"));
            String[] astring = GeneratorRecipe.shrink(GeneratorRecipe.patternFromJson(GsonHelper.getAsJsonArray(serializedRecipe, "pattern")));
            NonNullList<Ingredient> list = GeneratorRecipe.dissolvePattern(astring, map);
            ItemStack result = GeneratorRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
            boolean bool = GsonHelper.getAsBoolean(serializedRecipe, "show_notification", true);
            return new GeneratorRecipe(id, str, bookCategory, list, result, bool);
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public @Nullable GeneratorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String str = buf.readUtf();
            CraftingBookCategory cat = buf.readEnum(CraftingBookCategory.class);
            NonNullList<Ingredient> list = NonNullList.withSize(WIDTH * HEIGHT, Ingredient.EMPTY);

            list.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack stack = buf.readItem();
            boolean bool = buf.readBoolean();
            return new GeneratorRecipe(id, str, cat, list, stack, bool);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, GeneratorRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category);
            for (Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }
            buf.writeItem(recipe.result);
            buf.writeBoolean(recipe.showNotification);
        }
    }
}