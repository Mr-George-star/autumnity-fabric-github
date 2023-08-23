package net.george.autumnity.item;

import net.george.autumnity.effect.AutumnityEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 * @version v1.0.0.build.6
 */
public class AutumnityFoodComponents {
    public static final FoodComponent SYRUP_BOTTLE = new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build();
    public static final FoodComponent FOUL_BERRIES = new FoodComponent.Builder().hunger(1).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(AutumnityEffects.FOUL_TASTE, 320, 0), 1.0F).build();
    public static final FoodComponent FOUL_SOUP = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(AutumnityEffects.FOUL_TASTE, 2400, 1), 1.0F).build();
    public static final FoodComponent TURKEY = new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).meat()
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.1F).snack().build();
    public static final FoodComponent COOKED_TURKEY = new FoodComponent.Builder().hunger(2).saturationModifier(0.6F).meat().snack().build();
}
