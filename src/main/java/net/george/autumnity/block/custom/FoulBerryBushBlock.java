package net.george.autumnity.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.item.AutumnityItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @since 1.0.0.build.6
 * @author Mr.George
 */
@SuppressWarnings("deprecation")
public class FoulBerryBushBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_1;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)};

    public FoulBerryBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AGE, 0));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(AutumnityItems.FOUL_BERRIES);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(AGE)];
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(AGE) == 1 && random.nextInt(5) == 0) {
            VoxelShape voxelshape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
            Vec3d vec3d = voxelshape.getBoundingBox().getCenter();
            double d0 = (double) pos.getX() + vec3d.x;
            double d1 = (double) pos.getZ() + vec3d.z;

            int i = StatusEffects.POISON.getColor();
            double d2 = (double) (i >> 16 & 255) / 255.0D;
            double d3 = (double) (i >> 8 & 255) / 255.0D;
            double d4 = (double) (i & 255) / 255.0D;

            world.addParticle(ParticleTypes.ENTITY_EFFECT, d0 + (double) (random.nextFloat() / 5.0F),
                    (double) pos.getY() + (0.5D - (double) random.nextFloat()), d1 + (double) (random.nextFloat() / 5.0F), d2, d3, d4);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBrightness(pos.up()) >= 9 && canGrow(world, random, pos, state)) {
            if (state.get(AGE) == 0) {
                world.setBlockState(pos, state.with(AGE, 1), 2);
            } else if (world.isAir(pos.up())) {
                TallFoulBerryBushBlock block = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH;
                block.placeAt(world, pos, 0, 2);
            }
            grow(world, random, pos, state);
        }
    }

    //TODO: ADD: entity.getType() != AutumnityEntityTypes.SNAIL.get() && entity.getType() != AutumnityEntityTypes.TURKEY.get()
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && entity.getType() != EntityType.BEE) {
            entity.slowMovement(state, new Vec3d(0.8F, 0.75D, 0.8F));
            if (!world.isClient && !livingEntity.hasStatusEffect(StatusEffects.POISON) && !livingEntity.isSneaking()) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60));
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE) == 0) {
            world.setBlockState(pos, state.with(AGE, 1), 2);
        } else if (world.isAir(pos.up())) {
            TallFoulBerryBushBlock block = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH;
            block.placeAt(world, pos, 0, 2);
        }
    }
}
