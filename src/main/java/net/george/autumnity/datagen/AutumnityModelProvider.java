package net.george.autumnity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.item.AutumnityItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 * @version v1.0.0.build.7
 *
 * This Provider for Generate Models.
 */
public class AutumnityModelProvider extends FabricModelProvider {
    public AutumnityModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(AutumnityBlocks.MAPLE_PLANKS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(AutumnityItems.FOUL_BERRIES, Models.GENERATED);
        itemModelGenerator.register(AutumnityItems.MAPLE_SIGN, Models.GENERATED);
        itemModelGenerator.register(AutumnityItems.SNAIL_SHELL_PIECE, Models.GENERATED);
        itemModelGenerator.register(AutumnityItems.SNAIL_SHELL_CHESTPLATE, Models.GENERATED);
    }
}
