package stan.ripto.materialgenerators.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;

public class MaterialGeneratorsTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MaterialGenerators.MOD_ID);

    public static void register(IEventBus bus) {
        TABS.register(
                "materialgenerators_tab",
                () -> CreativeModeTab.builder()
                        .title(Component.literal("Material Generators"))
                        .icon(() -> new ItemStack(MaterialGeneratorsBlocks.GENERATOR.get()))
                        .displayItems((param, output) -> {
                            output.accept(MaterialGeneratorsBlocks.GENERATOR.get());
                            output.accept(MaterialGeneratorsItems.I_CARD.get());
                            output.accept(MaterialGeneratorsItems.C_CARD.get());
                        })
                        .build()
        );
        TABS.register(bus);
    }
}
