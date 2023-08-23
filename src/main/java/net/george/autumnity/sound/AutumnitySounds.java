package net.george.autumnity.sound;

import net.george.autumnity.Autumnity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @since v1.0.0.build.5
 * @version v1.0.0.build.7
 * @author Mr.George
 */
public class AutumnitySounds {

    public static final SoundEvent ENTITY_SNAIL_EAT = registerSound("entity.snail.eat");
    public static final SoundEvent ENTITY_SNAIL_HURT = registerSound("entity.snail.hurt");
    public static final SoundEvent ENTITY_SNAIL_DEATH = registerSound("entity.snail.death");
    public static final SoundEvent ENTITY_SNAIL_STEP = registerSound("entity.snail.step");
    public static final SoundEvent ENTITY_TURKEY_AMBIENT = registerSound("entity.turkey.ambient");
    public static final SoundEvent ENTITY_TURKEY_AGGRO = registerSound("entity.turkey.aggro");
    public static final SoundEvent ENTITY_TURKEY_HURT = registerSound("entity.turkey.hurt");
    public static final SoundEvent ENTITY_TURKEY_DEATH = registerSound("entity.turkey.death");
    public static final SoundEvent ENTITY_TURKEY_EGG = registerSound("entity.turkey.egg");
    public static final SoundEvent BLOCK_TURKEY_CUT = registerSound("block.turkey.cut");
    public static final SoundEvent ITEM_ARMOR_EQUIP_SNAIL = registerSound("item.armor.equip_snail");

    private static SoundEvent registerSound(String name) {
        Identifier id = new Identifier(Autumnity.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSounds() {
        Autumnity.LOGGER.debug("Registering Sound for " + Autumnity.MOD_ID);
    }
}
