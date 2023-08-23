package net.george.autumnity.world.feature;

import net.george.autumnity.world.feature.custom.FallenLeavesFeature;
import net.george.autumnity.world.feature.custom.FallenLeavesTreeFeature;
import net.george.autumnity.world.feature.custom.MapleTreeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

/**
 * @author Mr.George
 * @since v1.0.0.build.4
 * @version v1.0.0.build.6
 */
public class AutumnityFeatures {
    public static final Feature<TreeFeatureConfig> MAPLE_TREE = register("maple_tree",
            new MapleTreeFeature(TreeFeatureConfig.CODEC));
    public static final Feature<TreeFeatureConfig> FALLEN_LEAVES_MAPLE_TREE = register("fallen_leaves_maple_tree",
            new FallenLeavesTreeFeature(TreeFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> FALLEN_LEAVES = register("fallen_leaves",
            new FallenLeavesFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }
}
