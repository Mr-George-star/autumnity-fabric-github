package net.george.autumnity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * @author Mr.George
 * @since v1.0.0.build.6
 */
public class FoulTasteEffect extends StatusEffect {
    public FoulTasteEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
