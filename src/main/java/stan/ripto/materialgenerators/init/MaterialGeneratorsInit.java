package stan.ripto.materialgenerators.init;

import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;
import stan.ripto.materialgenerators.MaterialGenerators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class MaterialGeneratorsInit {
    public static final Set<String> IDS = new HashSet<>();

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

        tryLoadJson(jsonPath);
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
        defaults.add("#forge:ingots");
        defaults.add("#forge:gems");
        defaults.add("#forge:dusts");
        defaults.add("minecraft:obsidian");
        defaults.add("#minecraft:wool");
        defaults.add("minecraft:string");
        defaults.add("minecraft:ender_pearl");
        defaults.add("#forge:eggs");
        defaults.add("minecraft:arrow");
        defaults.add("minecraft:beef");
        defaults.add("minecraft:porkchop");
        defaults.add("minecraft:mutton");
        defaults.add("minecraft:chicken");
        defaults.add("minecraft:rabbit");
        defaults.add("#minecraft:fishes");
        defaults.add("minecraft:rotten_flesh");
        defaults.add("minecraft:spider_eye");
        defaults.add("#minecraft:coals");
        defaults.add("minecraft:netherite_scrap");
        defaults.add("#forge:bones");
        defaults.add("#forge:rods");
        defaults.add("#forge:feathers");
        defaults.add("#forge:leather");
        defaults.add("minecraft:rabbit_hide");
        defaults.add("minecraft:honeycomb");
        defaults.add("minecraft:ink_sac");
        defaults.add("minecraft:glow_ink_sac");
        defaults.add("#forge:slimeballs");
        defaults.add("minecraft:clay_ball");
        defaults.add("minecraft:snowball");
        defaults.add("minecraft:nether_star");
        defaults.add("minecraft:shulker_shell");
        defaults.add("minecraft:echo_shard");
        defaults.add("minecraft:gunpowder");
        defaults.add("minecraft:rabbit_foot");
        defaults.add("minecraft:magma_cream");
        defaults.add("minecraft:ghast_tear");
        defaults.add("minecraft:phantom_membrane");
        defaults.add("#minecraft:logs");
        defaults.add("#forge:stone");
        defaults.add("#forge:cobblestone");
        defaults.add("#minecraft:dirt");
        defaults.add("#forge:sand");
        defaults.add("minecraft:netherrack");
        defaults.add("minecraft:blackstone");
        defaults.add("minecraft:basalt");
        defaults.add("minecraft:end_stone");
        defaults.add("#minecraft:terracotta");
        defaults.add("minecraft:gravel");
        defaults.add("minecraft:ice");
        defaults.add("minecraft:soul_sand");
        defaults.add("#forge:mushrooms");
        defaults.add("minecraft:crimson_fungus");
        defaults.add("minecraft:warped_fungus");
        defaults.add("minecraft:flowers");
        defaults.add("minecraft:bamboo");
        defaults.add("minecraft:sugar_cane");
        defaults.add("minecraft:cactus");
        defaults.add("minecraft:glow_berries");
        defaults.add("minecraft:sweet_berries");
        defaults.add("#forge:crops");
        defaults.add("minecraft:kelp");
        defaults.add("minecraft:melon");
        defaults.add("minecraft:pumpkin");
        defaults.add("minecraft:apple");
        defaults.add("minecraft:chorus_fruit");
        return defaults;
    }

    private static void tryLoadJson(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            IDS.clear();
            for (JsonElement element : array) {
                if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                    IDS.add(element.getAsString());
                }
            }
            MaterialGenerators.LOGGER.info("Loaded {} item ids from {}", IDS.size(), path);
        } catch (IOException e) {
            MaterialGenerators.LOGGER.error("Failed to read {}", path, e);
        } catch (JsonParseException e) {
            MaterialGenerators.LOGGER.error("The JSON file is corrupted: {}. Please delete the JSON file and restart the game. (Error details: {})", path, e.getMessage());
        }
    }
}
