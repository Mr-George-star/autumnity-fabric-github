package net.george.autumnity.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.item.AutumnityItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

/**
 * @since 1.0.0.build.6
 * @author Mr.George
 */
@SuppressWarnings("deprecation")
public class TallFoulBerryBushBlock extends TallPlantBlock implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_3;
    private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public TallFoulBerryBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, 0));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(AutumnityItems.FOUL_BERRIES);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(AGE) == 0 && state.get(HALF) == DoubleBlockHalf.UPPER) {
            return TOP_SHAPE;
        } else {
            return SHAPE;
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0) {
            VoxelShape voxelshape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
            Vec3d vector3d = voxelshape.getBoundingBox().getCenter();
            double d0 = (double) pos.getX() + vector3d.x;
            double d1 = (double) pos.getZ() + vector3d.z;

            int i = StatusEffects.POISON.getColor();
            double d2 = (double) (i >> 16 & 255) / 255.0D;
            double d3 = (double) (i >> 8 & 255) / 255.0D;
            double d4 = (double) (i & 255) / 255.0D;

            world.addParticle(ParticleTypes.ENTITY_EFFECT, d0 + (double) (random.nextFloat() / 5.0F),
                    (double) pos.getY() + (0.5D - (double) random.nextFloat()),
                    d1 + (double) (random.nextFloat() / 5.0F), d2, d3, d4);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 3 && state.get(HALF) == DoubleBlockHalf.LOWER && world.getBrightness(pos.up()) >= 9 && canGrow(world, random, pos, state)) {
            world.setBlockState(pos, state.with(AGE, i + 1), 2);
            setHalfState(world, pos, state, i + 1);

            grow(world, random, pos, state);
        }
    }

    //TODO: ADD: entity.getType() != AutumnityEntityTypes.SNAIL.get() && entityIn.getType() != AutumnityEntityTypes.TURKEY.get()
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && entity.getType() != EntityType.BEE) {
            livingEntity.slowMovement(state, new Vec3d(0.8F, 0.75D, 0.8F));
            if (!world.isClient && !livingEntity.hasStatusEffect(StatusEffects.POISON) && !livingEntity.isSneaking()) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60));
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean flag = i == 3;
        if (!flag && player.getStackInHand(hand).getItem() == Items.BONE_MEAL) {
            return ActionResult.PASS;
        } else if (i > 1) {
            Block.dropStack(world, pos, new ItemStack(AutumnityItems.FOUL_BERRIES, 2));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES,
                    SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlockState(pos, state.with(AGE, i - 1), 2);
            setHalfState(world, pos, state, i - 1);
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    public void placeAt(WorldAccess world, BlockPos pos, int age, int flags) {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, age), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(AGE, age), flags);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, AGE);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 3;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(3, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), 2);
        setHalfState(world, pos, state, i);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    private static void setHalfState(World world, BlockPos pos, BlockState state, int age) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            if (world.getBlockState(pos.down()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH) {
                world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(AGE, age), 2);
            }
        } else {
            if (world.getBlockState(pos.up()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH) {
                world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(AGE, age), 2);
            }
        }
    }
}
