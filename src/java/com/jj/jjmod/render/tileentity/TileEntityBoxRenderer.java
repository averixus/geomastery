package com.jj.jjmod.render.tileentity;

import com.jj.jjmod.tileentities.TEBox;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

/** Render class for box block. */
public class TileEntityBoxRenderer extends TileEntitySpecialRenderer<TEBox> {

    /** Texture for box .*/
    private static final ResourceLocation TEXTURE = new
            ResourceLocation("jjmod:textures/blocks/crafters/woodwork1.png");
    
    private ModelBox model = new ModelBox();
    
    @Override
    public void renderTileEntityAt(TEBox te, double x, double y,
            double z, float ticks, int destroyStage) {
                
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        
        if (destroyStage >= 0) {
            
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, 1.0F, 1.0F);
            GlStateManager.matrixMode(5888);
            
        } else {
            
            this.bindTexture(TEXTURE);
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        
        if (destroyStage < 0) {
            
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        
        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        
        GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        float angle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * ticks;
        angle = 1.0F - angle;
        angle = 1.0F - angle * angle * angle;
        this.model.lid1.rotateAngleX = -(angle * ((float)Math.PI / 2F));
        this.model.lid2.rotateAngleX = angle * ((float)Math.PI / 2F);
        this.model.knob.rotateAngleX = this.model.lid1.rotateAngleX;
        this.model.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        
        if (destroyStage >= 0) {
            
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
