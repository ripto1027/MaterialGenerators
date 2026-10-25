package stan.ripto.materialgenerators.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;

import java.util.List;

public abstract class AbstractCardItem extends Item {
    private final String translateKey;

    public AbstractCardItem(Properties properties, String translateKey) {
        super(properties);
        this.translateKey = translateKey;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltips, isAdvanced);
        tooltips.add(Component.translatable(this.translateKey).withStyle(ChatFormatting.GRAY));
    }

    public abstract void useOnGenerator(ItemStack heldCard, GeneratorBlockEntity blockentity, Player player);
}
