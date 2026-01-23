package stan.ripto.materialgenerators.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import stan.ripto.materialgenerators.MaterialGenerators;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class IngredientLoader {
    private static final Set<ItemStack> STACKS = new HashSet<>();

    public static Stream<ItemStack> getStackStream() {
        return STACKS.stream();
    }

    public static void tryGetCenterIngredient() {
        Path configPath = FMLPaths.CONFIGDIR.get().resolve(MaterialGenerators.MOD_ID);
        Path jsonPath = configPath.resolve("generate_items.json");

        try (BufferedReader reader = Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8)) {
            JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();

            if (object.has("ingredients")) {
                JsonArray array = object.getAsJsonArray("ingredients");

                for (JsonElement element : array.asList()) {
                    if (element instanceof JsonObject object1) {
                        addItemsFromJson(object1);
                    }
                }
            }

        } catch (IOException e) {
            MaterialGenerators.LOGGER.error("Failed to read {}", jsonPath, e);
        }
    }

    private static void addItemsFromJson(JsonObject json) {
        if (json.has("item")) {
            ResourceLocation location = ResourceLocation.tryParse(json.get("item").getAsString());
            if (location != null) {
                Item item = ForgeRegistries.ITEMS.getValue(location);

                if (item != null) {
                    STACKS.add(new ItemStack(item));
                }
            }

        } else if (json.has("tag")) {
            ResourceLocation location = ResourceLocation.tryParse(json.get("tag").getAsString());
            if (location != null) {
                TagKey<Item> key = TagKey.create(ForgeRegistries.Keys.ITEMS, location);
                ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();

                if (manager != null) {
                    manager.getTag(key).forEach(item -> STACKS.add(new ItemStack(item)));
                }
            }
        }
    }
}
