package net.george.autumnity.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class FeatureUtil {
    private static BlockPredicate simplePatchPredicate(List<Block> blocks) {
        BlockPredicate blockPredicate;
        if (!blocks.isEmpty()) {
            blockPredicate = BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlocks(blocks, new BlockPos(0, -1, 0)));
        } else {
            blockPredicate = BlockPredicate.IS_AIR;
        }

        return blockPredicate;
    }

    public static RandomPatchFeatureConfig simpleRandomPatchConfiguration(int j, RegistryEntry<PlacedFeature> entry) {
        return new RandomPatchFeatureConfig(j, 7, 3, entry);
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RandomPatchFeatureConfig simplePatchConfiguration(F feature, FC featureConfig, List<Block> blocks, int j) {
        return simpleRandomPatchConfiguration(j, PlacedFeatures.createEntry(feature, featureConfig, simplePatchPredicate(blocks)));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RandomPatchFeatureConfig simplePatchConfiguration(F feature, FC featureConfig, List<Block> blocks) {
        return simplePatchConfiguration(feature, featureConfig, blocks, 96);
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RandomPatchFeatureConfig simplePatchConfiguration(F feature, FC featureConfig) {
        return simplePatchConfiguration(feature, featureConfig, List.of(), 96);
    }

}
