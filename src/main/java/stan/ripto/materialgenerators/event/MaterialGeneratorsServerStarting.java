package stan.ripto.materialgenerators.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import stan.ripto.materialgenerators.init.MaterialGeneratorsInit;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class MaterialGeneratorsServerStarting {
    private static final Set<Item> itemSet = new HashSet<>();

    @SuppressWarnings("unused")
    public static void onServerStarting(ServerStartingEvent event) {
        for (String id : MaterialGeneratorsInit.IDS) {
            getItemFromId(id);
        }
    }

    public static Stream<ItemStack> getStackStream() {
        Set<ItemStack> stackSet = new HashSet<>();
        itemSet.forEach(item -> stackSet.add(new ItemStack(item)));
        return stackSet.stream();
    }

    private static void getItemFromId(String id) {
        if (id.startsWith("#")) {
            ResourceLocation location = ResourceLocation.tryParse(id.substring(1));
            if (location != null) {
                TagKey<Item> key = TagKey.create(ForgeRegistries.Keys.ITEMS, location);
                ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
                if (manager != null) {
                    manager.getTag(key).forEach(itemSet::add);
                }
            }
        } else {
            ResourceLocation location = ResourceLocation.tryParse(id);
            if (location != null) {
                Item item = ForgeRegistries.ITEMS.getValue(location);
                if (item != null) {
                    itemSet.add(item);
                }
            }
        }
    }
}
