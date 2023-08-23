package net.george.autumnity.entity.special;

import com.mojang.logging.LogUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings({"unused"})
public class AutumnityFallingBlockEntity extends FallingBlockEntity {
    private static final Logger logger = LogUtils.getLogger();
    protected BlockState block;
    public int timeFalling;
    public boolean dropItem;
    private boolean destroyedOnLanding;
    private boolean hurtEntities;
    private int fallHurtMax;
    private float fallHurtAmount;
    @Nullable
    public NbtCompound blockEntityData;
    protected static final TrackedData<BlockPos> BLOCK_POS;

    public AutumnityFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
        this.block = Blocks.SAND.getDefaultState();
        this.dropItem = true;
        this.fallHurtMax = 40;
    }

    private AutumnityFallingBlockEntity(World world, double x, double y, double z, BlockState block) {
        this(EntityType.FALLING_BLOCK, world);
        this.block = block;
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setFallingBlockPos(this.getBlockPos());
    }

    public static AutumnityFallingBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state) {
        AutumnityFallingBlockEntity fallingBlockEntity = new AutumnityFallingBlockEntity(world, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state);
        world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        world.spawnEntity(fallingBlockEntity);
        return fallingBlockEntity;
    }

    public boolean isAttackable() {
        return false;
    }

    public void setFallingBlockPos(BlockPos pos) {
        this.dataTracker.set(BLOCK_POS, pos);
    }

    public BlockPos getFallingBlockPos() {
        return this.dataTracker.get(BLOCK_POS);
    }

    protected Entity.MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN);
    }

    public boolean collides() {
        return !this.isRemoved();
    }

    public void tick() {
        if (this.block.isAir()) {
            this.discard();
        } else {
            Block block = this.block.getBlock();
            ++this.timeFalling;
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
            }

            this.move(MovementType.SELF, this.getVelocity());
            if (!this.world.isClient) {
                BlockPos blockPos = this.getBlockPos();
                boolean bl = this.block.getBlock() instanceof ConcretePowderBlock;
                boolean bl2 = bl && this.world.getFluidState(blockPos).isIn(FluidTags.WATER);
                double d = this.getVelocity().lengthSquared();
                if (bl && d > 1.0) {
                    BlockHitResult blockHitResult = this.world.raycast(new RaycastContext(new Vec3d(this.prevX, this.prevY, this.prevZ), this.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS && this.world.getFluidState(blockHitResult.getBlockPos()).isIn(FluidTags.WATER)) {
                        blockPos = blockHitResult.getBlockPos();
                        bl2 = true;
                    }
                }

                if (!this.onGround && !bl2) {
                    if (!this.world.isClient && (this.timeFalling > 100 && (blockPos.getY() <= this.world.getBottomY() || blockPos.getY() > this.world.getTopY()) || this.timeFalling > 600)) {
                        if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }

                        this.discard();
                    }
                } else {
                    BlockState blockState = this.world.getBlockState(blockPos);
                    this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
                    if (!blockState.isOf(Blocks.MOVING_PISTON)) {
                        if (!this.destroyedOnLanding) {
                            boolean bl3 = blockState.canReplace(new AutomaticItemPlacementContext(this.world, blockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean bl4 = FallingBlock.canFallThrough(this.world.getBlockState(blockPos.down())) && (!bl || !bl2);
                            boolean bl5 = this.block.canPlaceAt(this.world, blockPos) && !bl4;
                            if (bl3 && bl5) {
                                if (this.block.contains(Properties.WATERLOGGED) && this.world.getFluidState(blockPos).getFluid() == Fluids.WATER) {
                                    this.block = this.block.with(Properties.WATERLOGGED, true);
                                }

                                if (this.world.setBlockState(blockPos, this.block, 3)) {
                                    ((ServerWorld)this.world).getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(this, new BlockUpdateS2CPacket(blockPos, this.world.getBlockState(blockPos)));
                                    this.discard();
                                    if (block instanceof LandingBlock) {
                                        ((LandingBlock)block).onLanding(this.world, blockPos, this.block, blockState, this);
                                    }

                                    if (this.blockEntityData != null && this.block.hasBlockEntity()) {
                                        BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
                                        if (blockEntity != null) {
                                            NbtCompound nbtCompound = blockEntity.createNbt();

                                            for (String string : this.blockEntityData.getKeys()) {
                                                nbtCompound.put(string, Objects.requireNonNull(this.blockEntityData.get(string)).copy());
                                            }

                                            try {
                                                blockEntity.readNbt(nbtCompound);
                                            } catch (Exception var15) {
                                                logger.error("Failed to load block entity from falling block", var15);
                                            }

                                            blockEntity.markDirty();
                                        }
                                    }
                                } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    this.discard();
                                    this.onDestroyedOnLanding(block, blockPos);
                                    this.dropItem(block);
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    this.onDestroyedOnLanding(block, blockPos);
                                    this.dropItem(block);
                                }
                            }
                        } else {
                            this.discard();
                            this.onDestroyedOnLanding(block, blockPos);
                        }
                    }
                }
            }

            this.setVelocity(this.getVelocity().multiply(0.98));
        }
    }

    public void onDestroyedOnLanding(Block block, BlockPos pos) {
        if (block instanceof LandingBlock) {
            ((LandingBlock)block).onDestroyedOnLanding(this.world, pos, this);
        }

    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.hurtEntities) {
            int i = MathHelper.ceil(fallDistance - 1.0F);
            if (i >= 0) {
                Predicate<? super Entity> predicate;
                DamageSource damageSource2;
                if (this.block.getBlock() instanceof LandingBlock landingBlock) {
                    predicate = landingBlock.getEntityPredicate();
                    damageSource2 = landingBlock.getDamageSource();
                } else {
                    predicate = EntityPredicates.EXCEPT_SPECTATOR;
                    damageSource2 = DamageSource.FALLING_BLOCK;
                }

                float f = (float) Math.min(MathHelper.floor((float) i * this.fallHurtAmount), this.fallHurtMax);
                this.world.getOtherEntities(this, this.getBoundingBox(), predicate)
                        .forEach((entity) -> entity.damage(damageSource2, f));
                boolean bl = this.block.isIn(BlockTags.ANVIL);
                if (bl && f > 0.0F && this.random.nextFloat() < 0.05F + (float) i * 0.05F) {
                    BlockState blockState = AnvilBlock.getLandingState(this.block);
                    if (blockState == null) {
                        this.destroyedOnLanding = true;
                    } else {
                        this.block = blockState;
                    }
                }

            }
        }
        return false;
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("BlockState", NbtHelper.fromBlockState(this.block));
        nbt.putInt("Time", this.timeFalling);
        nbt.putBoolean("DropItem", this.dropItem);
        nbt.putBoolean("HurtEntities", this.hurtEntities);
        nbt.putFloat("FallHurtAmount", this.fallHurtAmount);
        nbt.putInt("FallHurtMax", this.fallHurtMax);
        if (this.blockEntityData != null) {
            nbt.put("TileEntityData", this.blockEntityData);
        }

    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.block = NbtHelper.toBlockState(nbt.getCompound("BlockState"));
        this.timeFalling = nbt.getInt("Time");
        if (nbt.contains("HurtEntities", 99)) {
            this.hurtEntities = nbt.getBoolean("HurtEntities");
            this.fallHurtAmount = nbt.getFloat("FallHurtAmount");
            this.fallHurtMax = nbt.getInt("FallHurtMax");
        } else if (this.block.isIn(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (nbt.contains("DropItem", 99)) {
            this.dropItem = nbt.getBoolean("DropItem");
        }

        if (nbt.contains("TileEntityData", 10)) {
            this.blockEntityData = nbt.getCompound("TileEntityData");
        }

        if (this.block.isAir()) {
            this.block = Blocks.SAND.getDefaultState();
        }

    }

    public void setHurtEntities(float fallHurtAmount, int fallHurtMax) {
        this.hurtEntities = true;
        this.fallHurtAmount = fallHurtAmount;
        this.fallHurtMax = fallHurtMax;
    }

    public boolean doesRenderOnFire() {
        return false;
    }

    public void populateCrashReport(CrashReportSection section) {
        super.populateCrashReport(section);
        section.add("Immitating BlockState", this.block.toString());
    }

    public BlockState getBlockState() {
        return this.block;
    }

    public boolean entityDataRequiresOperator() {
        return true;
    }

    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, Block.getRawIdFromState(this.getBlockState()));
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.block = Block.getStateFromRawId(packet.getEntityData());
        this.intersectionChecked = true;
        double d = packet.getX();
        double e = packet.getY();
        double f = packet.getZ();
        this.setPosition(d, e, f);
        this.setFallingBlockPos(this.getBlockPos());
    }

    static {
        BLOCK_POS = DataTracker.registerData(AutumnityFallingBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }
}
