package stan.ripto.materialgenerators.datagen.client.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;

public class MaterialGeneratorsLanguageProvider {
    public static class English extends LanguageProvider {
        public English(PackOutput output) {
            super(output, MaterialGenerators.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            addBlock(MaterialGeneratorsBlocks.GENERATOR, "Material Generator");
            addItem(MaterialGeneratorsItems.DUMMY, "Dummy");
            addItem(MaterialGeneratorsItems.C_CARD, "C Card");
            addItem(MaterialGeneratorsItems.I_CARD, "I Card");
            add(TranslateKeys.DUMMY_TOOLTIP, "Place an item here to be generated.\nSee config/materialgenerators/generate_items.json to edit the list.");
            add(TranslateKeys.GENERATOR_TOOLTIP1, "Generate Item: %1$s");
            add(TranslateKeys.GENERATOR_TOOLTIP2, "Generate Count: %1$d/%2$d tick");
        }
    }

    public static class Japanese extends LanguageProvider {
        public Japanese(PackOutput output) {
            super(output, MaterialGenerators.MOD_ID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            addBlock(MaterialGeneratorsBlocks.GENERATOR, "素材生成機");
            addItem(MaterialGeneratorsItems.DUMMY, "ダミー");
            addItem(MaterialGeneratorsItems.C_CARD, "Cカード");
            addItem(MaterialGeneratorsItems.I_CARD, "Iカード");
            add(TranslateKeys.DUMMY_TOOLTIP, "ここに置いたアイテムが生成されます。\n生成リストはconfig/materialgenerators/generate_items.jsonから確認できます。");
            add(TranslateKeys.GENERATOR_TOOLTIP1, "生成アイテム: %1$s");
            add(TranslateKeys.GENERATOR_TOOLTIP2, "生成数: %1$d/%2$d tick");
        }
    }
}
