package net.george.autumnity.entity.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.advancement.AutumnityCriterion;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.item.AutumnityItems;
import net.george.autumnity.sound.AutumnitySounds;
import net.george.autumnity.util.AutumnityTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

/**
 * @since v1.0.0.build.7
 * @author Mr.George
 */
public class SnailEntity extends AnimalEntity {
    private static final UUID HIDING_ARMOR_BONUS_ID = UUID.fromString("73BF0604-4235-4D4C-8A74-6A633E526E24");
    private static final EntityAttributeModifier HIDING_ARMOR_BONUS_MODIFIER = new EntityAttributeModifier(HIDING_ARMOR_BONUS_ID, "Hiding armor bonus",
            20.0D, EntityAttributeModifier.Operation.ADDITION);
    private static final TrackedData<Integer> GOO_AMOUNT = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Byte> ACTION = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.BYTE);
    private int hidingTime = 0;

    private float hideAmount;
    private float hideAmountO;

    private int shakeAmount;
    private int shakeAmountO;

    private boolean canBreed = true;

    private static final Predicate<LivingEntity> ENEMY_MATCHER = (livingentity) -> {
        if (livingentity == null) {
            return false;
        } else {
            livingentity.getEquippedStack(EquipmentSlot.CHEST);
            if (livingentity.getEquippedStack(EquipmentSlot.CHEST).getItem() == AutumnityItems.SNAIL_SHELL_CHESTPLATE) {
                return false;
            } else if (livingentity instanceof PlayerEntity) {
                return !livingentity.isSneaking() && !livingentity.isSpectator() && !((PlayerEntity) livingentity).isCreative();
            } else {
                return !(livingentity instanceof SnailEntity) && !(livingentity instanceof MooshroomEntity);
            }
        }
    };

    public SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.getPathfindingPenalty(PathNodeType.DANGER_CACTUS);
        this.getPathfindingPenalty(PathNodeType.DAMAGE_CACTUS);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SnailEntity.HideGoal());
        this.goalSelector.add(1, new SnailEntity.EatGoal());
        this.goalSelector.add(2, new AnimalMateGoal(this, 0.5D));
        this.goalSelector.add(3, new TemptGoal(this, 0.5D, Ingredient.fromTag(AutumnityTags.Items.SNAIL_TEMPT_SNACKS), false));
        this.goalSelector.add(4, new SnailEntity.EatMushroomsGoal());
        this.goalSelector.add(5, new SnailEntity.EatMooshroomMushroomsGoal());
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.5D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(GOO_AMOUNT, 0);
        this.dataTracker.startTracking(ACTION, (byte) 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("GooAmount", this.getGooAmount());
        nbt.putInt("HidingTime", this.getHidingTime());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setGooAmount(nbt.getInt("GooAmount"));
        this.setHidingTime(nbt.getInt("HidingTime"));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AutumnitySounds.ENTITY_SNAIL_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AutumnitySounds.ENTITY_SNAIL_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AutumnitySounds.ENTITY_SNAIL_STEP, 0.4F, 1.0F);
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.isClient) {
            this.hideAmountO = this.hideAmount;
            if (this.getAction() == Action.HIDING) {
                this.hideAmount = MathHelper.clamp(this.hideAmount + 1, 0, 3);
            } else {
                this.hideAmount = MathHelper.clamp(this.hideAmount - 0.5F, 0, 3);
            }

            this.shakeAmountO = this.shakeAmount;
            if (this.shakeAmount > 0) {
                this.shakeAmount = MathHelper.clamp(this.shakeAmount - 1, 0, 20);
            } else {
                this.shakeAmount = MathHelper.clamp(this.shakeAmount + 1, -20, 0);
            }
        }
    }

    @Override
    public void tickMovement() {
        if (this.getAction() != Action.DEFAULT || this.isImmobile()) {
            this.jumping = false;
            this.sidewaysSpeed = 0.0F;
            this.forwardSpeed = 0.0F;
        }

        super.tickMovement();

        if (this.getAction() == Action.EATING) {
            this.eat();
        }

        if (!this.world.isClient && this.isAlive()) {
            if (!this.getMainHandStack().isEmpty() && !this.hasSnack()) {
                this.spitOutItem();
            }

            if (this.getGooAmount() > 0 && (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING))) {
                BlockState blockstate = AutumnityBlocks.SNAIL_GOO.getDefaultState();
                BlockPos blockpos = this.getBlockPos();
                if (this.getGooAmount() > 0 && this.world.isAir(blockpos) && blockstate.canPlaceAt(this.world, blockpos)) {
                    this.world.setBlockState(blockpos, blockstate);
                    this.setGooAmount(this.getGooAmount() - 1);
                }
            }
        }
    }

    public void eat() {
        if (this.age % 12 == 0 && !this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            this.playSound(AutumnitySounds.ENTITY_SNAIL_EAT,
                    0.25F + 0.5F * (float) this.random.nextInt(2),
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            if (this.world.isClient) {
                for (int i = 0; i < 6; ++i) {
                    Vec3d vector3d = new Vec3d(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) this.random.nextFloat() - 0.5D) * 0.1D);
                    vector3d = vector3d.rotateX(-this.getPitch() * ((float) Math.PI / 180F));
                    vector3d = vector3d.rotateY(-this.getYaw() * ((float) Math.PI / 180F));
                    double d0 = (double) (-this.random.nextFloat()) * 0.2D;
                    Vec3d vector3d1 = new Vec3d(((double) this.random.nextFloat() - 0.5D) * 0.2D, d0, 0.8D + ((double) this.random.nextFloat() - 0.5D) * 0.2D);
                    vector3d1 = vector3d1.rotateY(-this.bodyYaw * ((float) Math.PI / 180F));
                    vector3d1 = vector3d1.add(this.getX(), this.getY() + (double) this.getStandingEyeHeight(), this.getZ());
                    this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getEquippedStack(EquipmentSlot.MAINHAND)), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
                }
            }
        }
    }

    @Override
    protected void onGrowUp() {
        super.onGrowUp();
        if (!this.isBaby() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropItem(AutumnityItems.SNAIL_SHELL_PIECE, 1);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.getAction() == Action.DEFAULT) {
            ItemStack itemstack = player.getStackInHand(hand);
            if (!itemstack.isEmpty() && !this.hasSnack()) {
                if (this.isSnack(itemstack)) {
                    if (!this.isBaby() && this.getGooAmount() <= 0) {
                        if (!this.world.isClient) {
                            ItemStack itemstack1 = itemstack.copy();
                            itemstack1.setCount(1);
                            this.equipStack(EquipmentSlot.MAINHAND, itemstack1);
                            AutumnityCriterion.FEED_SNAIL.trigger((ServerPlayerEntity) player, itemstack1);
                            this.eat(player, hand, itemstack);
                        }
                        return ActionResult.success(this.world.isClient);
                    }
                } else if (this.isSnailBreedingItem(itemstack)) {
                    boolean flag = false;

                    if (!this.world.isClient && this.getBreedingAge() == 0 && this.canEat()) {
                        this.lovePlayer(player);
                        flag = true;
                    } else if (this.isBaby()) {
                        this.growUp((int) ((float) (-this.getBreedingAge() / 20) * 0.1F), true);
                        flag = true;
                    }

                    if (flag) {
                        if (!this.world.isClient && !player.getAbilities().creativeMode) {
                            ItemStack container = itemstack.copy();
                            if (container.isEmpty() && itemstack.getItem() instanceof StewItem)
                                container = new ItemStack(Items.BOWL);

                            itemstack.decrement(1);

                            if (!container.isEmpty()) {
                                if (itemstack.isEmpty()) {
                                    player.setStackInHand(hand, container);
                                } else {
                                    if (!player.getInventory().insertStack(container)) {
                                        player.dropItem(container, false);
                                    }
                                }
                            }
                        }

                        return ActionResult.success(this.world.isClient);
                    }
                }
            }
        }

        this.canBreed = false;
        ActionResult result = super.interactMob(player, hand);
        this.canBreed = true;
        return result;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getSource();
            if (this.getAction() == Action.HIDING && entity instanceof ArrowEntity) {
                return false;
            } else if (source == DamageSource.CACTUS) {
                return false;
            }

            this.spitOutItem();

            if (this.world.isClient) {
                this.shakeAmount = this.random.nextInt(2) == 0 ? -10 : 10;
            }

            return super.damage(source, amount);
        }
    }

    private void spitOutItem() {
        ItemStack itemstack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemstack.isEmpty() && !this.world.isClient) {
            ItemEntity itementity = new ItemEntity(this.world, this.getX() + this.getRotationVector().x,
                    this.getY() + this.getStandingEyeHeight(), this.getZ() + this.getRotationVector().z, itemstack);
            itementity.setPickupDelay(40);
            itementity.setThrower(this.getUuid());
            this.world.spawnEntity(itementity);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    private void eatSnack() {
        ItemStack itemstack = this.getMainHandStack();

        if (Ingredient.fromTag(AutumnityTags.Items.SNAIL_GLOW_SNACKS).test(itemstack)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0));
        }
        if (Ingredient.fromTag(AutumnityTags.Items.SNAIL_SPEED_SNACKS).test(itemstack)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 320, 2));
        }

        Item item = itemstack.getItem();
        ItemStack itemstack1 = itemstack.finishUsing(this.world, this);
        if (!itemstack1.isEmpty()) {
            if (itemstack1.getItem() != item) {
                this.equipStack(EquipmentSlot.MAINHAND, itemstack1);
                this.spitOutItem();
            } else {
                this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
        }

        this.setGooAmount(4);
    }

    private boolean hasSnack() {
        return this.isSnack(this.getMainHandStack());
    }

    private int getGooAmount() {
        return this.dataTracker.get(GOO_AMOUNT);
    }

    private void setGooAmount(int amount) {
        this.dataTracker.set(GOO_AMOUNT, amount);
    }

    private int getHidingTime() {
        return this.hidingTime;
    }

    private void setHidingTime(int hidingTimeIn) {
        this.hidingTime = hidingTimeIn;
    }

    public Action getAction() {
        return Action.byId(this.dataTracker.get(ACTION));
    }

    private void setAction(Action action) {
        this.dataTracker.set(ACTION, (byte) action.getId());

        if (!this.world.isClient) {
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HIDING_ARMOR_BONUS_MODIFIER);
            if (action == Action.HIDING) {
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(HIDING_ARMOR_BONUS_MODIFIER);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public float getHideAmount(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.hideAmountO, this.hideAmount) / 3.0F;
    }

    @Environment(EnvType.CLIENT)
    public float getShakeAmount(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.shakeAmountO, this.shakeAmount) / 20.0F;
    }

    private boolean isSnack(ItemStack stack) {
        return Ingredient.fromTag(AutumnityTags.Items.SNAIL_SNACKS).test(stack);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return this.canBreed && this.isSnailBreedingItem(stack);
    }

    private boolean isSnailBreedingItem(ItemStack stack) {
        return Ingredient.fromTag(AutumnityTags.Items.SNAIL_FOOD).test(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return AutumnityEntities.SNAIL.create(world);
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        return this.distanceTraveled + 0.6F;
    }

    /**------------NEW CLASS------------**/

    public enum Action {
        DEFAULT(0),
        EATING(1),
        HIDING(2);

        private static final Action[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(Action::getId)).toArray(Action[]::new);
        private final int id;

        Action(int idIn) {
            this.id = idIn;
        }

        public int getId() {
            return this.id;
        }

        public static Action byId(int indexIn) {
            if (indexIn < 0 || indexIn >= VALUES.length) {
                indexIn = 0;
            }

            return VALUES[indexIn];
        }
    }

    /**------------NEW CLASS------------**/

    public class HideGoal extends Goal {
        public HideGoal() {
            super();
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.JUMP));
        }

        @Override
        public boolean canStart() {
            return SnailEntity.this.getHidingTime() > 0 || SnailEntity.this.getAttacker() != null || this.shouldHideFromMob();
        }

        @Override
        public void start() {
            this.hide();
            SnailEntity.this.getNavigation().stop();
            SnailEntity.this.getMoveControl().moveTo(SnailEntity.this.getX(), SnailEntity.this.getY(), SnailEntity.this.getZ(), 0.0D);
            SnailEntity.this.setAction(Action.HIDING);
        }

        @Override
        public void stop() {
            SnailEntity.this.setAction(Action.DEFAULT);
        }

        @Override
        public void tick() {
            if ((SnailEntity.this.getAttacker() != null || this.shouldHideFromMob()) && SnailEntity.this.getHidingTime() < 120) {
                this.hide();
            } else {
                SnailEntity.this.setHidingTime(SnailEntity.this.getHidingTime() - 1);
            }
        }

        @Override
        public boolean shouldContinue() {
            return SnailEntity.this.getHidingTime() > 0;
        }

        private void hide() {
            SnailEntity.this.setHidingTime(120 + SnailEntity.this.random.nextInt(120));
        }

        private boolean shouldHideFromMob() {
            for (LivingEntity livingentity : SnailEntity.this.world.getEntitiesByClass(LivingEntity.class, SnailEntity.this.getBoundingBox().expand(0.5D), ENEMY_MATCHER)) {
                if (livingentity.isAlive() && livingentity.getHeight() > SnailEntity.this.getHeight()) {
                    return true;
                }
            }

            return false;
        }
    }

    /**------------NEW CLASS------------**/

    class EatGoal extends Goal {
        private int eatTime;

        public EatGoal() {
            super();
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
        }

        @Override
        public boolean canStart() {
            return SnailEntity.this.hasSnack();
        }

        @Override
        public void start() {
            this.eatTime = this.getTickCount(192);
            SnailEntity.this.getNavigation().stop();
            SnailEntity.this.getMoveControl().moveTo(SnailEntity.this.getX(), SnailEntity.this.getY(), SnailEntity.this.getZ(), 0.0D);
            SnailEntity.this.setAction(Action.EATING);
        }

        @Override
        public void stop() {
            SnailEntity.this.setAction(Action.DEFAULT);
        }

        @Override
        public void tick() {
            --this.eatTime;

            if (this.eatTime <= 0) {
                SnailEntity.this.eatSnack();
            }
        }

        @Override
        public boolean shouldContinue() {
            return SnailEntity.this.hasSnack();
        }
    }

    /**------------NEW CLASS------------**/

    class EatMushroomsGoal extends Goal {
        private double mushroomX;
        private double mushroomY;
        private double mushroomZ;

        public EatMushroomsGoal() {
            super();
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (SnailEntity.this.getRandom().nextInt(20) != 0) {
                return false;
            } else {
                return !SnailEntity.this.isBaby() && !SnailEntity.this.hasSnack() && SnailEntity.this.getGooAmount() <= 0 && (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && this.canMoveToMushroom());
            }
        }

        @Override
        public boolean shouldContinue() {
            return !SnailEntity.this.getNavigation().isIdle() && !SnailEntity.this.hasSnack() && SnailEntity.this.getGooAmount() <= 0;
        }

        @Override
        public void start() {
            SnailEntity.this.getNavigation().startMovingTo(this.mushroomX, this.mushroomY, this.mushroomZ, 0.5D);
        }

        @Override
        public void tick() {
            if (!SnailEntity.this.isBaby() && SnailEntity.this.getGooAmount() <= 0) {
                BlockPos blockpos = SnailEntity.this.getBlockPos();

                if (this.isBlockMushroom(blockpos)) {
                    if (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                        SnailEntity.this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(SnailEntity.this.world.getBlockState(blockpos).getBlock().asItem(), 1));
                        SnailEntity.this.world.breakBlock(blockpos, false);
                    }
                }
            }
        }

        @javax.annotation.Nullable
        private Vec3d findMushroom() {
            Random random = SnailEntity.this.getRandom();
            BlockPos blockpos = new BlockPos(SnailEntity.this.getX(), SnailEntity.this.getBoundingBox().minY, SnailEntity.this.getZ());

            for (int i = 0; i < 10; ++i) {
                BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
                if (this.isBlockMushroom(blockpos1)) {
                    return new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
                }
            }

            return null;
        }

        private boolean canMoveToMushroom() {
            Vec3d vec3d = this.findMushroom();
            if (vec3d == null) {
                return false;
            } else {
                this.mushroomX = vec3d.x;
                this.mushroomY = vec3d.y;
                this.mushroomZ = vec3d.z;
                return true;
            }
        }

        private boolean isBlockMushroom(BlockPos pos) {
            return SnailEntity.this.world.getBlockState(pos).isIn(AutumnityTags.Blocks.SNAIL_SNACKS);
        }
    }

    /**------------NEW CLASS------------**/

    public class EatMooshroomMushroomsGoal extends Goal {
        private MooshroomEntity targetMooshroom;
        private int timeToRecalcPath;

        public EatMooshroomMushroomsGoal() {
            super();
        }

        @Override
        public boolean canStart() {
            if (!SnailEntity.this.isBaby() && !SnailEntity.this.hasSnack() && SnailEntity.this.getGooAmount() <= 0) {
                List<MooshroomEntity> list = SnailEntity.this.world.getNonSpectatingEntities(MooshroomEntity.class, SnailEntity.this.getBoundingBox().expand(8.0D, 4.0D, 8.0D));
                MooshroomEntity mooshroom = null;
                double d0 = Double.MAX_VALUE;

                for (MooshroomEntity mooshroom1 : list) {
                    if (mooshroom1.getBreedingAge() >= 0) {
                        double d1 = SnailEntity.this.squaredDistanceTo(mooshroom1);
                        if (!(d1 > d0)) {
                            d0 = d1;
                            mooshroom = mooshroom1;
                        }
                    }
                }

                if (mooshroom == null) {
                    return false;
                } else {
                    this.targetMooshroom = mooshroom;
                    return true;
                }
            } else {
                return false;
            }
        }

        @Override
        public boolean shouldContinue() {
            if (!this.targetMooshroom.isAlive()) {
                return false;
            } else if (SnailEntity.this.hasSnack()) {
                return false;
            } else if (SnailEntity.this.getGooAmount() > 0) {
                return false;
            } else {
                double d0 = this.targetMooshroom.squaredDistanceTo(SnailEntity.this);
                return !(d0 > 256.0D);
            }
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
        }

        @Override
        public void stop() {
            this.targetMooshroom = null;
        }

        @Override
        public void tick() {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.getTickCount(10);
                SnailEntity.this.getNavigation().startMovingTo(this.targetMooshroom, 0.5D);
            }

            if (this.targetMooshroom != null && this.targetMooshroom.isAlive()) {
                double d0 = this.targetMooshroom.squaredDistanceTo(SnailEntity.this);
                if (d0 < 2.0D) {
                    if (this.targetMooshroom.getMooshroomType() == MooshroomEntity.Type.BROWN) {
                        SnailEntity.this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BROWN_MUSHROOM, 1));
                    } else {
                        SnailEntity.this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.RED_MUSHROOM, 1));
                    }

                    this.targetMooshroom.damage(DamageSource.mob(SnailEntity.this), 0.0F);
                }
            }
        }
    }
}
