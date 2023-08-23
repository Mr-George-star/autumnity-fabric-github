package net.george.autumnity.world.biome;

import net.george.autumnity.Autumnity;
import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.util.BiomeUtil;
import net.george.autumnity.world.feature.AutumnityPlacedFeatures;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.George
 * @since v1.0.0.build.5
 * @version v1.0.0.build.7
 */
public class AutumnityBiomes {
    public static final Map<RegistryKey<Biome>, Biome> BIOMES = new HashMap<>();
    public static final RegistryKey<Biome> MAPLE_FOREST = registerBiome("maple_forest", MapleForest.create());

    private static RegistryKey<Biome> registerBiome(String name, Biome biome) {
        RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Autumnity.MOD_ID, name));
        BIOMES.put(key, biome);
        return key;
    }

    public static void registerBiomes() {
        for (RegistryKey<Biome> key : BIOMES.keySet()) {
            BuiltinRegistries.add(BuiltinRegistries.BIOME, key, BIOMES.get(key));
        }
    }

    public static class MapleForest {
        private static SpawnSettings spawnSettings() {
            SpawnSettings.Builder builder = new SpawnSettings.Builder();
            BiomeUtil.basicSpawn(builder);
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(AutumnityEntities.TURKEY, 10, 4, 4));
            builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(AutumnityEntities.SNAIL, 16, 2, 2));
            return builder.build();
        }

        private static GenerationSettings generationSettings() {
            GenerationSettings.Builder builder = new GenerationSettings.Builder();
            BiomeUtil.basicGeneration(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addDefaultDisks(builder);
            DefaultBiomeFeatures.addDefaultFlowers(builder);
            DefaultBiomeFeatures.addForestGrass(builder);
            DefaultBiomeFeatures.addDefaultMushrooms(builder);
            DefaultBiomeFeatures.addDefaultVegetation(builder);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    AutumnityPlacedFeatures.MAPLE_FOREST_VEGETATION);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    AutumnityPlacedFeatures.FLOWER_MAPLE_FOREST);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    AutumnityPlacedFeatures.FALLEN_LEAVES);
            builder.feature(GenerationStep.Feature.VEGETAL_DECORATION,
                    AutumnityPlacedFeatures.PATCH_FOUL_BERRY_BUSH);
            return builder.build();
        }

        public static Biome create() {
            return new Biome.Builder()
                    .precipitation(Biome.Precipitation.RAIN)
                    .category(Biome.Category.FOREST).temperature(0.7F).downfall(0.8F)
                    .effects(new BiomeEffects.Builder()
                            .waterColor(4159204).waterFogColor(329011).fogColor(12638463)
                            .skyColor(BiomeUtil.calculateSkyColor(0.7F)).foliageColor(0x9FC944)
                            .grassColor(0x9AB839).moodSound(BiomeMoodSound.CAVE).loopSound(SoundEvents.MUSIC_GAME).build())
                    .spawnSettings(spawnSettings()).generationSettings(generationSettings()).build();
        }
    }
}
