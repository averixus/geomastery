/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.tileentity;

import java.util.Map.Entry;
import org.lwjgl.opengl.GL11;
import jayavery.geomastery.blocks.BlockFacing;
import jayavery.geomastery.tileentities.TETrunk;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TEST
public class RenderTrunk extends TileEntitySpecialRenderer<TETrunk> {

    @Override
    public void renderTileEntityAt(TETrunk te, double x, double y,
            double z, float tick, int damage) {
        
        IBlockState state = te.getWorld().getBlockState(te.getPos());
        EnumFacing fallDir = state.getValue(BlockFacing.FACING);

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        BlockPos pos = te.getPos();
        float angle = (float) (te.prevAngle + (te.angle - te.prevAngle) * Math.pow(1.1, tick));

        
        if (fallDir == EnumFacing.NORTH) {
            
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(-angle, 1, 0, 0);
            GlStateManager.translate(-pos.getX(), -pos.getY(), -pos.getZ());
            
        } else if (fallDir == EnumFacing.SOUTH) {
            
            GlStateManager.translate(x, y, z + 1);
            GlStateManager.rotate(angle, 1, 0, 0);
            GlStateManager.translate(-pos.getX(), -pos.getY(), -pos.getZ() - 1);
            
        } else if (fallDir == EnumFacing.WEST) {
            
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(angle, 0, 0, 1);
            GlStateManager.translate(-pos.getX(), -pos.getY(), -pos.getZ());
            
        } else if (fallDir == EnumFacing.EAST) {
            
            GlStateManager.translate(x + 1, y, z);
            GlStateManager.rotate(-angle, 0, 0, 1);
            GlStateManager.translate(-pos.getX() - 1, -pos.getY(), -pos.getZ());
        }
        
        
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        BlockModelRenderer render = dispatcher.getBlockModelRenderer();
        World world = te.getWorld();
        
        if (te.angle < 90) {
            for (Entry<BlockPos, IBlockState> entry : te.blocks.entrySet()) {
                System.out.println("blocks entry " + entry);
                render.renderModel(world, dispatcher.getModelForState(entry.getValue()), entry.getValue(), entry.getKey(), vb, false);
            }
        }
        
        tess.draw();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate(x - pos.getX(), y - pos.getY(), z - pos.getZ());
        vb.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        render.renderModel(world, dispatcher.getModelForState(Blocks.LOG.getDefaultState()), Blocks.LOG.getDefaultState(), pos, vb, false);
        tess.draw();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
