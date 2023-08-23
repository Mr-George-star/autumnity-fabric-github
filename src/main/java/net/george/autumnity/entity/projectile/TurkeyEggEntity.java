package net.george.autumnity.entity.projectile;

import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.entity.passive.TurkeyEntity;
import net.george.autumnity.item.AutumnityItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

/**
 * @since 1.0.0.build.6
 * @version 1.0.0.build.7
 * @author Mr.George
 */
@SuppressWarnings("unused")
public class TurkeyEggEntity extends ThrownItemEntity {
    public TurkeyEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public TurkeyEggEntity(World world, LivingEntity owner) {
        super(AutumnityEntities.TURKEY_EGG, owner, world);
    }

    public TurkeyEggEntity(World world, double x, double y, double z) {
        super(AutumnityEntities.TURKEY_EGG, x, y, z, world);
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            double d = 0.08;

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()),
                        this.getX(), this.getY(), this.getZ(),
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }

    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        result.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0F);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for(int j = 0; j < i; ++j) {
                    TurkeyEntity turkey = AutumnityEntities.TURKEY.create(this.world);
                    turkey.setBreedingAge(-24000);
                    turkey.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                    this.world.spawnEntity(turkey);
                }
            }

            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return AutumnityItems.TURKEY_EGG;
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(AutumnityItems.TURKEY_EGG);
    }
}
