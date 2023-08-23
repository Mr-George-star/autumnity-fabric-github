package net.george.autumnity.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.particle.AutumnityParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ColoredMapleLeavesBlock extends LeavesBlock {
    private final int color;

    public ColoredMapleLeavesBlock(Settings settings, int color) {
        super(settings);
        this.color = color;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (random.nextInt(100) == 0) {
            BlockPos blockPos = pos.down();
            if (world.isAir(blockPos)) {
                double d0 = (this.color >> 16 & 255) / 255.0F;
                double d1 = (this.color >> 8 & 255) / 255.0F;
                double d2 = (this.color & 255) / 255.0F;

                double d3 = (float) pos.getX() + random.nextFloat();
                double d4 = (double) pos.getY() - 0.05D;
                double d6 = (float) pos.getZ() + random.nextFloat();

                world.addParticle(AutumnityParticles.FALLING_MAPLE_LEAF.getType(), d3, d4, d6, d0, d1, d2);
            }
        }
    }
}
