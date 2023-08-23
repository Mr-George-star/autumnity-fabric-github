package net.george.autumnity.world.feature.custom;

import com.mojang.serialization.Codec;
import net.george.autumnity.block.AutumnityBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

/**
 * @author Mr.George
 * @since v1.0.0.build.5
 */
public class FallenLeavesTreeFeature extends MapleTreeFeature {
    public FallenLeavesTreeFeature(Codec<TreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<TreeFeatureConfig> context) {
        boolean flag = super.generate(context);

        Random random = context.getRandom();
        BlockPos position = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        TreeFeatureConfig config = context.getConfig();

        if (flag && random.nextInt(6) != 0) {
            BlockState blockstate = config.foliageProvider.getBlockState(random, position);
            BlockState leafCarpet;

            if (blockstate == AutumnityBlocks.RED_MAPLE_LEAVES.getDefaultState()) {
                leafCarpet = AutumnityBlocks.RED_MAPLE_LEAF_PILE.getDefaultState();
            } else if (blockstate == AutumnityBlocks.ORANGE_MAPLE_LEAVES.getDefaultState()) {
                leafCarpet = AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.getDefaultState();
            } else if (blockstate == AutumnityBlocks.YELLOW_MAPLE_LEAVES.getDefaultState()) {
                leafCarpet = AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.getDefaultState();
            } else {
                leafCarpet = AutumnityBlocks.MAPLE_LEAF_PILE.getDefaultState();
            }

            for (int x = -3; x <= 3; ++x) {
                for (int z = -3; z <= 3; ++z) {
                    if (Math.abs(x) < 2 || Math.abs(z) < 2) {
                        for (int y = -3; y <= 3; ++y) {
                            BlockPos blockpos = position.add(x, y, z);
                            if (random.nextInt(8) > 0 && world.isAir(blockpos)
                                    && blockpos.getY() < 318 &&
                                    world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK) {
                                world.setBlockState(blockpos, leafCarpet
                                                .with(Properties.UP, false)
                                                .with(Properties.DOWN, true)
                                                .with(Properties.NORTH, false)
                                                .with(Properties.SOUTH, false)
                                                .with(Properties.EAST, false)
                                                .with(Properties.WEST, false)
                                        , 2);
                            }
                        }
                    }
                }
            }
        }

        return flag;
    }
}
