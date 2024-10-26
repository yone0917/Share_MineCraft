package com.SakuraKijin.kijinmod.entity.render;

import com.SakuraKijin.kijinmod.entity.ArrowEntitys;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class ArrowEntitysRenderer extends EntityRenderer<ArrowEntitys> {
    public ArrowEntitysRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    public void render(ArrowEntitys p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(p_225623_3_, p_225623_1_.yRotO, p_225623_1_.yRot) - 90.0F));
        p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(p_225623_3_, p_225623_1_.xRotO, p_225623_1_.xRot)));
        p_225623_4_.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        p_225623_4_.scale(0.1F, 0.1F, 0.1F);
        p_225623_4_.translate(-4.0, 0.0, 0.0);
        IVertexBuilder lvt_18_2_ = p_225623_5_.getBuffer(RenderType.entityCutout(getTextureLocation(p_225623_1_)));
        MatrixStack.Entry lvt_19_1_ = p_225623_4_.last();
        Matrix4f lvt_20_1_ = lvt_19_1_.pose();
        Matrix3f lvt_21_1_ = lvt_19_1_.normal();

        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, -2, -2, 0.0F, 0.0F, -1, 0, 0, p_225623_6_);
        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_,0, -2, 2, 1.0F, 0.0F, -1, 0, 0, p_225623_6_);
        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, 2, 2, 1.0F, 1.0F, -1, 0, 0, p_225623_6_);
        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, 2, -2, 0.0F, 1.0F, -1, 0, 0, p_225623_6_);

        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, 2, -2, 0.0F, 1.0F, -1, 0, 0, p_225623_6_);
        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, 2, 2, 1.0F, 1.0F, -1, 0, 0, p_225623_6_);
        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, -2, 2, 1.0F, 0.0F, -1, 0, 0, p_225623_6_);
        this.vertex(lvt_20_1_, lvt_21_1_, lvt_18_2_, 0, -2, -2, 0.0F, 0.0F, -1, 0, 0, p_225623_6_);

        p_225623_4_.popPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
    public void vertex(Matrix4f p_229039_1_, Matrix3f p_229039_2_, IVertexBuilder p_229039_3_, int p_229039_4_, int p_229039_5_, int p_229039_6_, float p_229039_7_, float p_229039_8_, int p_229039_9_, int p_229039_10_, int p_229039_11_, int p_229039_12_) {
        p_229039_3_.vertex(p_229039_1_, (float)p_229039_4_, (float)p_229039_5_, (float)p_229039_6_).color(255, 255, 255, 255).uv(p_229039_7_, p_229039_8_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229039_12_).normal(p_229039_2_, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
    }


@Override
    public ResourceLocation getTextureLocation(ArrowEntitys entity) {
        return new ResourceLocation("kijinmod","textures/entity/projectiles/arrow_entitys.png");
    }
}
