/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.block;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.blocks.BlockWall;
import jayavery.geomastery.main.ClientProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Renderer for single wall blocks. */
@SideOnly(Side.CLIENT)
public class RenderWallSingle extends RenderWallAbstract {
    
    private IModel post;
    private IModel side;
    private IModel unconnected = null;
    
    private final int baseAngle;
    
    public RenderWallSingle(ResourceLocation block, int baseAngle) {
        
        super(block);
        this.baseAngle = baseAngle;
    }
    
    @Override
    protected List<BakedQuad> getAllQuads(IBlockState state,
            EnumFacing side, long rand) {
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        List<BakedQuad> result = Lists.newArrayList();
        
        // Extract block data
        
        BlockWall thisBlock = (BlockWall) extState.getBlock();
        boolean thisTop = extState.getValue(BlockWall.TOP);
        boolean thisBot = extState.getValue(BlockWall.BOTTOM);
        
        BlockWall northBlock = extState.getValue(BlockWall.NORTH);
        boolean northPri = thisBlock.compareTo(northBlock) > 0;
        BlockWall northSide = northPri ? northBlock : thisBlock;
        boolean northBot = thisBot && extState.getValue(BlockWall.N_BOTTOM);

        BlockWall eastBlock = extState.getValue(BlockWall.EAST);
        boolean eastPri = thisBlock.compareTo(eastBlock) > 0;
        BlockWall eastSide = eastPri ? eastBlock : thisBlock;
        boolean eastBot = thisBot && extState.getValue(BlockWall.E_BOTTOM);

        BlockWall southBlock = extState.getValue(BlockWall.SOUTH);
        boolean southPri = thisBlock.compareTo(southBlock) > 0;
        BlockWall southSide = southPri ? southBlock : thisBlock;
        boolean southBot = thisBot && extState.getValue(BlockWall.S_BOTTOM);

        BlockWall westBlock = extState.getValue(BlockWall.WEST);
        boolean westPri = thisBlock.compareTo(westBlock) > 0;
        BlockWall westSide = westPri ? westBlock : thisBlock;
        boolean westBot = thisBot && extState.getValue(BlockWall.W_BOTTOM);
        
        // Post
        
        this.addQuads(result, this.post, 0, state, side, rand);
        
        // Sides
        
        if (northBlock != null) {
            
            boolean northTop = thisTop || extState.getValue(BlockWall.N_TOP);
            
            RenderWallAbstract northRenderer = ClientProxy.WALL_RENDERS
                    .get(northSide);
            IModel northModel = northRenderer
                    .getConnectedSide(northTop, northBot, false);
            this.addQuads(result, northModel, northRenderer
                    .getSideAngle(EnumFacing.NORTH), state, side, rand);
            
        } else {
            
            this.addQuads(result, this.unconnected,
                    this.getSideAngle(EnumFacing.NORTH), state, side, rand);
        }
        
        if (eastBlock != null) {
            
            boolean eastTop = thisTop || extState.getValue(BlockWall.E_TOP);
            
            RenderWallAbstract eastRenderer = ClientProxy.WALL_RENDERS
                    .get(eastSide);
            IModel eastModel = eastRenderer.getConnectedSide(eastTop,
                    eastBot, false);
            this.addQuads(result, eastModel, eastRenderer
                    .getSideAngle(EnumFacing.EAST), state, side, rand);
            
        } else {
            
            this.addQuads(result, this.unconnected,
                    this.getSideAngle(EnumFacing.EAST), state, side, rand);
        }
        
        if (southBlock != null) {
            
            boolean southTop = thisTop || extState.getValue(BlockWall.S_TOP);

            RenderWallAbstract southRenderer = ClientProxy.WALL_RENDERS
                    .get(southSide);
            IModel southModel = southRenderer.getConnectedSide(southTop,
                    southBot, false);
            this.addQuads(result, southModel, southRenderer
                    .getSideAngle(EnumFacing.SOUTH), state, side, rand);
            
        } else {
            
            this.addQuads(result, this.unconnected,
                    this.getSideAngle(EnumFacing.SOUTH), state, side, rand);
        }
        
        if (westBlock != null) {
            
            boolean westTop = thisTop || extState.getValue(BlockWall.W_TOP);

            RenderWallAbstract westRenderer = ClientProxy.WALL_RENDERS
                    .get(westSide);
            IModel westModel = westRenderer.getConnectedSide(westTop,
                    westBot, false);
            this.addQuads(result, westModel, westRenderer
                    .getSideAngle(EnumFacing.WEST), state, side, rand);
            
        } else {
            
            this.addQuads(result, this.unconnected,
                    this.getSideAngle(EnumFacing.WEST), state, side, rand);
        }
        
        return result;
    }
    
    @Override
    public IModel loadModel(ResourceLocation rl) {
        
        this.post = this.model("/post");
        this.side = this.model("/side");
        this.unconnected = this.model("/unconnected");
        
        this.textures.addAll(this.post.getTextures());
        this.textures.addAll(this.side.getTextures());
        
        return this;
    }
    
    @Override
    public IModel getConnectedSide(boolean isTop, boolean isBottom,
            boolean isDouble) {
        
        return this.side;
    }
    
    @Override
    public int getSideAngle(EnumFacing facing) {
        
        return this.baseAngle + (90 * facing.getHorizontalIndex());
    }
}
