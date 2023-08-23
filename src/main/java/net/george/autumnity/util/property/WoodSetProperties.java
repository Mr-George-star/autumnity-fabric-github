package net.george.autumnity.util.property;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * @param color
 * @param material
 * @param woodSoundGroup
 * @param leavesSoundGroup
 *
 * @author Mr.George
 * @since v1.0.0.build.1
 *
 * Simplified the process of registering wooden blocks.
 * This record class based on {@link FabricBlockSettings}.
 */
//TODO：Remove to Special Library Mod!!
    @SuppressWarnings("unused")
public record WoodSetProperties(MapColor color, Material material, BlockSoundGroup woodSoundGroup, BlockSoundGroup leavesSoundGroup) {

    private static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }
    private static Boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
    private static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return true;
    }
    private static Boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static Builder builder(MapColor color) {
        return new Builder(color);
    }

    public FabricBlockSettings planks() {
        return FabricBlockSettings.of(this.material, this.color).strength(2.0F, 3.0F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings log() {
        return FabricBlockSettings.of(this.material, this.color).strength(2.0F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings leaves() {
        return FabricBlockSettings.of(this.material, this.color).strength(2.0F).ticksRandomly().sounds(this.leavesSoundGroup)
                .nonOpaque().allowsSpawning(WoodSetProperties::canSpawnOnLeaves).suffocates(WoodSetProperties::never).blockVision(WoodSetProperties::never);
    }

    public FabricBlockSettings pressurePlate() {
        return FabricBlockSettings.of(this.material, this.color).noCollision().strength(0.5F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings trapdoor() {
        return FabricBlockSettings.of(this.material, this.color).strength(3.0F).sounds(this.woodSoundGroup).nonOpaque().allowsSpawning(WoodSetProperties::never);
    }

    public FabricBlockSettings button() {
        return FabricBlockSettings.of(this.material, this.color).noCollision().strength(0.5F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings door() {
        return FabricBlockSettings.of(this.material, this.color).strength(3.0F).sounds(this.woodSoundGroup).nonOpaque();
    }

    public FabricBlockSettings beehive() {
        return FabricBlockSettings.of(this.material, this.color).strength(0.6F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings bookshelf() {
        return FabricBlockSettings.of(this.material, this.color).strength(1.5F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings ladder() {
        return BlockSettings.LADDER.getValue();
    }

    public FabricBlockSettings chest() {
        return FabricBlockSettings.of(this.material, this.color).strength(2.5F).sounds(this.woodSoundGroup);
    }

    public FabricBlockSettings leafPile() {
        return FabricBlockSettings.of(this.material, this.color)
                .noCollision().nonOpaque().strength(0.2F).sounds(this.leavesSoundGroup);
    }

    public FabricBlockSettings leftCarpet() {
        return FabricBlockSettings.of(this.material, this.color).nonOpaque().strength(0.0F).sounds(this.leavesSoundGroup);
    }

    public FabricBlockSettings post() {
        return FabricBlockSettings.of(this.material, this.color).strength(2.0F, 3.0F).sounds(this.woodSoundGroup);
    }

    public MapColor color() {
        return this.color;
    }

    public Material material() {
        return this.material;
    }

    public BlockSoundGroup woodSoundGroup() {
        return this.woodSoundGroup;
    }

    public BlockSoundGroup leavesSoundGroup() {
        return this.leavesSoundGroup;
    }

    public static final class Builder {
        private final MapColor color;
        private Material material = Material.WOOD;
        private BlockSoundGroup woodSoundGroup = BlockSoundGroup.WOOD;
        private BlockSoundGroup leavesSoundGroup = BlockSoundGroup.GRASS;

        private Builder(MapColor color) {
            this.color = color;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder woodSound(BlockSoundGroup group) {
            this.woodSoundGroup = group;
            return this;
        }

        public Builder leavesSound(BlockSoundGroup group) {
            this.leavesSoundGroup = group;
            return this;
        }

        public WoodSetProperties build() {
            return new WoodSetProperties(this.color, this.material, this.woodSoundGroup, this.leavesSoundGroup);
        }
    }
}
