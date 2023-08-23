package net.george.autumnity.world.feature;

import net.george.autumnity.Autumnity;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.block.custom.TallFoulBerryBushBlock;
import net.george.autumnity.util.FeatureUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

/**
 * @author Mr.George
 * @since v1.0.0.build.4
 * @version v1.0.0.build.6
 */
public class AutumnityConfiguredFeatures {

    public static class States {
        public static final BlockState MAPLE_LEAVES = AutumnityBlocks.MAPLE_LEAVES.getDefaultState();
        public static final BlockState YELLOW_MAPLE_LEAVES = AutumnityBlocks.YELLOW_MAPLE_LEAVES.getDefaultState();
        public static final BlockState ORANGE_MAPLE_LEAVES = AutumnityBlocks.ORANGE_MAPLE_LEAVES.getDefaultState();
        public static final BlockState RED_MAPLE_LEAVES = AutumnityBlocks.RED_MAPLE_LEAVES.getDefaultState();
        public static final BlockState MAPLE_LOG = AutumnityBlocks.MAPLE_LOG.getDefaultState();
    }

    public static class Configs {
        public static final TreeFeatureConfig MAPLE_TREE = createMaple(States.MAPLE_LEAVES).build();
        public static final TreeFeatureConfig YELLOW_MAPLE_TREE = createMaple(States.YELLOW_MAPLE_LEAVES).build();
        public static final TreeFeatureConfig ORANGE_MAPLE_TREE = createMaple(States.ORANGE_MAPLE_LEAVES).build();
        public static final TreeFeatureConfig RED_MAPLE_TREE = createMaple(States.RED_MAPLE_LEAVES).build();

        public static TreeFeatureConfig.Builder createMaple(BlockState leavesState) {
            return new TreeFeatureConfig.Builder(BlockStateProvider.of(States.MAPLE_LOG),
                    new StraightTrunkPlacer(5, 1, 0), BlockStateProvider.of(leavesState),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
                    new TwoLayersFeatureSize(1, 0, 2)).ignoreVines();
        }
    }

    /**
     * &#064;note For Configured Features Class registry, have to be : CF -> PF -> CF -> PF,
     * @implNote  CF: {@link ConfiguredFeature}, PF: {@link PlacedFeature}
     */

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> MAPLE_TREE =
            ConfiguredFeatures.register("maple_tree", AutumnityFeatures.MAPLE_TREE, Configs.MAPLE_TREE);
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YELLOW_MAPLE_TREE =
            ConfiguredFeatures.register("yellow_maple_tree", AutumnityFeatures.MAPLE_TREE, Configs.YELLOW_MAPLE_TREE);
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FALLEN_LEAVES_YELLOW_MAPLE_TREE =
            ConfiguredFeatures.register("fallen_leaves_yellow_maple_tree", AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE, Configs.YELLOW_MAPLE_TREE);
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ORANGE_MAPLE_TREE =
            ConfiguredFeatures.register("orange_maple_tree", AutumnityFeatures.MAPLE_TREE, Configs.ORANGE_MAPLE_TREE);
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> FALLEN_LEAVES_ORANGE_MAPLE_TREE =
            ConfiguredFeatures.register("fallen_leaves_orange_maple_tree", AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE, Configs.ORANGE_MAPLE_TREE);
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> RED_MAPLE_TREE =
            ConfiguredFeatures.register("red_maple_tree", AutumnityFeatures.MAPLE_TREE, Configs.RED_MAPLE_TREE);

    //Checked
    public static final RegistryEntry<PlacedFeature> MAPLE_CHECKED =
            PlacedFeatures.register("maple_checked", MAPLE_TREE,
                    PlacedFeatures.wouldSurvive(AutumnityBlocks.MAPLE_SAPLING));
    public static final RegistryEntry<PlacedFeature> YELLOW_MAPLE_CHECKED =
            PlacedFeatures.register("yellow_maple_checked", YELLOW_MAPLE_TREE,
                    PlacedFeatures.wouldSurvive(AutumnityBlocks.YELLOW_MAPLE_SAPLING));
    public static final RegistryEntry<PlacedFeature> FALLEN_LEAVES_YELLOW_MAPLE_CHECKED =
            PlacedFeatures.register("fallen_leaves_yellow_maple_checked", FALLEN_LEAVES_YELLOW_MAPLE_TREE,
                    PlacedFeatures.wouldSurvive(AutumnityBlocks.YELLOW_MAPLE_SAPLING));
    public static final RegistryEntry<PlacedFeature> ORANGE_MAPLE_CHECKED =
            PlacedFeatures.register("orange_maple_checked", ORANGE_MAPLE_TREE,
                    PlacedFeatures.wouldSurvive(AutumnityBlocks.ORANGE_MAPLE_SAPLING));
    public static final RegistryEntry<PlacedFeature> FALLEN_LEAVES_ORANGE_MAPLE_CHECKED =
            PlacedFeatures.register("fallen_leaves_orange_maple_checked", FALLEN_LEAVES_ORANGE_MAPLE_TREE,
                    PlacedFeatures.wouldSurvive(AutumnityBlocks.ORANGE_MAPLE_SAPLING));
    public static final RegistryEntry<PlacedFeature> RED_MAPLE_CHECKED =
            PlacedFeatures.register("red_maple_checked", RED_MAPLE_TREE,
                    PlacedFeatures.wouldSurvive(AutumnityBlocks.RED_MAPLE_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> MAPLE_SPAWN =
            ConfiguredFeatures.register("maple_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(MAPLE_CHECKED, 0.15F)),
                            MAPLE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> YELLOW_MAPLE_SPAWN =
            ConfiguredFeatures.register("yellow_maple_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(YELLOW_MAPLE_CHECKED, 0.15F)),
                            YELLOW_MAPLE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FALLEN_LEAVES_YELLOW_MAPLE_SPAWN =
            ConfiguredFeatures.register("fallen_leaves_yellow_maple_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(FALLEN_LEAVES_YELLOW_MAPLE_CHECKED, 0.5F)),
                            FALLEN_LEAVES_YELLOW_MAPLE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> ORANGE_MAPLE_SPAWN =
            ConfiguredFeatures.register("orange_maple_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(ORANGE_MAPLE_CHECKED, 0.15F)),
                            ORANGE_MAPLE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FALLEN_LEAVES_ORANGE_MAPLE_SPAWN =
            ConfiguredFeatures.register("fallen_leaves_orange_maple_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(FALLEN_LEAVES_ORANGE_MAPLE_CHECKED, 0.5F)),
                            FALLEN_LEAVES_ORANGE_MAPLE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> RED_MAPLE_SPAWN =
            ConfiguredFeatures.register("red_maple_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(RED_MAPLE_CHECKED, 0.15F)),
                            RED_MAPLE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> FALLEN_LEAVES =
            ConfiguredFeatures.register("fallen_leaves", AutumnityFeatures.FALLEN_LEAVES, FeatureConfig.DEFAULT);
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> MAPLE_FOREST_VEGETATION =
            ConfiguredFeatures.register("maple_forest_vegetation", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(
                            new RandomFeatureEntry(PlacedFeatures.createEntry(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
                            new RandomFeatureEntry(PlacedFeatures.createEntry(TreeConfiguredFeatures.HUGE_RED_MUSHROOM), 0.05F),
                            new RandomFeatureEntry(RED_MAPLE_CHECKED, 0.3F),
                            new RandomFeatureEntry(FALLEN_LEAVES_ORANGE_MAPLE_CHECKED, 0.4F),
                            new RandomFeatureEntry(FALLEN_LEAVES_YELLOW_MAPLE_CHECKED, 0.2F)
                    ), MAPLE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_FOUL_BERRY_BUSH =
            ConfiguredFeatures.register("patch_foul_berry_bush", Feature.RANDOM_PATCH,
                    FeatureUtil.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.getDefaultState()
                                    .with(TallFoulBerryBushBlock.AGE, 3))), List.of(Blocks.GRASS_BLOCK)));

    public static final RegistryEntry<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> FLOWER_MAPLE_FOREST =
            ConfiguredFeatures.register("flower_maple_forest", Feature.SIMPLE_RANDOM_SELECTOR,
                    new SimpleRandomFeatureConfig(RegistryEntryList.of(
                            PlacedFeatures.createEntry(Feature.RANDOM_PATCH,
                                    FeatureUtil.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                            new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.ROSE_BUSH)))),
                            PlacedFeatures.createEntry(Feature.NO_BONEMEAL_FLOWER,
                                    FeatureUtil.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                            new SimpleBlockFeatureConfig(BlockStateProvider.of(AutumnityBlocks.AUTUMN_CROCUS))))
                    )));

    public static void registerConfiguredFeatures() {
        Autumnity.LOGGER.debug("Registering Configured Features for " + Autumnity.MOD_ID);
    }
}
