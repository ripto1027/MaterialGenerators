package stan.ripto.materialgenerators.util;

import net.minecraft.world.item.ItemStack;

public class GenerationData {
    public static final int BASE_MAX_STACK_SIZE = 64;

    private ItemStack item;
    private int rate;
    private int count;

    public GenerationData(ItemStack item, int rate) {
        this.item = item;
        this.rate = rate;
        this.count = 0;
    }

    public static int calcCount(int rate, ItemStack item) {
        return (int) Math.ceil((double) (rate * item.getMaxStackSize()) / BASE_MAX_STACK_SIZE);
    }

    public boolean swapGenerationItem(ItemStack item) {
        if (item.isEmpty()) {
            this.item = ItemStack.EMPTY;
            this.count = 0;
            return true;
        }

        if (!GeneratableItems.isGeneratable(item.getItem())) {
            return false;
        }

        this.item = item;
        this.count = calcCount(this.rate, item);
        return true;
    }

    public int incRate(int value) {
        if (this.rate >= BASE_MAX_STACK_SIZE) return value;

        int ret = 0;
        int tmprate = this.rate + value;

        if (tmprate > BASE_MAX_STACK_SIZE) {
            ret = tmprate - BASE_MAX_STACK_SIZE;
            this.rate = BASE_MAX_STACK_SIZE;
        } else {
            this.rate = tmprate;
        }

        this.count = calcCount(this.rate, this.item);
        return ret;
    }

    public ItemStack item() {
        return this.item;
    }

    public void item(ItemStack item) {
        this.item = item;
    }

    public int rate() {
        return this.rate;
    }

    public void rate(int value) {
        this.rate = value;
        this.count = calcCount(value, this.item);
    }

    public int count() {
        return  this.count;
    }

    public boolean isEmpty() {
        return this.item.isEmpty();
    }
}
