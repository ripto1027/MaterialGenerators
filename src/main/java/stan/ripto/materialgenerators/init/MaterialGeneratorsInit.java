package stan.ripto.materialgenerators.init;

import com.google.gson.*;
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

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaults, writer);
            MaterialGenerators.LOGGER.info("Created default {}", path);
        } catch (IOException e) {
            MaterialGenerators.LOGGER.error("Failed to create default {}", path, e);
        }
    }

    private static JsonArray createDefaults() {
        JsonArray defaults = new JsonArray();

        defaults.add(tag("forge:ingots"));
        defaults.add(tag("forge:gems"));
        defaults.add(tag("forge:dusts"));
        defaults.add(item("minecraft:obsidian"));
        defaults.add(tag("minecraft:wool"));
        defaults.add(item("minecraft:string"));
        defaults.add(item("minecraft:ender_pearl"));
        defaults.add(tag("forge:eggs"));
        defaults.add(item("minecraft:arrow"));
        defaults.add(item("minecraft:beef"));
        defaults.add(item("minecraft:porkchop"));
        defaults.add(item("minecraft:mutton"));
        defaults.add(item("minecraft:chicken"));
        defaults.add(item("minecraft:rabbit"));
        defaults.add(tag("minecraft:fishes"));
        defaults.add(item("minecraft:rotten_flesh"));
        defaults.add(item("minecraft:spider_eye"));
        defaults.add(tag("minecraft:coals"));
        defaults.add(item("minecraft:netherite_scrap"));
        defaults.add(tag("forge:bones"));
        defaults.add(tag("forge:rods"));
        defaults.add(tag("forge:feathers"));
        defaults.add(tag("forge:leather"));
        defaults.add(item("minecraft:rabbit_hide"));
        defaults.add(item("minecraft:honeycomb"));
        defaults.add(item("minecraft:ink_sac"));
        defaults.add(item("minecraft:glow_ink_sac"));
        defaults.add(tag("forge:slimeballs"));
        defaults.add(item("minecraft:clay_ball"));
        defaults.add(item("minecraft:snowball"));
        defaults.add(item("minecraft:nether_star"));
        defaults.add(item("minecraft:shulker_shell"));
        defaults.add(item("minecraft:echo_shard"));
        defaults.add(item("minecraft:gunpowder"));
        defaults.add(item("minecraft:rabbit_foot"));
        defaults.add(item("minecraft:magma_cream"));
        defaults.add(item("minecraft:ghast_tear"));
        defaults.add(item("minecraft:phantom_membrane"));
        defaults.add(tag("minecraft:logs"));
        defaults.add(tag("forge:stone"));
        defaults.add(tag("forge:cobblestone"));
        defaults.add(tag("minecraft:dirt"));
        defaults.add(tag("forge:sand"));
        defaults.add(item("minecraft:netherrack"));
        defaults.add(item("minecraft:blackstone"));
        defaults.add(item("minecraft:basalt"));
        defaults.add(item("minecraft:end_stone"));
        defaults.add(tag("minecraft:terracotta"));
        defaults.add(item("minecraft:gravel"));
        defaults.add(item("minecraft:ice"));
        defaults.add(item("minecraft:soul_sand"));
        defaults.add(tag("forge:mushrooms"));
        defaults.add(item("minecraft:crimson_fungus"));
        defaults.add(item("minecraft:warped_fungus"));
        defaults.add(tag("minecraft:flowers"));
        defaults.add(item("minecraft:bamboo"));
        defaults.add(item("minecraft:sugar_cane"));
        defaults.add(item("minecraft:cactus"));
        defaults.add(item("minecraft:glow_berries"));
        defaults.add(item("minecraft:sweet_berries"));
        defaults.add(tag("forge:crops"));
        defaults.add(item("minecraft:kelp"));
        defaults.add(item("minecraft:melon"));
        defaults.add(item("minecraft:pumpkin"));
        defaults.add(item("minecraft:apple"));
        defaults.add(item("minecraft:chorus_fruit"));
        return defaults;
    }

    private static JsonObject item(String value) {
        JsonObject ob = new JsonObject();
        ob.addProperty("item", value);
        return ob;
    }

    private static JsonObject tag(String value) {
        JsonObject ob = new JsonObject();
        ob.addProperty("tag", value);
        return ob;
    }
}
