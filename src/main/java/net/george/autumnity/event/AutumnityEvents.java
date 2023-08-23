package net.george.autumnity.event;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.george.autumnity.advancement.AutumnityCriterion;
import net.george.autumnity.effect.AutumnityEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.TypedActionResult;

/**
 * @author Mr.George
 * @since v1.0.0.build.6
 */
public class AutumnityEvents {
    public static class OnPlayerEaten {
        public static void register() {
            UseItemCallback.EVENT.register((player, world, hand) -> {
                ItemStack stack = player.getStackInHand(hand);
                Item item = stack.getItem();
                if (item.isFood()) {
                    if (player.hasStatusEffect(AutumnityEffects.FOUL_TASTE) && player.canConsume(false)) {
                        FoodComponent component = item.getFoodComponent();
                        boolean flag = false;

                        if (item instanceof SuspiciousStewItem) {
                            NbtCompound nbtCompound = stack.getNbt();
                            if (nbtCompound != null && nbtCompound.contains("Effects", 9)) {
                                NbtList nbtElements = nbtCompound.getList("Effects", 10);

                                for (int i = 0; i < nbtElements.size(); ++i) {
                                    NbtCompound nbtCompound2 = nbtElements.getCompound(i);
                                    StatusEffect effect = StatusEffect.byRawId(nbtCompound2.getByte("EffectId"));

                                    if (effect == AutumnityEffects.FOUL_TASTE) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            for (Pair<StatusEffectInstance, Float> pair : component.getStatusEffects()) {
                                if (pair.getFirst().getEffectType() == AutumnityEffects.FOUL_TASTE) {
                                    flag = true;
                                    break;
                                }
                            }
                        }

                        if (!flag) {
                            HungerManager manager = player.getHungerManager();
                            int hunger = component.getHunger();

                            stack.setCount(stack.getCount() - 1);
                            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
                            manager.add(hunger / 2, 0.0F);
                            updateFoulTaste(player);

                            if (player instanceof ServerPlayerEntity) {
                                Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)player, stack);
                            }
                        }
                    } else {
                        return TypedActionResult.pass(stack);
                    }
                }
                return TypedActionResult.success(stack);
            });
        }

        public static void updateFoulTaste(PlayerEntity player) {
            StatusEffectInstance effect = player.getStatusEffect(AutumnityEffects.FOUL_TASTE);

            player.removeStatusEffect(AutumnityEffects.FOUL_TASTE);
            if (effect.getAmplifier() > 0) {
                player.addStatusEffect(new StatusEffectInstance(AutumnityEffects.FOUL_TASTE,
                        effect.getDuration(), effect.getAmplifier() - 1));
            }

            if (player instanceof ServerPlayerEntity playerEntity) {
                if (!player.world.isClient()) {
                    AutumnityCriterion.CURE_FOUL_TASTE.trigger(playerEntity);
                }
            }
        }
    }

    public static void registerEvents() {
        OnPlayerEaten.register();
    }
}