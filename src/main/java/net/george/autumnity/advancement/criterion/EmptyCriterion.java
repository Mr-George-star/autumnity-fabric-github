package net.george.autumnity.advancement.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * @author Mr.George
 * @since v1.0.0.build.6
 */
public class EmptyCriterion extends AbstractCriterion<EmptyCriterion.Condition> {
    private final Identifier id;

    public EmptyCriterion(Identifier id) {
        this.id = id;
    }

    @Override
    protected Condition conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Condition(id);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public void trigger(ServerPlayerEntity player) {
        trigger(player, predicate -> true);
    }

    public static class Condition extends AbstractCriterionConditions {
        private final Identifier id;

        public Condition(Identifier id) {
            super(id, EntityPredicate.Extended.EMPTY);
            this.id = id;
        }

        @Override
        public Identifier getId() {
            return this.id;
        }
    }
}
