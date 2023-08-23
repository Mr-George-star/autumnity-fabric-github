package net.george.autumnity.item.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @since v1.0.0.build.7
 * @author Mr.George
 */
public class SnailShellChestplateItem extends ArmorItem {
    public SnailShellChestplateItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if (player.isSneaking()) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 2, false, false, true));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.autumnity.snail_shell_chestplate.whenSneaking").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText(StatusEffects.RESISTANCE.getTranslationKey()).formatted(Formatting.BLUE)
                .append(" ").append(new TranslatableText("potion.potency.2").formatted(Formatting.BLUE)));
        tooltip.add(new TranslatableText("attribute.modifier.plus." + EntityAttributeModifier.Operation.ADDITION.name(), ItemStack.MODIFIER_FORMAT.format(10),
                new TranslatableText(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE.getTranslationKey())).formatted(Formatting.BLUE));
    }
}
