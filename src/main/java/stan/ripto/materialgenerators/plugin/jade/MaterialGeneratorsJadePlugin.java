package stan.ripto.materialgenerators.plugin.jade;

import snownee.jade.api.*;
import stan.ripto.materialgenerators.block.GeneratorBlock;
import stan.ripto.materialgenerators.blockentity.GeneratorBlockEntity;

import java.awt.*;

@SuppressWarnings("unused")
@WailaPlugin
public class MaterialGeneratorsJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(GeneratorJadeProvider.INSTANCE, GeneratorBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(GeneratorJadeProvider.INSTANCE, GeneratorBlock.class);
    }
}
