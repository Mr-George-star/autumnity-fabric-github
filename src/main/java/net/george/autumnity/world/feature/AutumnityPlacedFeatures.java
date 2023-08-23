package net.george.autumnity.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

/**
 * @author Mr.George
 * @since v1.0.0.build.4
 * @version v1.0.0.build.6
 */
public class AutumnityPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> MAPLE_PLACED = PlacedFeatures.register("maple_placed",
            AutumnityConfiguredFeatures.MAPLE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1F, 1)));
    public static final RegistryEntry<PlacedFeature> YELLOW_MAPLE_PLACED = PlacedFeatures.register("yellow_maple_placed",
            AutumnityConfiguredFeatures.YELLOW_MAPLE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1F, 1)));
    public static final RegistryEntry<PlacedFeature> FALLEN_LEAVES_YELLOW_MAPLE_PLACED = PlacedFeatures.register("fallen_leaves_yellow_maple_placed",
            AutumnityConfiguredFeatures.FALLEN_LEAVES_YELLOW_MAPLE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1F, 2)));
    public static final RegistryEntry<PlacedFeature> ORANGE_MAPLE_PLACED = PlacedFeatures.register("orange_maple_placed",
            AutumnityConfiguredFeatures.ORANGE_MAPLE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1F, 1)));
    public static final RegistryEntry<PlacedFeature> FALLEN_LEAVES_ORANGE_MAPLE_PLACED = PlacedFeatures.register("fallen_leaves_orange_maple_placed",
            AutumnityConfiguredFeatures.FALLEN_LEAVES_ORANGE_MAPLE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1F, 2)));
    public static final RegistryEntry<PlacedFeature> RED_MAPLE_PLACED = PlacedFeatures.register("red_maple_placed",
            AutumnityConfiguredFeatures.RED_MAPLE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> FALLEN_LEAVES = PlacedFeatures.register("fallen_leaves",
            AutumnityConfiguredFeatures.FALLEN_LEAVES, RarityFilterPlacementModifier.of(1),
            SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> PATCH_FOUL_BERRY_BUSH = PlacedFeatures.register("patch_foul_berry_bush",
            AutumnityConfiguredFeatures.PATCH_FOUL_BERRY_BUSH, RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> FLOWER_MAPLE_FOREST = PlacedFeatures.register("flower_maple_forest",
            AutumnityConfiguredFeatures.FLOWER_MAPLE_FOREST, RarityFilterPlacementModifier.of(7),
            SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> MAPLE_FOREST_VEGETATION = PlacedFeatures.register("maple_forest_vegetation",
            AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION,
            VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));
}
