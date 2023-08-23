package net.george.autumnity.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.george.autumnity.Autumnity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 */
public class AutumnityItemGroup {
    public static final ItemGroup AUTUMNITY = FabricItemGroupBuilder.build(new Identifier(Autumnity.MOD_ID, "autumnity"),
            () -> new ItemStack(AutumnityItems.SYRUP_BOTTLE));
}
