package net.george.autumnity.entity.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.Autumnity;
import net.george.autumnity.AutumnityClient;
import net.george.autumnity.entity.client.model.SnailEntityModel;
import net.george.autumnity.entity.passive.SnailEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

/**
 * @since v1.0.0.build.7
 * @author Mr.George
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class SnailEntityRenderer extends MobEntityRenderer<SnailEntity, SnailEntityModel<SnailEntity>> {
    private static final Identifier SNAIL_TEXTURES = new Identifier(Autumnity.MOD_ID, "textures/entity/snail/snail.png");
    private static final Identifier SNAKE_SNAIL_TEXTURES = new Identifier(Autumnity.MOD_ID, "textures/entity/snail/snake_snail.png");
    private static final Identifier NAUTILUS_SNAIL_TEXTURES = new Identifier(Autumnity.MOD_ID, "textures/entity/snail/nautilus_snail.png");
    
    public SnailEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailEntityModel<>(context.getPart(AutumnityClient.SNAIL)), 0.5F);
    }

    @Override
    public Identifier getTexture(SnailEntity entity) {
        String s = Formatting.strip(entity.getName().getString().toLowerCase());
        if (s != null) {
            if ("snake".equals(s) || "snakeblock".equals(s) || "snake block".equals(s) || "snailblock".equals(s) || "snail block".equals(s)) {
                return SNAKE_SNAIL_TEXTURES;
            } else if ("nautilus".equals(s) || "nautilus snail".equals(s)) {
                return NAUTILUS_SNAIL_TEXTURES;
            }
        }

        return SNAIL_TEXTURES;
    }

    @Override
    protected void setupTransforms(SnailEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        double d0 = entity.getShakeAmount(tickDelta);
        double d1 = d0 > 0 ? 2.0D : -2.0D;
        double d2 = Math.sin(12.6D * d0) * d1 * d0;
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(6.0F * (float) d2));
    }
}
