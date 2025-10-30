package stan.ripto.materialgenerators.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import stan.ripto.materialgenerators.init.MaterialGeneratorsInit;

import java.util.HashSet;
import java.util.Set;

public class MaterialGeneratorsServerStarting {
    private static final Set<Item> stackSet = new HashSet<>();

    @SuppressWarnings("unused")
    public static void onServerStarting(ServerStartingEvent event) {
        for (String id : MaterialGeneratorsInit.IDS) {
            getStackFromId(id);
        }
    }

    public static Set<Item> getStackSet() {
        return stackSet;
    }

    private static void getStackFromId(String id) {
        if (id.startsWith("#")) {
            ResourceLocation location = ResourceLocation.parse(id.substring(1));
            TagKey<Item> keys = TagKey.create(ForgeRegistries.Keys.ITEMS, location);
            ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
            if (manager != null) {
                manager.getTag(keys).forEach(stackSet::add);
            }
        } else {
            ResourceLocation location = ResourceLocation.parse(id);
            Item item = ForgeRegistries.ITEMS.getValue(location);
            if (item != null) {
                stackSet.add(item);
            }
        }
    }
}
