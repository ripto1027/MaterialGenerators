package stan.ripto.materialgenerators.datagen.server.loot.global_loot;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.item.Cards;
import stan.ripto.materialgenerators.loot.AddItemModifier;

public class MaterialGeneratorsGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public MaterialGeneratorsGlobalLootModifierProvider(PackOutput output) {
        super(output, MaterialGenerators.MOD_ID);
    }

    @Override
    protected void start() {
        for (EntityType<?> entity : ForgeRegistries.ENTITY_TYPES.getValues()) {
            if (entity.getCategory().equals(MobCategory.MONSTER)) {
                for (Cards card : Cards.values()) {
                    add(entity, card);
                }
            }
        }
    }

    private void add(EntityType<?> entity, Cards card) {
        String name = EntityType.getKey(entity).getPath();
        ResourceLocation location = card.getRegistryItem().getId();
        if (location != null) {
            add(
                card.getName() + "_from_" + name,
                new AddItemModifier(
                    new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + name)).build(),
                        LootItemKilledByPlayerCondition.killedByPlayer().build(),
                        LootItemRandomChanceWithLootingCondition
                                .randomChanceAndLootingBoost(card.getRate(), 0.02F).build()
                    },
                    card.getItem()
                )
            );
        } else {
            MaterialGenerators.LOGGER.error("MaterialGeneratorsGlobalLootModifierProvider ResourceLocation Null Error");
        }
    }
}
