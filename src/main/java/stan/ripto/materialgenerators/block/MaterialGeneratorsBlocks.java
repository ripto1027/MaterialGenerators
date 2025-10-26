package stan.ripto.materialgenerators.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.item.GeneratorBlockItem;
import stan.ripto.materialgenerators.item.MaterialGeneratorsItems;

import java.util.function.BiFunction;
import java.util.function.Function;

public class MaterialGeneratorsBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MaterialGenerators.MOD_ID);

    private static final BlockBehaviour.Properties GEN_PROPERTY =
            BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(2.0F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn(MaterialGeneratorsBlocks::never)
                    .isRedstoneConductor(MaterialGeneratorsBlocks::never)
                    .isSuffocating(MaterialGeneratorsBlocks::never)
                    .isViewBlocking(MaterialGeneratorsBlocks::never);

    public static final RegistryObject<GeneratorBlock> GENERATOR = register(
            "generator",
            GeneratorBlock::new,
            GEN_PROPERTY,
            GeneratorBlockItem::new,
            new Item.Properties()
    );

    @SuppressWarnings("SameParameterValue")
    private static <B extends Block, I extends BlockItem> RegistryObject<B> register(
            String name,
            Function<BlockBehaviour.Properties, B> bFunction,
            BlockBehaviour.Properties bProperty,
            BiFunction<Block, Item.Properties, I> iFunction,
            Item.Properties iProperty
    ) {
        RegistryObject<B> reg = BLOCKS.register(name, () -> bFunction.apply(bProperty));
        MaterialGeneratorsItems.ITEMS.register(name, () -> iFunction.apply(reg.get(), iProperty));
        return reg;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }
}
