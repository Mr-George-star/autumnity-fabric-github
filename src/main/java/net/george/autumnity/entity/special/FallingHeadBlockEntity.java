package net.george.autumnity.entity.special;

import net.george.autumnity.entity.AutumnityEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FallingHeadBlockEntity extends AutumnityFallingBlockEntity {
    public boolean canGoOnHead;
    public FallingHeadBlockEntity(EntityType<? extends AutumnityFallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public FallingHeadBlockEntity(World worldIn, double x, double y, double z, BlockState block, boolean drop) {
        this(AutumnityEntities.FALLING_HEAD_BLOCK, worldIn);
        this.intersectionChecked = true;
        this.block = block;
        this.setPos(x, y + (double) ((1.0F - this.getHeight()) / 2.0F), z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.canGoOnHead = drop;
        this.setFallingBlockPos(this.getBlockPos());
    }

    public static FallingHeadBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state, boolean drop) {
        FallingHeadBlockEntity turkey = new FallingHeadBlockEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state, drop);
        world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        world.spawnEntity(turkey);
        return turkey;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isRemoved() && this.canGoOnHead) {
            for (Entity entity : this.world.getOtherEntities(this, this.getBoundingBox(), (entity) ->
                    (entity instanceof PlayerEntity || entity instanceof ZombieEntity || entity instanceof AbstractSkeletonEntity || entity instanceof PiglinEntity) && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.HEAD).isEmpty())) {
                double d0 = entity.getY() + entity.getHeight();
                if (this.prevY >= d0 && this.getY() <= d0) {
                    entity.equipStack(EquipmentSlot.HEAD, new ItemStack((this.getBlockState().getBlock().asItem())));
                    this.discard();
                    break;
                }
            }
        }
    }
}
