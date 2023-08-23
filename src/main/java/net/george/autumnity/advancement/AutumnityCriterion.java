package net.george.autumnity.advancement;

import net.george.autumnity.Autumnity;
import net.george.autumnity.advancement.criterion.EmptyCriterion;
import net.george.autumnity.advancement.criterion.FeedSnailCriterion;
import net.minecraft.util.Identifier;

/**
 * @author Mr.George
 * @since v1.0.0.build.6
 */
public class AutumnityCriterion {
    public static final FeedSnailCriterion FEED_SNAIL = new FeedSnailCriterion();
    public static final EmptyCriterion CURE_FOUL_TASTE =
            new EmptyCriterion(new Identifier(Autumnity.MOD_ID, "cure_foul_taste"));

    public static void registerCriterion() {
        Autumnity.LOGGER.debug("Registering Advancement Criterion for " + Autumnity.MOD_ID);
    }
}
