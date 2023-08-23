package net.george.autumnity.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.george.autumnity.Autumnity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author Mr.George
 * @since v1.0.0.build.3
 */
public class AutumnityParticles {
    public static final DefaultParticleType FALLING_MAPLE_LEAF = FabricParticleTypes.simple(false);

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Autumnity.MOD_ID, "falling_maple_leaf"),
                FALLING_MAPLE_LEAF);
    }
}
