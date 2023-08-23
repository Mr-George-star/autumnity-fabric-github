package net.george.autumnity.entity.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.entity.passive.SnailEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

/**
 * @since v1.0.0.build.7
 * @author Mr.George
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class SnailEntityModel<T extends SnailEntity> extends EntityModel<T> {
    private final ModelPart body;
    private final ModelPart shell;
    private final ModelPart rightEye;
    private final ModelPart leftEye;
    private final ModelPart rightTentacle;
    private final ModelPart leftTentacle;
    private float hideAmount;

    public SnailEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.shell = root.getChild("shell");
        this.rightEye = root.getChild("right_eye");
        this.leftEye = root.getChild("left_eye");
        this.rightTentacle = root.getChild("right_tentacle");
        this.leftTentacle = root.getChild("left_tentacle");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 18.0F, 6.0F, false), ModelTransform.of(0.0F, 24.0F, -9.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
        root.addChild("shell", ModelPartBuilder.create().uv(0, 24).cuboid(-4.5F, 0.0F, 0.0F, 9.0F, 14.0F, 14.0F, false), ModelTransform.of(0.0F, 7.0F, -1.0F, -0.22F, 0.0F, 0.0F));
        root.addChild("right_eye", ModelPartBuilder.create().uv(28, 0).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 7.0F, 2.0F, false), ModelTransform.of(2.5F, 18.0F, -7.0F, 0.0F, 0.0F, 0.0F));
        root.addChild("left_eye", ModelPartBuilder.create().uv(28, 0).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 7.0F, 2.0F, true), ModelTransform.of(-2.5F, 18.0F, -7.0F, 0.0F, 0.0F, 0.0F));
        root.addChild("right_tentacle", ModelPartBuilder.create().uv(28, 9).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), ModelTransform.of(3.0F, 22.0F, -9.0F, 0.0F, 0.0F, 0.0F));
        root.addChild("left_tentacle", ModelPartBuilder.create().uv(28, 9).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), ModelTransform.of(-3.0F, 22.0F, -9.0F, 0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
        this.hideAmount = entity.getHideAmount(tickDelta);
        float f = 3.0F * this.hideAmount;
        float f1 = MathHelper.clamp(10.0F * this.hideAmount, 0.0F, 5.0F);
        boolean flag = this.hideAmount < 1.0F;

        this.rightEye.setPivot(2.5F, 18.0F + f1, -7.0F + f);
        this.leftEye.setPivot(-2.5F, 18.0F + f1, -7.0F + f);
        this.rightTentacle.setPivot(3.0F, 22.0F, -9.0F + f);
        this.leftTentacle.setPivot(-3.0F, 22.0F, -9.0F + f);
        this.shell.setPivot(0.0F, 7.0F, -1.0F - f);

        this.rightEye.visible = flag;
        this.leftEye.visible = flag;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = 1.0F - this.hideAmount;
        float f1 = headPitch * ((float) Math.PI / 180F) * 0.5F + 0.3F;
        float f2 = headYaw * ((float) Math.PI / 180F) * 0.5F;
        float f3 = entity.getId() % 2 == 0 ? 1.0F : 0.95F;
        float f4 = entity.getId() % 2 == 0 ? 0.95F : 1.0F;

        this.rightEye.pitch = f * (f1 + MathHelper.cos(0.04F * f3 * animationProgress) * 0.15F);
        this.rightEye.yaw = f * f2;
        this.rightEye.roll = f * (0.25F + MathHelper.sin(0.025F * f3 * animationProgress) * 0.1F);
        this.leftEye.pitch = f * (f1 + MathHelper.sin(0.04F * f4 * animationProgress) * 0.15F);
        this.leftEye.yaw = f * f2;
        this.leftEye.roll = f * (-0.25F + MathHelper.cos(0.025F * f4 * animationProgress) * 0.1F);

        if (entity.getAction() == SnailEntity.Action.EATING) {
            this.rightTentacle.yaw = 0.25F * MathHelper.cos(0.6F * animationProgress);
            this.leftTentacle.yaw = -this.rightTentacle.yaw;
        } else {
            this.rightTentacle.yaw = 0.0F;
            this.leftTentacle.yaw = 0.0F;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.push();
            matrices.scale(0.7F, 0.7F, 0.7F);
            matrices.translate(1.2F / 16.0F, 13.0F / 16.0F, 2.5F / 16.0F);
            this.leftEye.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
            matrices.push();
            matrices.scale(0.7F, 0.7F, 0.7F);
            matrices.translate(-1.2F / 16.0F, 13.0F / 16.0F, 2.5F / 16.0F);
            this.rightEye.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
            matrices.push();
            matrices.scale(0.5F, 0.5F, 0.5F);
            matrices.translate(0.0D, 24.0F / 16.0F, 0.0D);
            ImmutableList.of(this.rightTentacle, this.leftTentacle, this.shell).forEach((modelPart) ->
                    modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.push();
            matrices.scale(1.0F, 1.0F, 1.0F - 1.0F / 3.0F * this.hideAmount);
            this.body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
            matrices.pop();
        } else {
            ImmutableList.of(this.rightEye, this.leftEye).forEach((modelPart) ->
                    modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            ImmutableList.of(this.rightTentacle, this.leftTentacle, this.shell).forEach((modelPart) ->
                    modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.push();
            matrices.scale(1.0F, 1.0F, 1.0F - 1.0F / 3.0F * this.hideAmount);
            this.body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
        }
    }
}
