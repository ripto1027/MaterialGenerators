package stan.ripto.materialgenerators.datagen.client.item;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;

public class MaterialGeneratorsItemModelProvider extends ItemModelProvider {
    public MaterialGeneratorsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MaterialGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MaterialGeneratorsItems.DUMMY.get());
        basicItem(MaterialGeneratorsItems.C_CARD.get());
        basicItem(MaterialGeneratorsItems.I_CARD.get());
    }
}
