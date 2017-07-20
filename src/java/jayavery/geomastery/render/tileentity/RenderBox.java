/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.tileentity;

import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEStorage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

/** Renderer for box block. */
public class RenderBox
        extends TileEntitySpecialRenderer<TEStorage.Box> {

    /** Texture for box .*/
    private static final ResourceLocation TEXTURE = new ResourceLocation(Geomastery.MODID, "textures/entity/box.png");
    /** Model for box. */
    private ModelBox model = new ModelBox();
    
    @Override
    public void renderTileEntityAt(TEStorage.Box te, double x, double y,
            double z, float ticks, int destroyStage) {
                
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        
        if (destroyStage >= 0) {
            
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
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
        
        GlStateManager.rotate(20F, 0.0F, 1.0F, 0.0F);
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
