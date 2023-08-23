package net.george.autumnity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.george.autumnity.block.AutumnityBlocks;
import net.george.autumnity.entity.AutumnityEntities;
import net.george.autumnity.entity.client.model.SnailEntityModel;
import net.george.autumnity.entity.client.model.TurkeyEntityModel;
import net.george.autumnity.entity.client.renderer.FallingHeadBlockRenderer;
import net.george.autumnity.entity.client.renderer.SnailEntityRenderer;
import net.george.autumnity.entity.client.renderer.TurkeyEggRenderer;
import net.george.autumnity.entity.client.renderer.TurkeyEntityRenderer;
import net.george.autumnity.particle.AutumnityParticles;
import net.george.autumnity.particle.custom.FallingMapleLeafParticle;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

/**
 * @since 1.0.0.build.1
 * @version 1.0.0.build.7
 * @author Mr.George
 */
public class AutumnityClient implements ClientModInitializer {
    public static final EntityModelLayer SNAIL = new EntityModelLayer(
            new Identifier(Autumnity.MOD_ID, "snail"), "main");
    public static final EntityModelLayer TURKEY = new EntityModelLayer(
            new Identifier(Autumnity.MOD_ID, "turkey"), "main");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.SNAIL_GOO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.SNAIL_GOO_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.AUTUMN_CROCUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.POTTED_AUTUMN_CROCUS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.TURKEY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.COOKED_TURKEY, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.FOUL_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.TALL_FOUL_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.POTTED_FOUL_BERRIES, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.MAPLE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.MAPLE_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.MAPLE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.POTTED_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.MAPLE_LEAF_PILE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.YELLOW_MAPLE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.YELLOW_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.POTTED_YELLOW_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.ORANGE_MAPLE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.ORANGE_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.POTTED_ORANGE_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.RED_MAPLE_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.RED_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.POTTED_RED_MAPLE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AutumnityBlocks.RED_MAPLE_LEAF_PILE, RenderLayer.getCutout());


        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ?
                BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
                AutumnityBlocks.MAPLE_LEAVES, AutumnityBlocks.MAPLE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 16760576,
                AutumnityBlocks.YELLOW_MAPLE_LEAVES, AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 16745768,
                AutumnityBlocks.ORANGE_MAPLE_LEAVES, AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 12665871,
                AutumnityBlocks.RED_MAPLE_LEAVES, AutumnityBlocks.RED_MAPLE_LEAF_PILE);

        ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> FoliageColors.getDefaultColor()),
                AutumnityBlocks.MAPLE_LEAVES, AutumnityBlocks.MAPLE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 16760576,
                AutumnityBlocks.YELLOW_MAPLE_LEAVES, AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 16745768,
                AutumnityBlocks.ORANGE_MAPLE_LEAVES, AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 12665871,
                AutumnityBlocks.RED_MAPLE_LEAVES, AutumnityBlocks.RED_MAPLE_LEAF_PILE);


        EntityRendererRegistry.register(AutumnityEntities.SNAIL, SnailEntityRenderer::new);
        EntityRendererRegistry.register(AutumnityEntities.TURKEY, TurkeyEntityRenderer::new);
        EntityRendererRegistry.register(AutumnityEntities.TURKEY_EGG, TurkeyEggRenderer::new);
        EntityRendererRegistry.register(AutumnityEntities.FALLING_HEAD_BLOCK, FallingHeadBlockRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SNAIL, SnailEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TURKEY, TurkeyEntityModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(AutumnityParticles.FALLING_MAPLE_LEAF, FallingMapleLeafParticle.Factory::new);
    }
}
