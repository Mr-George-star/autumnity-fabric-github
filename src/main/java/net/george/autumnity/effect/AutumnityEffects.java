package net.george.autumnity.effect;

import net.george.autumnity.Autumnity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author Mr.George
 * @since v1.0.0.build.6
 */
public class AutumnityEffects {
    public static final StatusEffect FOUL_TASTE = registerEffect("foul_taste",
            new FoulTasteEffect(StatusEffectCategory.HARMFUL, 15363616));

    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Autumnity.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        Autumnity.LOGGER.debug("Registering Effects for " + Autumnity.MOD_ID);
    }
}
