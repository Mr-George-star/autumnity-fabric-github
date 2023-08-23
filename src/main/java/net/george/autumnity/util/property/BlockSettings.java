package net.george.autumnity.util.property;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @author Mr.George
 * @since v1.0.1.build.1
 * @version v1.0.0.build.6
 *
 * Assembled possible duplicate BLOCK SETTINGS.
 * This enum class based on {@link FabricBlockSettings}.
 */
//TODO：Remove to Special Library Mod!!
public enum BlockSettings {
    FLOWER(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)),
    SAPLING(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)),
    LADDER(FabricBlockSettings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque()),
    FLOWER_POT(FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()),
    PLANT_BUSH(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH));

    private final FabricBlockSettings value;

    BlockSettings(FabricBlockSettings settings) {
        this.value = settings;
    }

    public FabricBlockSettings getValue() {
        return this.value;
    }
}
