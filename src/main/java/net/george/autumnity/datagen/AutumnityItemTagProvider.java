package net.george.autumnity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.item.AutumnityItems;
import net.george.autumnity.util.AutumnityTags;
import net.minecraft.item.Items;

/**
 * @author Mr.George
 * @since v1.0.0.build.3
 * @version v1.0.0.build.7
 *
 * This Provider for Generate Item Tags.
 */
public class AutumnityItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public AutumnityItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(AutumnityTags.Items.SMALL_FLOWERS)
                .add(AutumnityBlocks.AUTUMN_CROCUS.asItem());

        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_STAIRS)
                .add(AutumnityBlocks.MAPLE_STAIRS.asItem());
        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_SLABS)
                .add(AutumnityBlocks.MAPLE_SLAB.asItem());
        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_PRESSURE_PLATES)
                .add(AutumnityBlocks.MAPLE_PRESSURE_PLATE.asItem());
        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_BUTTONS)
                .add(AutumnityBlocks.MAPLE_BUTTON.asItem());
        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_FENCES)
                .add(AutumnityBlocks.MAPLE_FENCE.asItem());
        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_DOORS)
                .add(AutumnityBlocks.MAPLE_DOOR.asItem());
        getOrCreateTagBuilder(AutumnityTags.Items.WOODEN_TRAPDOORS)
                .add(AutumnityBlocks.MAPLE_TRAPDOOR.asItem());

        getOrCreateTagBuilder(AutumnityTags.Items.SNAIL_FOOD)
                .add(Items.MUSHROOM_STEW).add(Items.SUSPICIOUS_STEW);
        getOrCreateTagBuilder(AutumnityTags.Items.SNAIL_SNACKS)
                .add(Items.RED_MUSHROOM).add(Items.BROWN_MUSHROOM)
                .addOptionalTag(AutumnityTags.Items.SNAIL_SPEED_SNACKS)
                .addOptionalTag(AutumnityTags.Items.SNAIL_GLOW_SNACKS);
        getOrCreateTagBuilder(AutumnityTags.Items.SNAIL_TEMPT_SNACKS)
                .add(Items.WARPED_FUNGUS_ON_A_STICK)
                .addTag(AutumnityTags.Items.SNAIL_FOOD)
                .addTag(AutumnityTags.Items.SNAIL_SNACKS);
        getOrCreateTagBuilder(AutumnityTags.Items.SNAIL_GLOW_SNACKS);
        getOrCreateTagBuilder(AutumnityTags.Items.SNAIL_SPEED_SNACKS)
                .add(Items.CRIMSON_FUNGUS).add(Items.WARPED_FUNGUS);
        getOrCreateTagBuilder(AutumnityTags.Items.TURKEY_FOOD)
                .add(AutumnityItems.FOUL_BERRIES);
        getOrCreateTagBuilder(AutumnityTags.Items.KNIVES);
    }
}
