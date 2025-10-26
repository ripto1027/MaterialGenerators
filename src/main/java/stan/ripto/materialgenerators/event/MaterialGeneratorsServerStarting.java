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
    private static final Set<Item> stackSet = new HashSet<>();
    private static Stream<ItemStack> stackStream;

    @SuppressWarnings("unused")
    public static void onServerStarting(ServerStartingEvent event) {
        Set<ItemStack> stacks = new HashSet<>();
        for (String id : MaterialGeneratorsInit.IDS) {
            stacks.addAll(getStackFromId(id));
        }
        stackStream = stacks.stream();
    }

    public static Set<Item> getStackSet() {
        return stackSet;
    }

    public static Stream<ItemStack> getStackStream() {
        return stackStream;
    }

    private static Set<ItemStack> getStackFromId(String id) {
        Set<ItemStack> stacks = new HashSet<>();
        if (id.startsWith("#")) {
            ResourceLocation location = ResourceLocation.parse(id.substring(1));
            TagKey<Item> keys = TagKey.create(ForgeRegistries.Keys.ITEMS, location);
            ITagManager<Item> manager = ForgeRegistries.ITEMS.tags();
            if (manager != null) {
                manager.getTag(keys).forEach(element -> {
                    stackSet.add(element);
                    stacks.add(element.getDefaultInstance());
                });
            }
        } else {
            ResourceLocation location = ResourceLocation.parse(id);
            Item item = ForgeRegistries.ITEMS.getValue(location);
            if (item != null) {
                stackSet.add(item);
                stacks.add(item.getDefaultInstance());
            }
        }
        return stacks;
    }
}
