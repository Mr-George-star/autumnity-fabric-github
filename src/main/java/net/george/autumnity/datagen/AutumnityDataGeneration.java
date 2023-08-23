package net.george.autumnity.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * @author Mr.George
 * @since v1.0.0.build.1
 * @version v1.0.0.build.3
 *
 * This class reduces the number of files in the resource directory.
 */
public class AutumnityDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(AutumnityModelProvider::new);
        fabricDataGenerator.addProvider(AutumnityLootTableProvider::new);
        fabricDataGenerator.addProvider(AutumnityBlockTagProvider::new);
        fabricDataGenerator.addProvider(AutumnityItemTagProvider::new);
    }
}
