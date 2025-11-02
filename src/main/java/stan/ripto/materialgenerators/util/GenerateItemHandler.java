package stan.ripto.materialgenerators.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;

public class GenerateItemHandler {
    public static void generateItemHandler(GeneratorBlockEntity gen, ItemStack stack) {
        if (isIncreaseCoolTimeItems(gen, stack)) {
            int result1 = 0, result2;
            if (isIncreaseGenerateCountItems(stack)) {
                result1 = gen.setGenerateCount(stack);
            }
            result2 = gen.setCoolTime(stack);
            stack.shrink(Math.max(result1, result2));
        } else if (isIncreaseGenerateCountItems(stack)) {
            int result1 = 0, result2;
            if (isIncreaseCoolTimeItems(gen, stack)) {
                result1 = gen.setCoolTime(stack);
            }
            result2 = gen.setGenerateCount(stack);
            stack.shrink(Math.max(result1, result2));
        }
    }

    private static boolean isIncreaseCoolTimeItems(GeneratorBlockEntity gen, ItemStack stack) {
        return stack.is(gen.getGenerateItem()) || stack.is(MaterialGeneratorsItems.C_CARD.get());
    }

    private static boolean isIncreaseGenerateCountItems(ItemStack stack) {
        return stack.is(Items.NETHER_STAR) || stack.is(MaterialGeneratorsItems.I_CARD.get());
    }
}
