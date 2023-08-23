package net.george.autumnity.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.george.autumnity.Autumnity;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.item.custom.SnailShellChestplateItem;
import net.george.autumnity.item.custom.SyrupBottleItem;
import net.george.autumnity.item.custom.TurkeyEggItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 * @version v1.0.0.build.7
 */
@SuppressWarnings({"unused"})
public class AutumnityItems {
    public static final Item SAP_BOTTLE = registerItem("sap_bottle",
            new Item(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(AutumnityItemGroup.AUTUMNITY)));
    public static final Item SYRUP_BOTTLE = registerItem("syrup_bottle",
            new SyrupBottleItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(16)
                    .group(AutumnityItemGroup.AUTUMNITY).food(AutumnityFoodComponents.SYRUP_BOTTLE)));
    public static final Item FOUL_BERRIES = registerItem("foul_berries",
            new AliasedBlockItem(AutumnityBlocks.FOUL_BERRY_BUSH, new FabricItemSettings().group(AutumnityItemGroup.AUTUMNITY)
                    .food(AutumnityFoodComponents.FOUL_BERRIES)));
    public static final Item FOUL_SOUP = registerItem("foul_soup",
            new StewItem(new FabricItemSettings().maxCount(1).group(AutumnityItemGroup.AUTUMNITY)
                    .food(AutumnityFoodComponents.FOUL_SOUP)));

    public static final Item MAPLE_SIGN = registerItem("maple_sign",
            new SignItem(new FabricItemSettings().group(AutumnityItemGroup.AUTUMNITY).maxCount(16),
                    AutumnityBlocks.MAPLE_SIGN, AutumnityBlocks.MAPLE_WALL_SIGN));

    public static final Item SNAIL_SHELL_PIECE = registerItem("snail_shell_piece",
            new Item(new FabricItemSettings().group(AutumnityItemGroup.AUTUMNITY)));
    public static final Item SNAIL_SHELL_CHESTPLATE = registerItem("snail_shell_chestplate",
            new SnailShellChestplateItem(AutumnityArmorMaterials.SNAIL_SHELL.getValue(), EquipmentSlot.CHEST,
                    new FabricItemSettings().group(AutumnityItemGroup.AUTUMNITY)));

    public static final Item TURKEY_EGG = registerItem("turkey_egg",
            new TurkeyEggItem(new FabricItemSettings().maxCount(16).group(AutumnityItemGroup.AUTUMNITY)));
    public static final Item TURKEY_PIECE = registerItem("turkey_piece",
            new Item(new FabricItemSettings().food(AutumnityFoodComponents.TURKEY).group(AutumnityItemGroup.AUTUMNITY)));
    public static final Item COOKED_TURKEY_PIECE = registerItem("cooked_turkey_piece",
            new Item(new FabricItemSettings().food(AutumnityFoodComponents.COOKED_TURKEY).group(AutumnityItemGroup.AUTUMNITY)));

    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            new SpawnEggItem(AutumnityEntities.SNAIL, 7355937, 14727558,
                    new FabricItemSettings().maxCount(16).group(AutumnityItemGroup.AUTUMNITY)));
    public static final Item TURKEY_SPAWN_EGG = registerItem("turkey_spawn_egg",
            new SpawnEggItem(AutumnityEntities.TURKEY, 6765623, 5019859,
                    new FabricItemSettings().maxCount(16).group(AutumnityItemGroup.AUTUMNITY)));

    private static Item registerItem(String name, Item settings) {
        return Registry.register(Registry.ITEM, new Identifier(Autumnity.MOD_ID, name), settings);
    }

    public static void registerItems() {
        Autumnity.LOGGER.debug("Registering Items for " + Autumnity.MOD_ID);
    }
}
