/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.tileentity;

import jayavery.geomastery.blocks.BlockContainerFacing;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

/** Renderer for chest block. */
public class RenderChest
        extends TileEntitySpecialRenderer<TEStorage.Chest> {

    /** Texture for chest. */
    private static final ResourceLocation TEXTURE = new ResourceLocation(Geomastery.MODID, "textures/entity/chest.png");
    /** Model for chest. */
    private ModelChest model = new ModelChest();
    
    @Override
    public void renderTileEntityAt(TEStorage.Chest te, double x, double y,
            double z, float ticks, int destroyStage) {
        
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        
        if (destroyStage >= 0) {
            
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
            
        } else {
            
            this.bindTexture(TEXTURE);
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        
        if (destroyStage < 0) {
            
            GlStateManager.color(1F, 1F, 1F, 1F);
        }
        
        GlStateManager.translate(x, y + 1F, z + 1F);
        GlStateManager.scale(1F, -1F, -1F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        
        float rotation = 0;
        
        if (te.hasWorld()) {
            
            IBlockState state = te.getWorld().getBlockState(te.getPos());
            rotation = 90 * ((state.getValue(BlockContainerFacing.FACING)
                    .getHorizontalIndex() + 2) % 4);
        }
        
        GlStateManager.rotate(rotation, 0F, 1F, 0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        float angle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * ticks;
        angle = 1.0F - angle;
        angle = 1.0F - angle * angle * angle;
        this.model.chestLid.rotateAngleX = -(angle * ((float) Math.PI / 2F));
        this.model.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        
        if (destroyStage >= 0) {
            
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
