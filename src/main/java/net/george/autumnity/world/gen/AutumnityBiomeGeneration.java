package net.george.autumnity.world.gen;

import com.mojang.datafixers.util.Pair;
import net.george.autumnity.Autumnity;
import net.george.autumnity.world.biome.AutumnityBiomes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.*;

import java.util.function.Consumer;

/**
 * @author Mr.George
 * @since v1.0.0.build.5
 */
public class AutumnityBiomeGeneration extends Region implements Runnable, TerraBlenderApi {
    public AutumnityBiomeGeneration() {
        super(new Identifier(Autumnity.MOD_ID, "overworld"), RegionType.OVERWORLD, 5);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addBiome(
                mapper, ParameterUtils.Temperature.HOT, ParameterUtils.Humidity.ARID,
                ParameterUtils.Continentalness.FAR_INLAND, ParameterUtils.Erosion.EROSION_0,
                ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING, ParameterUtils.Depth.SURFACE,
                0L, AutumnityBiomes.MAPLE_FOREST);
    }

    @Override
    public void onTerraBlenderInitialized() {
        Autumnity.callbackWhenInitialized(this);
    }

    @Override
    public void run() {
        Regions.register(this);
    }
}
