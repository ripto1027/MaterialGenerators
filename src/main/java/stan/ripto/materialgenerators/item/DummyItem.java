package stan.ripto.materialgenerators.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.datagen.client.lang.TranslateKeys;

import java.util.List;

public class DummyItem extends Item {
    public DummyItem(Properties property) {
        super(property);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltips, isAdvanced);
        tooltips.add(Component.translatable(TranslateKeys.DUMMY_TOOLTIP));
    }
}
