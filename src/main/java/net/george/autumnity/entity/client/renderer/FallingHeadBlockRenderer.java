package net.george.autumnity.entity.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.entity.special.FallingHeadBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@Environment(EnvType.CLIENT)
@SuppressWarnings("deprecation")
public class FallingHeadBlockRenderer extends EntityRenderer<FallingHeadBlockEntity> {
    public FallingHeadBlockRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(FallingHeadBlockEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        BlockState blockstate = entity.getBlockState();
        if (blockstate.getRenderType() == BlockRenderType.MODEL) {
            World world = entity.getWorld();
            if (blockstate != world.getBlockState(entity.getBlockPos()) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
                matrices.push();
                BlockPos blockpos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                matrices.translate(-0.5D, 0.0D, -0.5D);
                BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();
                //TODO: ?? TEST
                for (RenderLayer type : RenderLayer.getBlockLayers()) {
                    if (type == RenderLayer.getSolid()) {
                        manager.getModelRenderer().render(world, manager.getModel(blockstate),
                                blockstate, blockpos, matrices, vertexConsumers.getBuffer(type),
                                false, new Random(), blockstate.getRenderingSeed(entity.getFallingBlockPos()),
                                OverlayTexture.DEFAULT_UV);
                    }
                }
                matrices.pop();
                super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
            }
        }
    }

    @Override
    public Identifier getTexture(FallingHeadBlockEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
