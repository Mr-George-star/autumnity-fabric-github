package net.george.autumnity.item;

import net.george.autumnity.sound.AutumnitySounds;
import net.george.autumnity.util.property.ArmorMaterialProperties;
import net.minecraft.recipe.Ingredient;

public enum AutumnityArmorMaterials {
    SNAIL_SHELL(new ArmorMaterialProperties("snail_shell", 23, new int[]{0, 0, 5, 0},
            9, AutumnitySounds.ITEM_ARMOR_EQUIP_SNAIL, 0.0F, 0.0F,
            () -> Ingredient.ofItems(AutumnityItems.SNAIL_SHELL_PIECE)));

    private final ArmorMaterialProperties value;

    AutumnityArmorMaterials(ArmorMaterialProperties properties) {
        this.value = properties;
    }

    public ArmorMaterialProperties getValue() {
        return this.value;
    }
}
