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
        JsonArray defaults = new JsonArray();
        defaults.add("#forge:ingots");
        defaults.add("#forge:gems");
        defaults.add("#forge:dusts");
        defaults.add("minecraft:obsidian");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaults, writer);
            MaterialGenerators.LOGGER.info("Created default {}", path);
        } catch (IOException e) {
            MaterialGenerators.LOGGER.error("Failed to create default {}", path, e);
        }
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
        }
    }
}
