package net.george.autumnity.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mr.George
 * @since v1.0.0.build.3
 */
public class FallingMapleLeafParticle extends SpriteBillboardParticle {
    private final float rotSpeed;
    private final double dx;
    private double dy;
    private final double dz;

    public FallingMapleLeafParticle(ClientWorld clientWorld, double xCoord, double yCoord, double zCoord,
                                      double dx , double dy, double dz) {
        super(clientWorld, xCoord, yCoord, zCoord);
        this.scale *= 1.2F;
        this.maxAge = 80;
        this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
        this.angle = (float) Math.random() * ((float) Math.PI * 2F);

        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age >= this.maxAge) {
            this.markDead();
        } else {
            this.move(this.dx, this.dy, this.dz);
            this.dy -= 0.002F;
            this.dy = Math.max(this.dy, -0.1F);

            this.prevAngle = this.angle;
            if (!this.onGround) {
                this.angle += (float) Math.PI * this.rotSpeed * 1.6F;
            } else {
                this.dy = 0.0D;
            }

            if (this.onGround || this.y < 0) {
                this.age++;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteSet;

        public Factory(SpriteProvider spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double dx, double dy, double dz) {
            FallingMapleLeafParticle particle = new FallingMapleLeafParticle(world, x, y, z, dx, dy, dz);
            particle.setColor((float) dx, (float) dy, (float) dz);
            particle.setSprite(this.spriteSet);
            return particle;
        }
    }
}
