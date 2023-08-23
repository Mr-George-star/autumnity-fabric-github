package net.george.autumnity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.util.AutumnityTags;
import net.minecraft.block.Blocks;

/**
 * @author Mr.George
 * @since v1.0.0.build.3
 * @version v1.0.0.build.7
 *
 * This Provider for Generate Block Tags.
 */
public class AutumnityBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public AutumnityBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(AutumnityTags.Blocks.SMALL_FLOWERS)
                .add(AutumnityBlocks.AUTUMN_CROCUS);

        getOrCreateTagBuilder(AutumnityTags.Blocks.MAPLE_LOGS)
                .add(AutumnityBlocks.STRIPPED_MAPLE_LOG)
                .add(AutumnityBlocks.STRIPPED_MAPLE_WOOD)
                .add(AutumnityBlocks.SAPPY_MAPLE_LOG)
                .add(AutumnityBlocks.SAPPY_MAPLE_WOOD)
                .add(AutumnityBlocks.MAPLE_LOG)
                .add(AutumnityBlocks.MAPLE_WOOD);
        getOrCreateTagBuilder(AutumnityTags.Blocks.LOGS)
                .addOptionalTag(AutumnityTags.Blocks.MAPLE_LOGS);
        getOrCreateTagBuilder(AutumnityTags.Blocks.LOGS_THAT_BURN)
                .addOptionalTag(AutumnityTags.Blocks.MAPLE_LOGS);
        getOrCreateTagBuilder(AutumnityTags.Blocks.STAIRS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_STAIRS)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_STAIRS);
        getOrCreateTagBuilder(AutumnityTags.Blocks.SLABS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_SLAB)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_SLAB);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WALLS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_WALL)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_WALL);

        getOrCreateTagBuilder(AutumnityTags.Blocks.NEEDS_STONE_TOOL)
                .add(AutumnityBlocks.SNAIL_SHELL_BLOCK)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICKS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_STAIRS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_SLAB)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_WALL)
                .add(AutumnityBlocks.CHISELED_SNAIL_SHELL_BRICKS)
                .add(AutumnityBlocks.SNAIL_SHELL_TILES)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_STAIRS)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_SLAB)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_WALL);

        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_STAIRS)
                .add(AutumnityBlocks.MAPLE_STAIRS);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_SLABS)
                .add(AutumnityBlocks.MAPLE_SLAB);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_PRESSURE_PLATES)
                .add(AutumnityBlocks.MAPLE_PRESSURE_PLATE);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_BUTTONS)
                .add(AutumnityBlocks.MAPLE_BUTTON);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_FENCES)
                .add(AutumnityBlocks.MAPLE_FENCE);
        getOrCreateTagBuilder(AutumnityTags.Blocks.FENCE_GATES)
                .add(AutumnityBlocks.MAPLE_FENCE_GATE);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_DOORS)
                .add(AutumnityBlocks.MAPLE_DOOR);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WOODEN_TRAPDOORS)
                .add(AutumnityBlocks.MAPLE_TRAPDOOR);
        getOrCreateTagBuilder(AutumnityTags.Blocks.STANDING_SIGNS)
                .add(AutumnityBlocks.MAPLE_SIGN);
        getOrCreateTagBuilder(AutumnityTags.Blocks.WALL_SIGNS)
                .add(AutumnityBlocks.MAPLE_WALL_SIGN);

        getOrCreateTagBuilder(AutumnityTags.Blocks.LEAVES)
                .add(AutumnityBlocks.MAPLE_LEAVES)
                .add(AutumnityBlocks.YELLOW_MAPLE_LEAVES)
                .add(AutumnityBlocks.ORANGE_MAPLE_LEAVES)
                .add(AutumnityBlocks.RED_MAPLE_LEAVES);
        getOrCreateTagBuilder(AutumnityTags.Blocks.SAPLINGS)
                .add(AutumnityBlocks.MAPLE_SAPLING)
                .add(AutumnityBlocks.YELLOW_MAPLE_SAPLING)
                .add(AutumnityBlocks.ORANGE_MAPLE_SAPLING)
                .add(AutumnityBlocks.RED_MAPLE_SAPLING);
        getOrCreateTagBuilder(AutumnityTags.Blocks.MINEABLE_PICKAXE)
                .add(AutumnityBlocks.SNAIL_SHELL_BLOCK)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICKS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_STAIRS)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_SLAB)
                .add(AutumnityBlocks.SNAIL_SHELL_BRICK_WALL)
                .add(AutumnityBlocks.CHISELED_SNAIL_SHELL_BRICKS)
                .add(AutumnityBlocks.SNAIL_SHELL_TILES)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_STAIRS)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_SLAB)
                .add(AutumnityBlocks.SNAIL_SHELL_TILE_WALL);

        getOrCreateTagBuilder(AutumnityTags.Blocks.SLIPPERY_SNAIL_GOO_BLOCKS)
                .add(Blocks.WET_SPONGE);
        getOrCreateTagBuilder(AutumnityTags.Blocks.SNAIL_SNACKS)
                .add(Blocks.RED_MUSHROOM).add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.CRIMSON_FUNGUS).add(Blocks.WARPED_FUNGUS);

        getOrCreateTagBuilder(AutumnityTags.Blocks.KNIFE)
                .add(AutumnityBlocks.TURKEY)
                .add(AutumnityBlocks.COOKED_TURKEY);
    }
}
