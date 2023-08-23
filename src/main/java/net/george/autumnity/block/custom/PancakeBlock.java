package net.george.autumnity.block.custom;

import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.effect.AutumnityEffects;
import net.george.autumnity.event.AutumnityEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PancakeBlock extends Block {
    public static final IntProperty PANCAKES = IntProperty.of("pancakes", 1, 32);
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)};

    public PancakeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PANCAKES, 2));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = Math.floorDiv(state.get(PANCAKES) - 1, 2);
        return SHAPES[i];
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.getBlock() == this) {
            return state.with(PANCAKES, Math.min(32, state.get(PANCAKES) + 2));
        } else {
            BlockState blockState = context.getWorld().getBlockState(context.getBlockPos().down());
            if (blockState.getBlock() == this && blockState.get(PANCAKES) == 31) {
                context.getWorld().setBlockState(context.getBlockPos().down(), blockState.with(PANCAKES, 32), 11);
                return this.getDefaultState().with(PANCAKES, 1);
            } else {
                return super.getPlacementState(context);
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (player.isSneaking()) {
            if (world.getBlockState(pos.up()).getBlock() != this) {
                if (state.get(PANCAKES) > 1) {
                    if (!world.isClient) {
                        int i = state.get(PANCAKES);

                        Block.dropStack(world, pos, new ItemStack(this.asItem()));

                        if (i > 2) {
                            world.setBlockState(pos, state.with(PANCAKES, i - 2), 3);
                        } else {
                            world.removeBlock(pos, false);
                        }
                    }
                    return ActionResult.SUCCESS;
                } else if (world.getBlockState(pos.down()).getBlock() == this &&
                        world.getBlockState(pos.down()).get(PANCAKES) == 32) {
                    if (!world.isClient) {
                        Block.dropStack(world, pos, new ItemStack(this.asItem()));

                        world.setBlockState(pos.down(), state.with(PANCAKES, 31), 3);
                        world.removeBlock(pos, false);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        } else if (player.getStackInHand(hand).getItem() != AutumnityBlocks.PANCAKE.asItem()) {
            if (world.isClient) {
                if (this.eatCake(world, pos, state, player, itemStack) == ActionResult.SUCCESS) {
                    return ActionResult.SUCCESS;
                }

                if (itemStack.isEmpty()) {
                    return ActionResult.CONSUME;
                }
            }

            return this.eatCake(world, pos, state, player, itemStack);
        }

        return ActionResult.PASS;
    }

    private ActionResult eatCake(World world, BlockPos pos, BlockState state, PlayerEntity player, ItemStack itemStack) {
        int i = state.get(PANCAKES);
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else if (i < 31 && itemStack.getItem() == this.asItem()) {
            return ActionResult.PASS;
        } else if (world.getBlockState(pos.up()).getBlock() == this) {
            return ActionResult.PASS;
        } else {
            ItemStack stack = this.getPickStack(world, pos, state);
            player.playSound(player.getEatSound(stack), 1.0F, 1.0F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);
            player.getHungerManager().add(4, 0.3F);
            if (i > 1) {
                world.setBlockState(pos, state.with(PANCAKES, i - 1), 3);
            } else {
                world.removeBlock(pos, false);
            }

            if (player.hasStatusEffect(AutumnityEffects.FOUL_TASTE)) {
                player.getHungerManager().add(2, 0.0F);
                AutumnityEvents.OnPlayerEaten.updateFoulTaste(player);
            }

            return ActionResult.SUCCESS;
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == this.asItem() &&
                state.get(PANCAKES) < 31 || super.canReplace(state, context);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PANCAKES);
    }

    protected boolean isValidGround(BlockState state, BlockView world, BlockPos pos) {
        return !state.getCollisionShape(world, pos).getFace(Direction.UP).isEmpty() || (state.getBlock() == this && state.get(PANCAKES) >= 31);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.isValidGround(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() :
                super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return (int) Math.ceil((double) state.get(PANCAKES) / 2);
    }
}
