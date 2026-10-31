package stan.ripto.materialgenerators.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;
import stan.ripto.materialgenerators.configs.MaterialGeneratorsServerConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GeneratableItems {
    private static final Set<Item> GENERATABLE_ITEMS = new HashSet<>();
    private static boolean shouldReload = false;

    public static void loadConfig() {
        GENERATABLE_ITEMS.clear();

        List<? extends String> generatableList = MaterialGeneratorsServerConfig.SERVER.generatableList.get();

        IForgeRegistry<Item> itemRegistries = ForgeRegistries.ITEMS;
        ITagManager<Item> itemTagRegistries = Objects.requireNonNull(ForgeRegistries.ITEMS.tags());

        for (String generatable : generatableList) {
            if (!generatable.startsWith("#")) {
                ResourceLocation id = ResourceLocation.tryParse(generatable);
                if (id != null) {
                    GENERATABLE_ITEMS.add(itemRegistries.getValue(id));
                }
            } else {
                ResourceLocation id = ResourceLocation.tryParse(generatable.substring(1));
                if (id != null) {
                    TagKey<Item> itemTagKey = TagKey.create(ForgeRegistries.Keys.ITEMS, id);
                    ITag<Item> items = itemTagRegistries.getTag(itemTagKey);
                    items.forEach(GENERATABLE_ITEMS::add);
                }
            }
        }
    }

    public static void setShouldReload(boolean value) {
        shouldReload = value;
    }

    public static boolean isGeneratable(Item item) {
        if (shouldReload) {
            loadConfig();
            shouldReload = false;
        }

        return GENERATABLE_ITEMS.contains(item);
    }
}
