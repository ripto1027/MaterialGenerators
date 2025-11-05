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
            addItem(MaterialGeneratorsItems.C_CARD, "C Card");
            addItem(MaterialGeneratorsItems.I_CARD, "I Card");
            add(TranslateKeys.GENERATOR_TOOLTIP1, "Generate Item: %1$s");
            add(TranslateKeys.GENERATOR_TOOLTIP2, "Generate Count: %1$d/%2$d tick");
            add(TranslateKeys.I_CARD_TOOLTIP, "Using this item on a generator block increases\nthe amount of items it produces per use.");
            add(TranslateKeys.C_CARD_TOOLTIP, "Using this item on a generator block reduces\nthe time required for each generation cycle.");
            add(TranslateKeys.JADE_CONFIG_PLUGIN, "Generator Info");
        }
    }

    public static class Japanese extends LanguageProvider {
        public Japanese(PackOutput output) {
            super(output, MaterialGenerators.MOD_ID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            addBlock(MaterialGeneratorsBlocks.GENERATOR, "素材生成機");
            addItem(MaterialGeneratorsItems.C_CARD, "Cカード");
            addItem(MaterialGeneratorsItems.I_CARD, "Iカード");
            add(TranslateKeys.GENERATOR_TOOLTIP1, "生成アイテム: %1$s");
            add(TranslateKeys.GENERATOR_TOOLTIP2, "生成数: %1$d/%2$d tick");
            add(TranslateKeys.I_CARD_TOOLTIP, "このアイテムを素材生成機に対して使用すると\n1回あたりのアイテムの生成量を増やせます。");
            add(TranslateKeys.C_CARD_TOOLTIP, "このアイテムを素材生成機に対して使用すると\n1回の生成にかかる時間を短縮できます。");
        }
    }
}
