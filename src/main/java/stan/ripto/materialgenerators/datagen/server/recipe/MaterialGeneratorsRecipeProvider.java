package stan.ripto.materialgenerators.datagen.server.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;
import stan.ripto.materialgenerators.recipe.GeneratorRecipeBuilder;

import java.util.function.Consumer;

public class MaterialGeneratorsRecipeProvider extends RecipeProvider {
    public MaterialGeneratorsRecipeProvider(PackOutput output) {
        super(output);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        String groupName = "cards";

        Item db = Items.DIAMOND_BLOCK;
        Item nb = Items.NETHERITE_BLOCK;
        Item nust = Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE;
        Item cc = MaterialGeneratorsItems.C_CARD.get();

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, cc)
                .define('A', db)
                .define('B', nb)
                .define('C', nust)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .group(groupName + "_c_card")
                .unlockedBy(getHasName(db), has(db))
                .unlockedBy(getHasName(nb), has(nb))
                .unlockedBy(getHasName(nust), has(nust))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialGeneratorsItems.I_CARD.get())
                .define('A', db)
                .define('B', nb)
                .define('C', nust)
                .define('D', cc)
                .pattern("BAB")
                .pattern("CDC")
                .pattern("BAB")
                .group(groupName + "_i_card")
                .unlockedBy(getHasName(db), has(db))
                .unlockedBy(getHasName(nb), has(nb))
                .unlockedBy(getHasName(nust), has(nust))
                .unlockedBy(getHasName(cc), has(cc))
                .save(writer);

        generator(writer, "ingots", Tags.Items.INGOTS);
        generator(writer, "gems", Tags.Items.GEMS);
        generator(writer, "dusts", Tags.Items.DUSTS);
        generator(writer, "obsidian", Tags.Items.OBSIDIAN);
        generator(writer, "wool", ItemTags.WOOL);
        generator(writer, "string", Tags.Items.STRING);
        generator(writer, "ender_pearls", Tags.Items.ENDER_PEARLS);
        generator(writer, "eggs", Tags.Items.EGGS);
        generator(writer, "arrows", ItemTags.ARROWS);
        generator(writer, "beef", Items.BEEF);
        generator(writer, "porkchop", Items.PORKCHOP);
        generator(writer, "mutton", Items.MUTTON);
        generator(writer, "chicken", Items.CHICKEN);
        generator(writer, "rabbit", Items.RABBIT);
        generator(writer, "fishes", ItemTags.FISHES);
        generator(writer, "rotten_flesh", Items.ROTTEN_FLESH);
        generator(writer, "spider_eye", Items.SPIDER_EYE);
        generator(writer, "coals", ItemTags.COALS);
        generator(writer, "netherite_scrap", Items.NETHERITE_SCRAP);
        generator(writer, "bones", Tags.Items.BONES);
        generator(writer, "rods", Tags.Items.RODS);
        generator(writer, "feathers", Tags.Items.FEATHERS);
        generator(writer, "leather", Tags.Items.LEATHER);
        generator(writer, "rabbit_hide", Items.RABBIT_HIDE);
        generator(writer, "honeycomb", Items.HONEYCOMB);
        generator(writer, "ink_sac", Items.INK_SAC);
        generator(writer, "glow_ink_sac", Items.GLOW_INK_SAC);
        generator(writer, "slimeballs", Tags.Items.SLIMEBALLS);
        generator(writer, "clay_ball", Items.CLAY_BALL);
        generator(writer, "snowball", Items.SNOWBALL);
        generator(writer, "nether_stars", Tags.Items.NETHER_STARS);
        generator(writer, "shulker_shell", Items.SHULKER_SHELL);
        generator(writer, "echo_shard", Items.ECHO_SHARD);
        generator(writer, "gunpowder", Tags.Items.GUNPOWDER);
        generator(writer, "rabbit_foot", Items.RABBIT_FOOT);
        generator(writer, "magma_cream", Items.MAGMA_CREAM);
        generator(writer, "ghast_tear", Items.GHAST_TEAR);
        generator(writer, "phantom_membrane", Items.PHANTOM_MEMBRANE);
        generator(writer, "logs", ItemTags.LOGS);
        generator(writer, "stone", Tags.Items.STONE);
        generator(writer, "cobblestone", Tags.Items.COBBLESTONE);
        generator(writer, "dirt", ItemTags.DIRT);
        generator(writer, "sand", Tags.Items.SAND);
        generator(writer, "netherrack", Tags.Items.NETHERRACK);
        generator(writer, "blackstone", Items.BLACKSTONE);
        generator(writer, "basalt", Items.BASALT);
        generator(writer, "end_stones", Tags.Items.END_STONES);
        generator(writer, "terracotta", ItemTags.TERRACOTTA);
        generator(writer, "gravel", Tags.Items.GRAVEL);
        generator(writer, "ice", Items.ICE);
        generator(writer, "soul_sand", Items.SOUL_SAND);
        generator(writer, "mushrooms", Tags.Items.MUSHROOMS);
        generator(writer, "crimson_fungus", Items.CRIMSON_FUNGUS);
        generator(writer, "warped_fungus", Items.WARPED_FUNGUS);
        generator(writer, "flowers", ItemTags.FLOWERS);
        generator(writer, "bamboo", Items.BAMBOO);
        generator(writer, "sugar_cane", Items.SUGAR_CANE);
        generator(writer, "cactus", Items.CACTUS);
        generator(writer, "glow_berries", Items.GLOW_BERRIES);
        generator(writer, "sweet_berries", Items.SWEET_BERRIES);
        generator(writer, "crops", Tags.Items.CROPS);
        generator(writer, "kelp", Items.KELP);
        generator(writer, "melon", Items.MELON);
        generator(writer, "pumpkin", Items.PUMPKIN);
        generator(writer, "apple", Items.APPLE);
        generator(writer, "chorus_fruit", Items.CHORUS_FRUIT);
    }

    private void generator(Consumer<FinishedRecipe> writer, String path, ItemLike item) {
        GeneratorRecipeBuilder.create(path, item).save(writer);
    }

    private void generator(Consumer<FinishedRecipe> writer, String path, TagKey<Item> tag) {
        GeneratorRecipeBuilder.create(path, tag).save(writer);
    }
}
