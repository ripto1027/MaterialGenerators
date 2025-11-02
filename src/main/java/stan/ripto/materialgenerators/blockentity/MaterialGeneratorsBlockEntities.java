package stan.ripto.materialgenerators.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.materialgenerators.MaterialGenerators;
import stan.ripto.materialgenerators.block.MaterialGeneratorsBlocks;

public class MaterialGeneratorsBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MaterialGenerators.MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR = BLOCK_ENTITIES.register(
            "generator",
            () -> BlockEntityType.Builder.of(
                    GeneratorBlockEntity::new,
                    MaterialGeneratorsBlocks.GENERATOR.get()
            ).build(null)
    );

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
