package net.george.autumnity.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.george.autumnity.util.AutumnityTags;
import net.george.autumnity.world.feature.AutumnityPlacedFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

/**
 * @author Mr.George
 * @since v1.0.0.build.5
 */
public class AutumnityTreeGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, AutumnityPlacedFeatures.MAPLE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, AutumnityPlacedFeatures.YELLOW_MAPLE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, AutumnityPlacedFeatures.FALLEN_LEAVES_YELLOW_MAPLE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.excludeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, AutumnityPlacedFeatures.ORANGE_MAPLE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.excludeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, AutumnityPlacedFeatures.FALLEN_LEAVES_ORANGE_MAPLE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.tag(AutumnityTags.Biomes.TAIGAS),
                GenerationStep.Feature.VEGETAL_DECORATION, AutumnityPlacedFeatures.RED_MAPLE_PLACED.getKey().get());
    }
}
