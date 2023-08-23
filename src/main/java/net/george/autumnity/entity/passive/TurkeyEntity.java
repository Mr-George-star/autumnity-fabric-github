package net.george.autumnity.entity.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.item.AutumnityItems;
import net.george.autumnity.sound.AutumnitySounds;
import net.george.autumnity.util.AutumnityTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.UUID;

/**
 * @since 1.0.0.build.6
 * @author Mr.George
 */
@SuppressWarnings("unused")
public class TurkeyEntity extends AnimalEntity implements Angerable {
    private static final TrackedData<Integer> ANGER_TIME =
            DataTracker.registerData(TurkeyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private float wingRotation;
    private float destPos;
    private float oFlapSpeed;
    private float oFlap;
    private float wingRotDelta = 1.0F;

    private float peckTicks;
    private float prevPeckTicks;

    public int timeUntilNextEgg = this.random.nextInt(9600) + 9600;
    public boolean turkeyJockey;

    private static final UniformIntProvider ANGER_RANGE = TimeHelper.betweenSeconds(20, 39);
    private UUID lastHurtBy;

    public TurkeyEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new TurkeyEntity.EscapeDangerGoal(this));
        this.goalSelector.add(2, new PounceAtTargetGoal(this, 0.3F));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.4D, false));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(5, new TemptGoal(this, 1.0D, Ingredient.fromTag(AutumnityTags.Items.TURKEY_FOOD), false));
        this.goalSelector.add(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, new TurkeyEntity.RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(3, new TurkeyEntity.JockeyTargetGoal<>(this, PlayerEntity.class));
        this.targetSelector.add(4, new UniversalAngerGoal<>(this, true));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 4) {
            this.peckTicks = 8;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.peckTicks = 8;
        this.world.sendEntityStatus(this, (byte) 4);
        return super.tryAttack(target);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (this.world.isClient) {
            this.oFlap = this.wingRotation;
            this.oFlapSpeed = this.destPos;
            this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
            this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
            if (!this.onGround && this.wingRotDelta < 1.0F) {
                this.wingRotDelta = 1.0F;
            }
            this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);
            this.wingRotation += this.wingRotDelta * 2.0F;
        }

        this.prevPeckTicks = this.peckTicks;

        if (this.peckTicks > 0) {
            this.peckTicks--;
        }

        Vec3d vector = this.getVelocity();
        if (!this.onGround && vector.y < 0.0D) {
            this.setVelocity(vector.multiply(1.0D, 0.6D, 1.0D));
        }

        if (!this.world.isClient) {
            if (this.isAlive() && !this.isBaby() && !this.isTurkeyJockey() && --this.timeUntilNextEgg <= 0) {
                this.playSound(AutumnitySounds.ENTITY_TURKEY_EGG, 1.0F,
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(AutumnityItems.TURKEY_EGG);
                this.timeUntilNextEgg = this.getRandomNextEggTime(this.random);
            }

            this.tickAngerLogic((ServerWorld) this.world, true);
        }
    }

    @Environment(EnvType.CLIENT)
    public float getWingRotation(float partialTicks) {
        float f = MathHelper.lerp(partialTicks, this.oFlap, this.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, this.oFlapSpeed, this.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Environment(EnvType.CLIENT)
    public float getPeckProgress(float partialTicks) {
        float f = MathHelper.lerp(partialTicks, this.prevPeckTicks, this.peckTicks) / 8.0F;

        if (f < 0.5F) {
            return 2.0F * f;
        } else {
            return -2.0F * f + 2.0F;
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.hasAngerTime() ? AutumnitySounds.ENTITY_TURKEY_AGGRO : AutumnitySounds.ENTITY_TURKEY_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AutumnitySounds.ENTITY_TURKEY_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AutumnitySounds.ENTITY_TURKEY_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return Ingredient.fromTag(AutumnityTags.Items.TURKEY_FOOD).test(stack);
    }

    @Override
    protected int getXpToDrop(PlayerEntity player) {
        return this.isTurkeyJockey() ? 10 : super.getXpToDrop(player);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.turkeyJockey = nbt.getBoolean("IsTurkeyJockey");
        if (nbt.contains("EggLayTime")) {
            this.timeUntilNextEgg = nbt.getInt("EggLayTime");
        }
        this.readAngerFromNbt(this.world, nbt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsTurkeyJockey", this.turkeyJockey);
        nbt.putInt("EggLayTime", this.timeUntilNextEgg);
        this.writeAngerToNbt(nbt);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return this.isTurkeyJockey();
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        float f = MathHelper.sin(this.bodyYaw * ((float) Math.PI / 180F));
        float f1 = MathHelper.cos(this.bodyYaw * ((float) Math.PI / 180F));
        passenger.setPos(this.getX() + (double) (0.1F * f), this.getBodyY(0.5D) +
                passenger.getHeightOffset() + 0.0D, this.getZ() - (double) (0.1F * f1));
        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).bodyYaw = this.bodyYaw;
        }
    }

    public boolean isTurkeyJockey() {
        return this.turkeyJockey;
    }

    public void setTurkeyJockey(boolean jockey) {
        this.turkeyJockey = jockey;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int time) {
        this.dataTracker.set(ANGER_TIME, time);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.lastHurtBy;
    }

    @Override
    public void setAngryAt(@Nullable UUID target) {
        this.lastHurtBy = target;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_RANGE.get(this.random));
    }

    public int getRandomNextEggTime(Random random) {
        return random.nextInt(9600) + 9600;
    }


    static class EscapeDangerGoal extends net.minecraft.entity.ai.goal.EscapeDangerGoal {
        public EscapeDangerGoal(TurkeyEntity turkey) {
            super(turkey, 1.4D);
        }

        @Override
        public boolean canStart() {
            return this.mob.isBaby() && super.canStart();
        }
    }

    static class RevengeGoal extends net.minecraft.entity.ai.goal.RevengeGoal {
        public RevengeGoal(TurkeyEntity entity) {
            super(entity);
        }

        @Override
        protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
            if (mob instanceof TurkeyEntity && !mob.isBaby()) {
                super.setMobEntityTarget(mob, target);
            }
        }

        @Override
        public void start() {
            super.start();

            if (this.mob.isBaby()) {
                this.callSameTypeForRevenge();
                this.stop();
            }
        }
    }

    static class JockeyTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        public JockeyTargetGoal(TurkeyEntity turkey, Class<T> classTarget) {
            super(turkey, classTarget, true);
        }

        @Override
        public boolean canStart() {
            TurkeyEntity turkey = (TurkeyEntity) this.mob;

            return turkey.isTurkeyJockey() && super.canStart();
        }
    }
}
