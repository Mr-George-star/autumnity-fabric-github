package net.george.autumnity.block.custom;

import net.george.autumnity.entity.passive.SnailEntity;
import net.george.autumnity.util.AutumnityTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SnailGooFullBlock extends TransparentBlock {
    public static final BooleanProperty SLIPPERY = BooleanProperty.of("slippery");
    protected static final VoxelShape SHAPE =
            Block.createCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public SnailGooFullBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SLIPPERY, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return !state.get(SLIPPERY) ? SHAPE : super.getCollisionShape(state, world, pos, context);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(SLIPPERY, this.shouldBeSlippery(ctx.getBlockPos(), ctx.getWorld()));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state.with(SLIPPERY, this.shouldBeSlippery(pos, world));
    }

    public final boolean shouldBeSlippery(BlockPos blockPos, BlockView world) {
        for (Direction direction : Direction.values()) {
            BlockPos blockpos1 = blockPos.offset(direction);
            if (this.doesBlockMakeSlippery(blockpos1, world.getBlockState(blockpos1), world)) {
                return true;
            }
        }
        return false;
    }

    public final boolean doesBlockMakeSlippery(BlockPos blockPos, BlockState state, BlockView world) {
        FluidState fluidstate = world.getFluidState(blockPos);
        return state.isIn(AutumnityTags.Blocks.SLIPPERY_SNAIL_GOO_BLOCKS) || fluidstate.isIn(FluidTags.WATER);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSneaking()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0.0F, DamageSource.FALL);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(SLIPPERY) && !(entity instanceof SnailEntity)) {
            if (entity.getBoundingBox().maxY <= pos.getY() + 0.0625D) {
                if (!entity.isSneaking()) {
                    entity.slowMovement(state, new Vec3d(1.0D, 0.0F, 1.0D));
                }
            } else {
                entity.setVelocity(entity.getVelocity().multiply(0.4D, 1.0D, 0.4D));
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SLIPPERY);
    }
}
