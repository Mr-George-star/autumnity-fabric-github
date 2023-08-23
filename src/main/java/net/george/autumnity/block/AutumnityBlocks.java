package net.george.autumnity.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.george.autumnity.Autumnity;
import net.george.autumnity.block.custom.*;
import net.george.autumnity.effect.AutumnityEffects;
import net.george.autumnity.item.AutumnityItemGroup;
import net.george.autumnity.util.AutumnitySignTypes;
import net.george.autumnity.util.property.BlockSettings;
import net.george.autumnity.util.property.WoodSetProperties;
import net.george.autumnity.world.feature.tree.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 * @version v1.0.0.build.7
 */
@SuppressWarnings({"SameParameterValue"})
public class AutumnityBlocks {

    public static final Block SNAIL_GOO = registerBlock("snail_goo",
            new SnailGooBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.TERRACOTTA_WHITE)
                    .nonOpaque().noCollision().sounds(BlockSoundGroup.HONEY)), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_GOO_BLOCK = registerBlock("snail_goo_block",
            new SnailGooFullBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.TERRACOTTA_WHITE)
                    .nonOpaque().sounds(BlockSoundGroup.HONEY)), AutumnityItemGroup.AUTUMNITY);
    public static final Block PANCAKE = registerBlock("pancake",
            new PancakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL)), AutumnityItemGroup.AUTUMNITY);
    public static final Block AUTUMN_CROCUS = registerBlock("autumn_crocus",
            new FlowerBlock(AutumnityEffects.FOUL_TASTE, 16, BlockSettings.FLOWER.getValue()),
            AutumnityItemGroup.AUTUMNITY);
    public static final Block POTTED_AUTUMN_CROCUS = registerBlockWithoutItem("potted_autumn_crocus",
            new FlowerPotBlock(AUTUMN_CROCUS, BlockSettings.FLOWER_POT.getValue()));

    public static final Block TURKEY = registerBlock("turkey",
            new TurkeyBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).nonOpaque().sounds(BlockSoundGroup.WOOL)),
            AutumnityItemGroup.AUTUMNITY);
    public static final Block COOKED_TURKEY = registerBlock("cooked_turkey",
            new CookedTurkeyBlock(FabricBlockSettings.of(Material.WOOL).strength(0.5F).nonOpaque().sounds(BlockSoundGroup.WOOL)),
            AutumnityItemGroup.AUTUMNITY);

    public static final Block FOUL_BERRY_BUSH = registerBlockWithoutItem("foul_berry_bush",
            new FoulBerryBushBlock(BlockSettings.PLANT_BUSH.getValue()));
    public static final Block TALL_FOUL_BERRY_BUSH = registerBlockWithoutItem("tall_foul_berry_bush",
            new TallFoulBerryBushBlock(BlockSettings.PLANT_BUSH.getValue()));
    public static final Block POTTED_FOUL_BERRIES = registerBlockWithoutItem("potted_foul_berries",
            new FlowerPotBlock(FOUL_BERRY_BUSH, BlockSettings.FLOWER_POT.getValue()));

    public static final Block SNAIL_SHELL_BLOCK = registerBlock("snail_shell_block",
            new SnailShellBlock(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_BRICKS = registerBlock("snail_shell_bricks",
            new Block(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_BRICK_STAIRS = registerBlock("snail_shell_brick_stairs",
            new StairsBlock(SNAIL_SHELL_BRICKS.getDefaultState(), Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_BRICK_SLAB = registerBlock("snail_shell_brick_slab",
            new SlabBlock(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_BRICK_WALL = registerBlock("snail_shell_brick_wall",
            new WallBlock(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block CHISELED_SNAIL_SHELL_BRICKS = registerBlock("chiseled_snail_shell_bricks",
            new Block(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_TILES = registerBlock("snail_shell_tiles",
            new Block(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_TILE_STAIRS = registerBlock("snail_shell_tile_stairs",
            new StairsBlock(SNAIL_SHELL_BRICKS.getDefaultState(), Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_TILE_SLAB = registerBlock("snail_shell_tile_slab",
            new SlabBlock(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);
    public static final Block SNAIL_SHELL_TILE_WALL = registerBlock("snail_shell_tile_wall",
            new WallBlock(Properties.SNAIL_SHELL), AutumnityItemGroup.AUTUMNITY);

    public static final Block STRIPPED_MAPLE_LOG = registerBlock("stripped_maple_log",
            new PillarBlock(Properties.MAPLE.log()), AutumnityItemGroup.AUTUMNITY);
    public static final Block STRIPPED_MAPLE_WOOD = registerBlock("stripped_maple_wood",
            new PillarBlock(Properties.MAPLE.log()), AutumnityItemGroup.AUTUMNITY);
    public static final Block SAPPY_MAPLE_LOG = registerBlock("sappy_maple_log",
            new SappyMapleBlock(STRIPPED_MAPLE_LOG, Properties.MAPLE.log()), AutumnityItemGroup.AUTUMNITY);
    public static final Block SAPPY_MAPLE_WOOD = registerBlock("sappy_maple_wood",
            new SappyMapleBlock(STRIPPED_MAPLE_WOOD, Properties.MAPLE.log()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_LOG = registerBlock("maple_log",
            new MapleBlock(STRIPPED_MAPLE_LOG, SAPPY_MAPLE_LOG, Properties.MAPLE.log()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_WOOD = registerBlock("maple_wood",
            new MapleBlock(STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_WOOD, Properties.MAPLE.log()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_PLANKS = registerBlock("maple_planks",
            new Block(Properties.MAPLE.planks()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_STAIRS = registerBlock("maple_stairs",
            new StairsBlock(MAPLE_PLANKS.getDefaultState(), Properties.MAPLE.planks()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_SLAB = registerBlock("maple_slab",
            new SlabBlock(Properties.MAPLE.planks()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_PRESSURE_PLATE = registerBlock("maple_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Properties.MAPLE.pressurePlate()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_BUTTON = registerBlock("maple_button",
            new WoodenButtonBlock(Properties.MAPLE.button()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_FENCE = registerBlock("maple_fence",
            new FenceBlock(Properties.MAPLE.planks()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_FENCE_GATE = registerBlock("maple_fence_gate",
            new FenceGateBlock(Properties.MAPLE.planks()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_DOOR = registerBlock("maple_door",
            new DoorBlock(Properties.MAPLE.door()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_TRAPDOOR = registerBlock("maple_trapdoor",
            new TrapdoorBlock(Properties.MAPLE.trapdoor()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_SIGN = registerBlockWithoutItem("maple_sign",
            new SignBlock(FabricBlockSettings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD),
                    AutumnitySignTypes.MAPLE));
    public static final Block MAPLE_WALL_SIGN = registerBlockWithoutItem("maple_wall_sign",
            new WallSignBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_ORANGE).noCollision().strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD), AutumnitySignTypes.MAPLE));

    public static final Block MAPLE_LEAVES = registerBlock("maple_leaves",
            new MapleLeavesBlock(Properties.MAPLE.leaves()), AutumnityItemGroup.AUTUMNITY);
    public static final Block MAPLE_SAPLING = registerBlock("maple_sapling",
            new SaplingBlock(new MapleSaplingGenerator(),
                    BlockSettings.SAPLING.getValue()), AutumnityItemGroup.AUTUMNITY);
    public static final Block POTTED_MAPLE_SAPLING = registerBlockWithoutItem("potted_maple_sapling",
            new FlowerPotBlock(MAPLE_SAPLING, BlockSettings.FLOWER_POT.getValue()));
    public static final Block MAPLE_LEAF_PILE = registerBlock("maple_leaf_pile",
            new LeafPileBlock(Properties.MAPLE.leafPile()), AutumnityItemGroup.AUTUMNITY);

    public static final Block YELLOW_MAPLE_LEAVES = registerBlock("yellow_maple_leaves",
            new ColoredMapleLeavesBlock(Properties.MAPLE.leaves(), 16766735), AutumnityItemGroup.AUTUMNITY);
    public static final Block YELLOW_MAPLE_SAPLING = registerBlock("yellow_maple_sapling",
            new SaplingBlock(new YellowMapleSaplingGenerator(),
                    BlockSettings.SAPLING.getValue()), AutumnityItemGroup.AUTUMNITY);
    public static final Block POTTED_YELLOW_MAPLE_SAPLING = registerBlockWithoutItem("potted_yellow_maple_sapling",
            new FlowerPotBlock(YELLOW_MAPLE_SAPLING, BlockSettings.FLOWER_POT.getValue()));
    public static final Block YELLOW_MAPLE_LEAF_PILE = registerBlock("yellow_maple_leaf_pile",
            new LeafPileBlock(Properties.MAPLE.leafPile()), AutumnityItemGroup.AUTUMNITY);

    public static final Block ORANGE_MAPLE_LEAVES = registerBlock("orange_maple_leaves",
            new ColoredMapleLeavesBlock(Properties.MAPLE.leaves(), 16745768), AutumnityItemGroup.AUTUMNITY);
    public static final Block ORANGE_MAPLE_SAPLING = registerBlock("orange_maple_sapling",
            new SaplingBlock(new OrangeMapleSaplingGenerator(),
                    BlockSettings.SAPLING.getValue()), AutumnityItemGroup.AUTUMNITY);
    public static final Block POTTED_ORANGE_MAPLE_SAPLING = registerBlockWithoutItem("potted_orange_maple_sapling",
            new FlowerPotBlock(ORANGE_MAPLE_SAPLING, BlockSettings.FLOWER_POT.getValue()));
    public static final Block ORANGE_MAPLE_LEAF_PILE = registerBlock("orange_maple_leaf_pile",
            new LeafPileBlock(Properties.MAPLE.leafPile()), AutumnityItemGroup.AUTUMNITY);

    public static final Block RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            new ColoredMapleLeavesBlock(Properties.MAPLE.leaves(), 12665871), AutumnityItemGroup.AUTUMNITY);
    public static final Block RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
            new SaplingBlock(new RedMapleSaplingGenerator(),
                    BlockSettings.SAPLING.getValue()), AutumnityItemGroup.AUTUMNITY);
    public static final Block POTTED_RED_MAPLE_SAPLING = registerBlockWithoutItem("potted_red_maple_sapling",
            new FlowerPotBlock(RED_MAPLE_SAPLING, BlockSettings.FLOWER_POT.getValue()));
    public static final Block RED_MAPLE_LEAF_PILE = registerBlock("red_maple_leaf_pile",
            new LeafPileBlock(Properties.MAPLE.leafPile()), AutumnityItemGroup.AUTUMNITY);


    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Autumnity.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(Autumnity.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Autumnity.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }



    public static void registerBlocks() {
        Autumnity.LOGGER.debug("Registering Blocks for " + Autumnity.MOD_ID);
    }

    public static final class Properties {
        public static final WoodSetProperties MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).build();
        public static final FabricBlockSettings SNAIL_SHELL = FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_BROWN)
                .requiresTool().strength(3.0F, 9.0F);
    }
}
