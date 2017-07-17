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
import jayavery.geomastery.blocks.BlockBeam.EBeamAxis;
import jayavery.geomastery.main.ClientProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.property.IExtendedBlockState;

/** Renderer for heaping wall blocks. */
public class RenderWallHeaping extends RenderWallAbstract {

    private IModel singlePost;
    private IModel singleSide;
    private IModel doublePost;
    private IModel doubleSide;
    
    private final int sideAngle;
    
    public RenderWallHeaping(ResourceLocation block, int sideAngle) {
        
        super(block);
        this.sideAngle = sideAngle;
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
        boolean thisDoub = extState.getValue(BlockWall.DOUBLE);
        
        BlockWall northBlock = extState.getValue(BlockWall.NORTH);
        boolean northPri = thisBlock.compareTo(northBlock) > 0;
        BlockWall northSide = northPri ? northBlock : thisBlock;
        boolean northBot = thisBot && extState.getValue(BlockWall.N_BOTTOM);
        boolean northDoub = thisDoub && extState.getValue(BlockWall.N_DOUBLE);

        BlockWall eastBlock = extState.getValue(BlockWall.EAST);
        boolean eastPri = thisBlock.compareTo(eastBlock) > 0;
        BlockWall eastSide = eastPri ? eastBlock : thisBlock;
        boolean eastBot = thisBot && extState.getValue(BlockWall.E_BOTTOM);
        boolean eastDoub = thisDoub && extState.getValue(BlockWall.E_DOUBLE);

        BlockWall southBlock = extState.getValue(BlockWall.SOUTH);
        boolean southPri = thisBlock.compareTo(southBlock) > 0;
        BlockWall southSide = southPri ? southBlock : thisBlock;
        boolean southBot = thisBot && extState.getValue(BlockWall.S_BOTTOM);
        boolean southDoub = thisDoub && extState.getValue(BlockWall.S_DOUBLE);

        BlockWall westBlock = extState.getValue(BlockWall.WEST);
        boolean westPri = thisBlock.compareTo(westBlock) > 0;
        BlockWall westSide = westPri ? westBlock : thisBlock;
        boolean westBot = thisBot && extState.getValue(BlockWall.W_BOTTOM);
        boolean westDoub = thisDoub && extState.getValue(BlockWall.W_DOUBLE);
        
        // Post
        
        this.addQuads(result, this.getPostModel(thisDoub),
                0, state, side, rand);
        
        // Sides
        
        if (northBlock != null) {
            
            boolean northTop = extState.getValue(BlockWall.N_TOP);
            
            if (northDoub != thisDoub) {
                
                northTop = northPri ? northTop : thisTop;
                
            } else {
                
                northTop = northTop || thisTop;
            }
            
            RenderWallAbstract northRenderer = ClientProxy.WALL_RENDERS.get(northSide);
            IModel northModel = northRenderer
                    .getConnectedSide(northTop, northBot, northDoub);
            this.addQuads(result, northModel, northRenderer
                    .getSideAngle(EnumFacing.NORTH), state, side, rand);
        }
        
        if (eastBlock != null) {
            
            boolean eastTop = extState.getValue(BlockWall.E_TOP);
            
            if (eastDoub != thisDoub) {
                
                eastTop = eastPri ? eastTop : thisTop;
                
            } else {
                
                eastTop = eastTop || thisTop;
            }
            
            RenderWallAbstract eastRenderer = ClientProxy.WALL_RENDERS.get(eastSide);
            IModel eastModel = eastRenderer.getConnectedSide(eastTop, eastBot, eastDoub);
            this.addQuads(result, eastModel, eastRenderer
                    .getSideAngle(EnumFacing.EAST), state, side, rand);
        }
        
        if (southBlock != null) {
            
            boolean southTop = extState.getValue(BlockWall.S_TOP);
            
            if (southDoub != thisDoub) {
                
                southTop = southPri ? southTop : thisTop;
                
            } else {
                
                southTop = southTop || thisTop;
            }
            
            RenderWallAbstract southRenderer = ClientProxy.WALL_RENDERS.get(southSide);
            IModel southModel = southRenderer
                    .getConnectedSide(southTop, southBot, southDoub);
            this.addQuads(result, southModel, southRenderer
                    .getSideAngle(EnumFacing.SOUTH), state, side, rand);
        }
        
        if (westBlock != null) {
            
            boolean westTop = extState.getValue(BlockWall.W_TOP);
            
            if (westDoub != thisDoub) {
                
                westTop = westPri ? westTop : thisTop;
                
            } else {
                
                westTop = westTop || thisTop;
            }

            RenderWallAbstract westRenderer = ClientProxy.WALL_RENDERS.get(westSide);
            IModel westModel = westRenderer.getConnectedSide(westTop, westBot, westDoub);
            this.addQuads(result, westModel, westRenderer
                    .getSideAngle(EnumFacing.WEST), state, side, rand);
        }
        
        return result;
    }
    
    @Override
    public IModel loadModel(ResourceLocation rl) {
        
        // Load part models
        
        this.singlePost = this.model("/post_single");
        this.singleSide = this.model("/side_single");
        this.doublePost = this.model("/post_double");
        this.doubleSide = this.model("/side_double");
        
        // Prepare texture dependencies list
        
        for (IModel model : new IModel[] {this.singlePost, this.singleSide,
                this.doublePost, this.doubleSide}) {
            
            this.textures.addAll(model.getTextures());
        }
        
        return this;
    }
    
    @Override
    public IModel getConnectedSide(boolean isTop, boolean isBottom, boolean isDouble) {
        
        return isDouble ? this.doubleSide : this.singleSide;
    }
    
    @Override
    public int getSideAngle(EnumFacing facing) {
        
        return this.sideAngle + (90 * facing.getHorizontalIndex());
    }
    
    protected IModel getPostModel(boolean isDouble) {
        
        return isDouble ? this.doublePost : this.singlePost;
    }
}
