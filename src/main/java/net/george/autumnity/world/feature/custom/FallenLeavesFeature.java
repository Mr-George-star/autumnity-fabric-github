package net.george.autumnity.world.feature.custom;

import com.mojang.serialization.Codec;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.util.BuiltinUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

/**
 * @author Mr.George
 * @since v1.0.0.build.5
 */
public class FallenLeavesFeature extends Feature<DefaultFeatureConfig> {
    private final int maxBuildHeight;
    
    public FallenLeavesFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
        this.maxBuildHeight = BuiltinUtil.MAX_BUILD_HEIGHT;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos pos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();

        int i = 0;

        int j = random.nextInt(3);
        BlockState blockstate = j == 0 ? AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.getDefaultState() : AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.getDefaultState();

        for (int x = -3; x <= 3; ++x) {
            for (int z = -3; z <= 3; ++z) {
                if (Math.abs(x) < 2 || Math.abs(z) < 2) {
                    for (int y = -3; y <= 3; ++y) {
                        BlockPos blockpos = pos.add(x, y, z);
                        if (random.nextInt(3) > 0 && world.isAir(blockpos) && blockpos.getY() < this.maxBuildHeight && world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK) {
                            world.setBlockState(blockpos, blockstate
                                    .with(Properties.UP, false)
                                    .with(Properties.DOWN, true)
                                    .with(Properties.NORTH, false)
                                    .with(Properties.SOUTH, false)
                                    .with(Properties.EAST, false)
                                    .with(Properties.WEST, false), 2);
                            ++i;
                        }
                    }
                }
            }
        }

        return i > 0;
    }
}
