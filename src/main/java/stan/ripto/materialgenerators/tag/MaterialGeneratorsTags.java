package stan.ripto.materialgenerators.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import stan.ripto.materialgenerators.MaterialGenerators;

public class MaterialGeneratorsTags {
    public static class Items {
        public static final TagKey<Item> CARDS =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath(MaterialGenerators.MOD_ID, "cards"));
    }
}
