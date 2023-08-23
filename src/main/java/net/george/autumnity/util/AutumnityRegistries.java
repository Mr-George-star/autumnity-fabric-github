package net.george.autumnity.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.entity.passive.SnailEntity;
import net.george.autumnity.entity.passive.TurkeyEntity;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 * @version v1.0.0.build.7
 *
 * Integrating some Stuff registrations.
 */
public class AutumnityRegistries {
    public static void registerRegistries() {
        registerFlammableBlock();
        registerEntityAttributes();
    }

    private static void registerFlammableBlock() {
        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();

        instance.add(AutumnityBlocks.STRIPPED_MAPLE_LOG, 5, 5);
        instance.add(AutumnityBlocks.STRIPPED_MAPLE_WOOD, 5, 5);
        instance.add(AutumnityBlocks.SAPPY_MAPLE_LOG, 5, 5);
        instance.add(AutumnityBlocks.SAPPY_MAPLE_WOOD, 5, 5);
        instance.add(AutumnityBlocks.MAPLE_LOG, 5, 5);
        instance.add(AutumnityBlocks.MAPLE_WOOD, 5, 5);
        instance.add(AutumnityBlocks.MAPLE_PLANKS, 5, 20);
        instance.add(AutumnityBlocks.MAPLE_LEAVES, 30, 60);
        instance.add(AutumnityBlocks.MAPLE_LEAF_PILE, 30, 60);

        instance.add(AutumnityBlocks.YELLOW_MAPLE_LEAVES, 30, 60);
        instance.add(AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE, 30, 60);

        instance.add(AutumnityBlocks.ORANGE_MAPLE_LEAVES, 30, 60);
        instance.add(AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE, 30, 60);

        instance.add(AutumnityBlocks.RED_MAPLE_LEAVES, 30, 60);
        instance.add(AutumnityBlocks.RED_MAPLE_LEAF_PILE, 30, 60);
    }

    private static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(AutumnityEntities.SNAIL, SnailEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(AutumnityEntities.TURKEY, TurkeyEntity.createAttributes());
    }
}
