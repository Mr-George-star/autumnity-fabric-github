package net.george.autumnity.block.custom;

import net.fabricmc.loader.api.FabricLoader;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.effect.AutumnityEffects;
import net.george.autumnity.entity.special.FallingHeadBlockEntity;
import net.george.autumnity.event.AutumnityEvents;
import net.george.autumnity.item.AutumnityFoodComponents;
import net.george.autumnity.item.AutumnityItems;
import net.george.autumnity.sound.AutumnitySounds;
import net.george.autumnity.util.AutumnityTags;
import net.george.autumnity.util.BuiltinUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TurkeyBlock extends FallingBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty CHUNKS = IntProperty.of("chunks", 0, 4);
    public static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 16.0D),
            Block.createCuboidShape(1.0D, 0.0D, 2.0D, 13.0D, 8.0D, 16.0D),
            Block.createCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
            Block.createCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 10.0D),
            Block.createCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 6.0D)};
    public static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 8.0D, 14.0D),
            Block.createCuboidShape(3.0D, 0.0D, 0.0D, 15.0D, 8.0D, 14.0D),
            Block.createCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
            Block.createCuboidShape(3.0D, 0.0D, 6.0D, 13.0D, 8.0D, 14.0D),
            Block.createCuboidShape(3.0D, 0.0D, 10.0D, 13.0D, 8.0D, 14.0D)};
    public static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Block.createCuboidShape(2.0D, 0.0D, 1.0D, 16.0D, 8.0D, 15.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 16.0D, 8.0D, 15.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 10.0D, 8.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 6.0D, 8.0D, 13.0D)};
    public static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Block.createCuboidShape(0.0D, 0.0D, 1.0D, 14.0D, 8.0D, 15.0D),
            Block.createCuboidShape(0.0D, 0.0D, 1.0D, 14.0D, 8.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
            Block.createCuboidShape(6.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
            Block.createCuboidShape(10.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D)};

    public TurkeyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(CHUNKS, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = state.get(CHUNKS);

        switch (state.get(FACING)) {
            case SOUTH -> {
                return SOUTH_SHAPE[i];
            }
            case WEST -> {
                return WEST_SHAPE[i];
            }
            case EAST -> {
                return EAST_SHAPE[i];
            }
            default -> {
                return NORTH_SHAPE[i];
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= BuiltinUtil.MIN_BUILD_HEIGHT) {
            FallingHeadBlockEntity turkey;
            if (state.get(CHUNKS) == 0) {
                turkey = FallingHeadBlockEntity.spawnFromBlock(world, pos.add(0.5D, 0.0D, 0.5D), state, true);
            } else {
                turkey = FallingHeadBlockEntity.spawnFromBlock(world, pos, state, false);
            }

            this.configureFallingBlockEntity(turkey);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            if (this.eatTurkey(state, player, world, pos, hand) == ActionResult.SUCCESS) {
                return ActionResult.SUCCESS;
            }
            if (player.getStackInHand(hand).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return this.eatTurkey(state, player, world, pos, hand);
    }

    public ActionResult eatTurkey(BlockState state, PlayerEntity player, World world, BlockPos pos, Hand hand){
        int i = state.get(CHUNKS);
        boolean has_cutter = FabricLoader.getInstance().isModLoaded("farmersdelight") ? player.getStackInHand(hand).isIn(AutumnityTags.Items.KNIVES) : player.getStackInHand(hand).getItem() instanceof AxeItem;
        if (player.canConsume(false) || has_cutter) {
            if (has_cutter) {
                Block.dropStack(world, pos, new ItemStack(getLegItem()));
                world.playSound(player, pos, AutumnitySounds.BLOCK_TURKEY_CUT, SoundCategory.BLOCKS,
                        1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);
            } else {
                player.playSound(player.getEatSound(new ItemStack(AutumnityBlocks.TURKEY)),
                        1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.4F + 1.0F);
                this.restoreHunger(world, player);
            }

            if (i < 4) {
                world.setBlockState(pos, state.with(CHUNKS, i + 1));
            } else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    public void restoreHunger(World world, PlayerEntity player) {
        player.getHungerManager().add(AutumnityFoodComponents.TURKEY.getHunger(),
                AutumnityFoodComponents.TURKEY.getSaturationModifier());
        if (!world.isClient() && world.getRandom().nextFloat() < 0.1F) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0));
        }

        int i = AutumnityFoodComponents.TURKEY.getHunger();
        int j = i == 1 ? i : (int) (i * 0.5F);

        if (player.hasStatusEffect(AutumnityEffects.FOUL_TASTE)) {
            player.getHungerManager().add(j, 0.0F);
            AutumnityEvents.OnPlayerEaten.updateFoulTaste(player);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CHUNKS);
    }

    public Item getLegItem() {
        return AutumnityItems.TURKEY_PIECE;
    }
}
