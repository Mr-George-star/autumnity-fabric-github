package net.george.autumnity.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.particle.AutumnityParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MapleLeavesBlock extends LeavesBlock {
    public MapleLeavesBlock(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (random.nextInt(100) == 0) {
            BlockPos blockPos = pos.down();
            if (world.isAir(blockPos)) {
                int color = world.getBiome(pos).value().getFoliageColor();

                double d0 = (color >> 16 & 255) / 255.0F;
                double d1 = (color >> 8 & 255) / 255.0F;
                double d2 = (color & 255) / 255.0F;

                double d3 = (float) pos.getX() + random.nextFloat();
                double d4 = (double) pos.getY() - 0.05D;
                double d6 = (float) pos.getZ() + random.nextFloat();

                world.addParticle(AutumnityParticles.FALLING_MAPLE_LEAF.getType(), d3, d4, d6, d0, d1, d2);
            }
        }
    }
}
