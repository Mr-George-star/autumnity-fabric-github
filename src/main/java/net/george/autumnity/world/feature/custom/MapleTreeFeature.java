package net.george.autumnity.world.feature.custom;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.util.BuiltinUtil;
import net.george.autumnity.util.TreeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author Mr.George
 * @since v1.0.0.build.5
 */
public class MapleTreeFeature extends Feature<TreeFeatureConfig> {
    private final int maxBuildHeight;
    private final int minBuildHeight;
    
    public MapleTreeFeature(Codec<TreeFeatureConfig> configCodec) {
        super(configCodec);
        this.maxBuildHeight = BuiltinUtil.MAX_BUILD_HEIGHT;
        this.minBuildHeight = BuiltinUtil.MIN_BUILD_HEIGHT;
    }

    @Override
    public boolean generate(FeatureContext<TreeFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos position = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        TreeFeatureConfig config = context.getConfig();

        int i = config.trunkPlacer.getHeight(random);

        boolean flag = true;
        if (position.getY() > this.minBuildHeight && position.getY() + i + 1 <= this.maxBuildHeight) {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
                int k = 1;
                if (j == position.getY()) {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2) {
                    k = 2;
                }

                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < this.maxBuildHeight) {
                            if (!TreeFeature.isAirOrLeaves(world, mutable.set(l, j, i1))) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (TreeUtil.isValidGround(world, position.down(), (SaplingBlock) AutumnityBlocks.MAPLE_SAPLING) && position.getY() < this.maxBuildHeight - i - 1) {
                Set<BlockPos> logPosSet = Sets.newHashSet();

                BiConsumer<BlockPos, BlockState> logBiConsumer = (pos, state) -> {
                    logPosSet.add(pos.toImmutable());
                    TreeUtil.setForcedState(world, pos, state);
                };

                for (int i2 = 0; i2 < 2; ++i2) {
                    BlockPos blockpos = position.up(i - 1 - i2);
                    for (BlockPos blockPos1 : BlockPos.iterate(blockpos.add(-2, -1, -2), blockpos.add(2, 3, 2))) {
                        double d0 = blockPos1.getSquaredDistance(blockpos);
                        if (d0 <= (double) (2.35F * 2.35F) || (d0 <= (double) (2.5F * 2.5F) && random.nextInt(2) > 0)) {
                            TreeUtil.placeLeafAt(world, blockPos1, random, config);
                        }
                    }
                }

                config.trunkPlacer.generate(world, logBiConsumer, random, i, position, config);
                TreeUtil.updateLeaves(world, logPosSet);

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
