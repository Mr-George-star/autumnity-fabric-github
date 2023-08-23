package net.george.autumnity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.george.autumnity.advancement.AutumnityCriterion;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.effect.AutumnityEffects;
import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.event.AutumnityEvents;
import net.george.autumnity.item.AutumnityItems;
import net.george.autumnity.particle.AutumnityParticles;
import net.george.autumnity.sound.AutumnitySounds;
import net.george.autumnity.util.AutumnityRegistries;
import net.george.autumnity.world.biome.AutumnityBiomes;
import net.george.autumnity.world.feature.AutumnityConfiguredFeatures;
import net.george.autumnity.world.gen.AutumnityWorldGeneration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * @since 1.0.0.build.1
 * @version 1.0.0.build.6
 * @author Mr.George
 * @implNote forge: Holder<> = fabric: RegistryEntry<>
 *     forge: BlockPos.offset(int, int, int) = fabric: BlockPos.add(int, int, int)
 *     BlockGetter = BlockView
 */
public class Autumnity implements ModInitializer {
	public static final String MOD_ID = "autumnity";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	private static boolean initialized = false;
	private static final ArrayList<Runnable> runnables = new ArrayList<>(1);

	@Override
	public void onInitialize() {
		LOGGER.info("Autumnity is Initializing...");

		AutumnityConfiguredFeatures.registerConfiguredFeatures();
		AutumnityBiomes.registerBiomes();

		AutumnitySounds.registerSounds();

		AutumnityItems.registerItems();
		AutumnityBlocks.registerBlocks();

		AutumnityParticles.registerParticles();
		AutumnityEffects.registerEffects();

		AutumnityRegistries.registerRegistries();
		AutumnityCriterion.registerCriterion();

		AutumnityEffects.registerEffects();

		AutumnityEvents.registerEvents();
		AutumnityEntities.registerEntities();

		AutumnityWorldGeneration.registerWorldGeneration();

		checkPrecedenceMod();
		initialized = true;
		for (Runnable callback : runnables) {
			callback.run();
		}
	}

	private static void checkPrecedenceMod() {
		if (!FabricLoader.getInstance().isModLoaded("terrablender")) {
			LOGGER.error("The dependent Mod TerraBlender of this Mod isn't loaded, please check your Mod List!");
		}
	}

	public static void callbackWhenInitialized(Runnable callback) {
		if (initialized) {
			callback.run();
		} else {
			runnables.add(callback);
		}
	}
}
