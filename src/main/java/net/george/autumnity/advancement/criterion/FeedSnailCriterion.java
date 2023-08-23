package net.george.autumnity.advancement.criterion;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.george.autumnity.Autumnity;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FeedSnailCriterion extends AbstractCriterion<FeedSnailCriterion.Condition> {
    public static final Identifier ID = new Identifier(Autumnity.MOD_ID, "feed_snail");

    @Override
    protected Condition conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Condition(playerPredicate, ItemPredicate.fromJson(obj.get("item")));
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack) {
        this.trigger(player, (condition -> condition.test(stack)));
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static class Condition extends AbstractCriterionConditions {
        private final ItemPredicate item;

        public Condition(EntityPredicate.Extended playerPredicate, ItemPredicate itemPredicate) {
            super(FeedSnailCriterion.ID, playerPredicate);
            this.item = itemPredicate;
        }

        public static Condition any() {
            return new Condition(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY);
        }

        public static Condition forItem(ItemConvertible convertible) {
            return new Condition(EntityPredicate.Extended.EMPTY, new ItemPredicate(null,
                    ImmutableSet.of(convertible.asItem()), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY,
                    new EnchantmentPredicate[]{EnchantmentPredicate.ANY}, new EnchantmentPredicate[]{EnchantmentPredicate.ANY},
                    null, NbtPredicate.ANY));
        }

        public boolean test(ItemStack stack) {
            return this.item.test(stack);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject object = super.toJson(predicateSerializer);
            object.add("item", this.item.toJson());
            return object;
        }
    }
}
