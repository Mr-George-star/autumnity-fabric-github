package net.george.autumnity.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.george.autumnity.Autumnity;
import net.george.autumnity.entity.passive.SnailEntity;
import net.george.autumnity.entity.passive.TurkeyEntity;
import net.george.autumnity.entity.projectile.TurkeyEggEntity;
import net.george.autumnity.entity.special.FallingHeadBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings({"SameParameterValue"})
public class AutumnityEntities {
    public static final EntityType<SnailEntity> SNAIL = registerEntity("snail",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnailEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8F, 0.9F)).build());

    public static final EntityType<TurkeyEntity> TURKEY = registerEntity("turkey",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TurkeyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 0.8F)).build());

    public static final EntityType<TurkeyEggEntity> TURKEY_EGG = registerEntity("turkey_egg",
            FabricEntityTypeBuilder.<TurkeyEggEntity>create(SpawnGroup.MISC, TurkeyEggEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10).build());

    public static final EntityType<FallingHeadBlockEntity> FALLING_HEAD_BLOCK = registerEntity("falling_head_block",
            FabricEntityTypeBuilder.<FallingHeadBlockEntity>create(SpawnGroup.MISC, FallingHeadBlockEntity::new)
                    .dimensions(EntityDimensions.fixed(0.98F, 0.98F)).build());

    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Autumnity.MOD_ID, name), entity);
    }

    public static void registerEntities() {
        Autumnity.LOGGER.debug("Registering Entities for " + Autumnity.MOD_ID);
    }
}
