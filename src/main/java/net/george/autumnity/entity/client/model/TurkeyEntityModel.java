package net.george.autumnity.entity.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.entity.passive.TurkeyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

/*
  @implNote xRot = pitch
 *           yRot = yaw
 *           zRot = roll
 */
/**
 * @since 1.0.0.build.6
 * @author Mr.George
 */
@Environment(EnvType.CLIENT)
public class TurkeyEntityModel extends AnimalModel<TurkeyEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public TurkeyEntityModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightWing = this.body.getChild("right_wing");
        this.leftWing = this.body.getChild("left_wing");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 3.0F, false), ModelTransform.of(0.0F, 14.0F, -3.0F, 0.0F, 0.0F, 0.0F));
        head.addChild("waddle", ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, -1.0F, -4.0F, 2.0F, 4.0F, 2.0F, false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        head.addChild("beak", ModelPartBuilder.create().uv(0, 9).cuboid(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 2.0F, false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 14).cuboid(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, false), ModelTransform.of(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        body.addChild("right_wing", ModelPartBuilder.create().uv(44, 0).cuboid(0.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, true), ModelTransform.of(5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        body.addChild("left_wing", ModelPartBuilder.create().uv(44, 0).cuboid(-2.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, false), ModelTransform.of(-5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        body.addChild("tail", ModelPartBuilder.create().uv(16, 4).cuboid(-7.0F, -10.0F, 0.0F, 14.0F, 10.0F, 0.0F, false), ModelTransform.of(0.0F, -7.0F, 4.0F, -0.7853982F, 0.0F, 0.0F));
        root.addChild("right_leg", ModelPartBuilder.create().uv(30, 17).cuboid(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, false), ModelTransform.of(-3.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        root.addChild("left_leg", ModelPartBuilder.create().uv(30, 17).cuboid(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, true), ModelTransform.of(3.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(data, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
    }

    @Override
    public void setAngles(TurkeyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float partialTick = animationProgress - (float) entity.age;
        float wingAngles = entity.getWingRotation(partialTick);
        float peckAngles = entity.getPeckProgress(partialTick);

        this.head.pitch = headPitch * ((float) Math.PI / 180F);
        this.head.yaw = headYaw * ((float) Math.PI / 180F);
        this.head.pitch += peckAngles * 0.8F;
        this.head.pivotZ = -3.0F - peckAngles * 1.5F;

        this.rightWing.roll = -wingAngles;
        this.leftWing.roll = wingAngles;

        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
    }
}
