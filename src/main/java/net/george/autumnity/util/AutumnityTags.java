package net.george.autumnity.util;

import net.george.autumnity.Autumnity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author Mr.George
 * @since v1.0.0.build.3
 * @version v1.0.0.build.7
 */
@SuppressWarnings({"SameParameterValue"})
public class AutumnityTags {
    public static class Blocks {
        public static final TagKey<Block> SMALL_FLOWERS = createTag("minecraft", "small_flowers");

        public static final TagKey<Block> MAPLE_LOGS = createTag(null, "maple_logs");
        public static final TagKey<Block> LOGS = createTag("minecraft", "logs");
        public static final TagKey<Block> LOGS_THAT_BURN = createTag("minecraft", "logs_that_burn");
        public static final TagKey<Block> STAIRS = createTag("minecraft", "stairs");
        public static final TagKey<Block> SLABS = createTag("minecraft", "slabs");
        public static final TagKey<Block> WALLS = createTag("minecraft", "walls");

        public static final TagKey<Block> NEEDS_STONE_TOOL = createTag("minecraft", "needs_stone_tool");

        public static final TagKey<Block> WOODEN_STAIRS = createTag("minecraft", "wooden_stairs");
        public static final TagKey<Block> WOODEN_SLABS = createTag("minecraft", "wooden_slabs");
        public static final TagKey<Block> WOODEN_PRESSURE_PLATES = createTag("minecraft", "wooden_pressure_plates");
        public static final TagKey<Block> WOODEN_BUTTONS = createTag("minecraft", "wooden_buttons");
        public static final TagKey<Block> WOODEN_FENCES = createTag("minecraft", "wooden_fences");
        public static final TagKey<Block> FENCE_GATES = createTag("minecraft", "fence_gates");
        public static final TagKey<Block> WOODEN_DOORS = createTag("minecraft", "wooden_doors");
        public static final TagKey<Block> WOODEN_TRAPDOORS = createTag("minecraft", "wooden_trapdoors");
        public static final TagKey<Block> STANDING_SIGNS = createTag("minecraft", "standing_signs");
        public static final TagKey<Block> WALL_SIGNS = createTag("minecraft", "wall_signs");

        public static final TagKey<Block> LEAVES = createTag("minecraft", "leaves");
        public static final TagKey<Block> SAPLINGS = createTag("minecraft", "saplings");
        public static final TagKey<Block> MINEABLE_PICKAXE = createTag("minecraft", "mineable/pickaxe");

        public static final TagKey<Block> SLIPPERY_SNAIL_GOO_BLOCKS = createTag(null, "slippery_snail_goo_blocks");
        public static final TagKey<Block> SNAIL_SNACKS = createTag(null, "snail_snacks");

        public static final TagKey<Block> KNIFE = createTag("farmersdelight", "mineable/knife");

        private static TagKey<Block> createTag(@Nullable String nameplace, String name) {
            String place = Objects.requireNonNullElse(nameplace, Autumnity.MOD_ID);
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(place, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SMALL_FLOWERS = createTag("minecraft", "small_flowers");

        public static final TagKey<Item> WOODEN_STAIRS = createTag("minecraft", "wooden_stairs");
        public static final TagKey<Item> WOODEN_SLABS = createTag("minecraft", "wooden_slabs");
        public static final TagKey<Item> WOODEN_PRESSURE_PLATES = createTag("minecraft", "wooden_pressure_plates");
        public static final TagKey<Item> WOODEN_BUTTONS = createTag("minecraft", "wooden_buttons");
        public static final TagKey<Item> WOODEN_FENCES = createTag("minecraft", "wooden_fences");
        public static final TagKey<Item> WOODEN_DOORS = createTag("minecraft", "wooden_doors");
        public static final TagKey<Item> WOODEN_TRAPDOORS = createTag("minecraft", "wooden_trapdoors");

        public static final TagKey<Item> SNAIL_FOOD = createTag(null, "snail_food");
        public static final TagKey<Item> SNAIL_SNACKS = createTag(null, "snail_snacks");
        public static final TagKey<Item> SNAIL_TEMPT_SNACKS = createTag(null, "snail_tempt_snacks");
        public static final TagKey<Item> SNAIL_GLOW_SNACKS = createTag(null, "snail_glow_snacks");
        public static final TagKey<Item> SNAIL_SPEED_SNACKS = createTag(null, "snail_speed_snacks");
        public static final TagKey<Item> TURKEY_FOOD = createTag(null, "turkey_food");

        public static final TagKey<Item> KNIVES = createTag("farmersdelight", "tools/knives");

        private static TagKey<Item> createTag(@Nullable String nameplace, String name) {
            String place = Objects.requireNonNullElse(nameplace, Autumnity.MOD_ID);
            return TagKey.of(Registry.ITEM_KEY, new Identifier(place, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> TAIGAS = createTag("c", "taigas");

        private static TagKey<Biome> createTag(@Nullable String nameplace, String name) {
            String place = Objects.requireNonNullElse(nameplace, Autumnity.MOD_ID);
            return TagKey.of(Registry.BIOME_KEY, new Identifier(place, name));
        }
    }
}
