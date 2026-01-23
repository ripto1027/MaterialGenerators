package stan.ripto.materialgenerators.init;

import com.google.gson.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.loading.FMLPaths;
import stan.ripto.materialgenerators.MaterialGenerators;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MaterialGeneratorsInit {
    public static void setup() {
        Path configPath = FMLPaths.CONFIGDIR.get().resolve(MaterialGenerators.MOD_ID);
        Path jsonPath = configPath.resolve("generate_items.json");

        if (!Files.exists(configPath)) {
            try {
                Files.createDirectories(configPath);
            } catch (IOException e) {
                MaterialGenerators.LOGGER.error("Could not create config directory", e);
            }
        }

        if (!Files.exists(jsonPath)) {
            tryCreateJson(jsonPath);
        }
    }

    private static void tryCreateJson(Path path) {
        JsonArray defaults = createDefaults();

        JsonObject o = new JsonObject();
        o.add("ingredients", defaults);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(o, writer);
            MaterialGenerators.LOGGER.info("Created default {}", path);
        } catch (IOException e) {
            MaterialGenerators.LOGGER.error("Failed to create default {}", path, e);
        }
    }

    private static JsonArray createDefaults() {
        JsonArray defaults = new JsonArray();

        defaults.add(tag(Tags.Items.INGOTS));
        defaults.add(tag(Tags.Items.GEMS));
        defaults.add(tag(Tags.Items.DUSTS));
        defaults.add(tag(Tags.Items.OBSIDIAN));
        defaults.add(tag(ItemTags.WOOL));
        defaults.add(tag(Tags.Items.STRING));
        defaults.add(tag(Tags.Items.ENDER_PEARLS));
        defaults.add(tag(Tags.Items.EGGS));
        defaults.add(tag(ItemTags.ARROWS));
        defaults.add(item(Items.BEEF));
        defaults.add(item(Items.PORKCHOP));
        defaults.add(item(Items.MUTTON));
        defaults.add(item(Items.CHICKEN));
        defaults.add(item(Items.RABBIT));
        defaults.add(tag(ItemTags.FISHES));
        defaults.add(item(Items.ROTTEN_FLESH));
        defaults.add(item(Items.SPIDER_EYE));
        defaults.add(tag(ItemTags.COALS));
        defaults.add(item(Items.NETHERITE_SCRAP));
        defaults.add(tag(Tags.Items.BONES));
        defaults.add(tag(Tags.Items.RODS));
        defaults.add(tag(Tags.Items.FEATHERS));
        defaults.add(tag(Tags.Items.LEATHER));
        defaults.add(item(Items.RABBIT_HIDE));
        defaults.add(item(Items.HONEYCOMB));
        defaults.add(item(Items.INK_SAC));
        defaults.add(item(Items.GLOW_INK_SAC));
        defaults.add(tag(Tags.Items.SLIMEBALLS));
        defaults.add(item(Items.CLAY_BALL));
        defaults.add(item(Items.SNOWBALL));
        defaults.add(tag(Tags.Items.NETHER_STARS));
        defaults.add(item(Items.SHULKER_SHELL));
        defaults.add(item(Items.ECHO_SHARD));
        defaults.add(tag(Tags.Items.GUNPOWDER));
        defaults.add(item(Items.RABBIT_FOOT));
        defaults.add(item(Items.MAGMA_CREAM));
        defaults.add(item(Items.GHAST_TEAR));
        defaults.add(item(Items.PHANTOM_MEMBRANE));
        defaults.add(tag(ItemTags.LOGS));
        defaults.add(tag(Tags.Items.STONE));
        defaults.add(tag(Tags.Items.COBBLESTONE));
        defaults.add(tag(ItemTags.DIRT));
        defaults.add(tag(Tags.Items.SAND));
        defaults.add(tag(Tags.Items.NETHERRACK));
        defaults.add(item(Items.BLACKSTONE));
        defaults.add(item(Items.BASALT));
        defaults.add(tag(Tags.Items.END_STONES));
        defaults.add(tag(ItemTags.TERRACOTTA));
        defaults.add(tag(Tags.Items.GRAVEL));
        defaults.add(item(Items.ICE));
        defaults.add(item(Items.SOUL_SAND));
        defaults.add(tag(Tags.Items.MUSHROOMS));
        defaults.add(item(Items.CRIMSON_FUNGUS));
        defaults.add(item(Items.WARPED_FUNGUS));
        defaults.add(tag(ItemTags.FLOWERS));
        defaults.add(item(Items.BAMBOO));
        defaults.add(item(Items.SUGAR_CANE));
        defaults.add(item(Items.CACTUS));
        defaults.add(item(Items.GLOW_BERRIES));
        defaults.add(item(Items.SWEET_BERRIES));
        defaults.add(tag(Tags.Items.CROPS));
        defaults.add(item(Items.KELP));
        defaults.add(item(Items.MELON));
        defaults.add(item(Items.PUMPKIN));
        defaults.add(item(Items.APPLE));
        defaults.add(item(Items.CHORUS_FRUIT));

        return defaults;
    }

    private static JsonObject item(Item item) {
        return new Ingredient.ItemValue(new ItemStack(item)).serialize();
    }

    private static JsonObject tag(TagKey<Item> tag) {
        return new Ingredient.TagValue(tag).serialize();
    }
}
