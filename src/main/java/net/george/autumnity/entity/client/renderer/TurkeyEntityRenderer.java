package net.george.autumnity.entity.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.george.autumnity.Autumnity;
import net.george.autumnity.AutumnityClient;
import net.george.autumnity.entity.client.model.TurkeyEntityModel;
import net.george.autumnity.entity.passive.TurkeyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

/**
 * @since 1.0.0.build.6
 * @author Mr.George
 */
@Environment(EnvType.CLIENT)
public class TurkeyEntityRenderer extends MobEntityRenderer<TurkeyEntity, TurkeyEntityModel> {
    public TurkeyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TurkeyEntityModel(context.getPart(AutumnityClient.TURKEY)), 0.5f);
    }

    @Override
    public Identifier getTexture(TurkeyEntity entity) {
        return new Identifier(Autumnity.MOD_ID, "textures/entity/turkey/turkey.png");
    }
}
