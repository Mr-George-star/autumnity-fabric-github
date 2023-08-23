package net.george.autumnity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.george.autumnity.Autumnity;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.block.custom.TallFoulBerryBushBlock;
import net.george.autumnity.item.AutumnityItems;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

/**
 * @author Mr.George
 * @since v1.0.0.build.2
 * @version v1.0.0.build.7
 *
 * This Provider for Generate Loot Tables.
 */
@SuppressWarnings("deprecation")
public class AutumnityLootTableProvider extends SimpleFabricLootTableProvider {
    private static final String MOD_ID = Autumnity.MOD_ID;
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public static final FabricLootSupplierBuilder FOUL_BERRY_BUILDER = FabricLootSupplierBuilder.builder()
            .withPool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                    .bonusRolls(ConstantLootNumberProvider.create(0.0F)).with(ItemEntry.builder(AutumnityItems.FOUL_BERRIES))
                    .conditionally(BlockStatePropertyLootCondition.builder(AutumnityBlocks.TALL_FOUL_BERRY_BUSH)
                            .properties(StatePredicate.Builder.create().exactMatch(TallFoulBerryBushBlock.AGE, 3).exactMatch(TallFoulBerryBushBlock.HALF, "lower")))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0F, 4.0F), false))
                    .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 1))
                    .build())
            .withPool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                    .bonusRolls(ConstantLootNumberProvider.create(0.0F)).with(ItemEntry.builder(AutumnityItems.FOUL_BERRIES))
                    .conditionally(BlockStatePropertyLootCondition.builder(AutumnityBlocks.TALL_FOUL_BERRY_BUSH)
                            .properties(StatePredicate.Builder.create().exactMatch(TallFoulBerryBushBlock.AGE, 2).exactMatch(TallFoulBerryBushBlock.HALF, "lower")))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F), false))
                    .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 1))
                    .build())
            .withFunction(ExplosionDecayLootFunction.builder().build());

    public AutumnityLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_goo"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_GOO));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_goo_block"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_GOO_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/pancake"),
                BlockLootTableGenerator.drops(AutumnityBlocks.PANCAKE));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/autumn_crocus"),
                BlockLootTableGenerator.drops(AutumnityBlocks.AUTUMN_CROCUS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/potted_autumn_crocus"),
                BlockLootTableGenerator.pottedPlantDrops(AutumnityBlocks.AUTUMN_CROCUS));

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/turkey"),
                BlockLootTableGenerator.drops(AutumnityBlocks.TURKEY));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/cooked_turkey"),
                BlockLootTableGenerator.drops(AutumnityBlocks.COOKED_TURKEY));

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/foul_berry_bush"),
                BlockLootTableGenerator.dropsNothing());
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/tall_foul_berry_bush"),
                FOUL_BERRY_BUILDER);
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/potted_foul_berries"),
                BlockLootTableGenerator.pottedPlantDrops(AutumnityItems.FOUL_BERRIES));

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_block"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_BLOCK));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_bricks"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_BRICKS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_brick_stairs"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_BRICK_STAIRS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_brick_slab"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_BRICK_SLAB));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_brick_wall"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_BRICK_WALL));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/chiseled_snail_shell_bricks"),
                BlockLootTableGenerator.drops(AutumnityBlocks.CHISELED_SNAIL_SHELL_BRICKS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_tiles"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_TILES));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_tile_stairs"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_TILE_STAIRS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_tile_slab"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_TILE_SLAB));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/snail_shell_tile_wall"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SNAIL_SHELL_TILE_WALL));

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/stripped_maple_log"),
                BlockLootTableGenerator.drops(AutumnityBlocks.STRIPPED_MAPLE_LOG));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/stripped_maple_wood"),
                BlockLootTableGenerator.drops(AutumnityBlocks.STRIPPED_MAPLE_WOOD));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/sappy_maple_log"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SAPPY_MAPLE_LOG));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/sappy_maple_wood"),
                BlockLootTableGenerator.drops(AutumnityBlocks.SAPPY_MAPLE_WOOD));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_log"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_LOG));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_wood"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_WOOD));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_planks"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_PLANKS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_stairs"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_STAIRS));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_slab"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_SLAB));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_pressure_plate"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_PRESSURE_PLATE));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_button"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_BUTTON));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_fence"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_FENCE));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_fence_gate"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_FENCE_GATE));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_door"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_DOOR));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_trapdoor"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_TRAPDOOR));

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_leaves"),
                BlockLootTableGenerator.leavesDrop(AutumnityBlocks.MAPLE_LEAVES,
                        AutumnityBlocks.MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_sapling"),
                BlockLootTableGenerator.drops(AutumnityBlocks.MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/potted_maple_sapling"),
                BlockLootTableGenerator.pottedPlantDrops(AutumnityBlocks.MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/maple_leaf_pile"),
                BlockLootTableGenerator.dropsNothing());

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/yellow_maple_leaves"),
                BlockLootTableGenerator.leavesDrop(AutumnityBlocks.YELLOW_MAPLE_LEAVES,
                        AutumnityBlocks.YELLOW_MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/yellow_maple_sapling"),
                BlockLootTableGenerator.drops(AutumnityBlocks.YELLOW_MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/potted_yellow_maple_sapling"),
                BlockLootTableGenerator.pottedPlantDrops(AutumnityBlocks.YELLOW_MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/yellow_maple_leaf_pile"),
                BlockLootTableGenerator.dropsNothing());

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/orange_maple_leaves"),
                BlockLootTableGenerator.leavesDrop(AutumnityBlocks.ORANGE_MAPLE_LEAVES,
                        AutumnityBlocks.ORANGE_MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/orange_maple_sapling"),
                BlockLootTableGenerator.drops(AutumnityBlocks.ORANGE_MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/potted_orange_maple_sapling"),
                BlockLootTableGenerator.pottedPlantDrops(AutumnityBlocks.ORANGE_MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/orange_maple_leaf_pile"),
                BlockLootTableGenerator.dropsNothing());

        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/red_maple_leaves"),
                BlockLootTableGenerator.leavesDrop(AutumnityBlocks.RED_MAPLE_LEAVES,
                        AutumnityBlocks.RED_MAPLE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/red_maple_sapling"),
                BlockLootTableGenerator.drops(AutumnityBlocks.RED_MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/potted_red_maple_sapling"),
                BlockLootTableGenerator.pottedPlantDrops(AutumnityBlocks.RED_MAPLE_SAPLING));
        identifierBuilderBiConsumer.accept(new Identifier(MOD_ID, "blocks/red_maple_leaf_pile"),
                BlockLootTableGenerator.dropsNothing());
    }
}
