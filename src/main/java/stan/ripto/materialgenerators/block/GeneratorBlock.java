package stan.ripto.materialgenerators.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import stan.ripto.materialgenerators.block_entity.GeneratorBlockEntity;
import stan.ripto.materialgenerators.block_entity.MaterialGeneratorsBlockEntities;
import stan.ripto.materialgenerators.datagen.client.lang.TranslateKeys;
import stan.ripto.materialgenerators.util.GenerateItemHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class GeneratorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected GeneratorBlock(Properties property) {
        super(property);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        if (!pLevel.isClientSide) {
            return createTickerHelper(
                    type,
                    MaterialGeneratorsBlockEntities.GENERATOR.get(),
                    (level, pos, state, tile) -> tile.tick(level, pos)
            );
        }
        return null;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = new ArrayList<>();
        ItemStack stack = new ItemStack(this);
        BlockEntity tile = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (tile instanceof GeneratorBlockEntity gen) {
            CompoundTag tag = stack.getOrCreateTag();
            gen.saveAdditional(tag);
            gen.setChanged();
        }
        drops.add(stack);
        return drops;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            ItemStack stack = player.getMainHandItem();
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof GeneratorBlockEntity gen) {
                if (!stack.isEmpty()) {
                    GenerateItemHandler.generateItemHandler(gen, stack);
                } else {
                    if (gen.getGenerateItem() != null) {
                        player.sendSystemMessage(Component.translatable(TranslateKeys.GENERATOR_TOOLTIP1, gen.getGenerateItemName()));
                    }
                    player.sendSystemMessage(Component.translatable(TranslateKeys.GENERATOR_TOOLTIP2, gen.getGenerateCount(), gen.getCoolTime()));
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.getBlockEntity(pos) instanceof GeneratorBlockEntity genTile) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("generate_item")) {
                ResourceLocation location = ResourceLocation.parse(tag.getString("generate_item"));
                Item item = ForgeRegistries.ITEMS.getValue(location);
                genTile.setGenerateItem(item);
            }
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentState, Direction pDirection) {
        return pAdjacentState.is(this) || super.skipRendering(pState, pAdjacentState, pDirection);
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}
