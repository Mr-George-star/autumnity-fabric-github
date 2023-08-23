package net.george.autumnity.world.feature.tree;

import net.george.autumnity.world.feature.AutumnityConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * @author Mr.George
 * @since v1.0.0.build.4
 * @version v1.0.0.build.6
 */
public class YellowMapleSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return AutumnityConfiguredFeatures.YELLOW_MAPLE_TREE;
    }
}
