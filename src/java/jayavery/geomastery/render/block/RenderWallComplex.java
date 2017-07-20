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
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.blocks.BlockBeam.EBeamAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Renderer for complex wall blocks. */
@SideOnly(Side.CLIENT)
public class RenderWallComplex extends RenderWallAbstract {
    
    private IModel singlePostTop;
    private IModel singlePostBottom;
    private IModel singlePostMiddle;
    private IModel singlePostLone;
    private IModel singleSideTop;
    private IModel singleSideBottom;
    private IModel singleSideMiddle;
    private IModel singleSideLone;
    
    private IModel doublePostTop;
    private IModel doublePostBottom;
    private IModel doublePostMiddle;
    private IModel doublePostLone;
    private IModel doubleSideTop;
    private IModel doubleSideBottom;
    private IModel doubleSideMiddle;
    private IModel doubleSideLone;
    
    private IModel singleStraightTop;
    private IModel singleStraightLone;
    
    public RenderWallComplex(ResourceLocation block) {
        
        super(block);
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

        // Straight models if applicable
        
        if (!thisDoub && !northPri && !southPri && thisTop &&
                eastSide == null && westSide == null &&
                northBot == southBot == thisBot) {
            
            this.addQuads(result, this.getStraightModel(thisBot),
                    this.getStraightAngle(EBeamAxis.NS), state, side, rand);
            return result;
            
        } else if (!thisDoub && !eastPri && !westPri && thisTop &&
                northSide == null && southSide == null &&
                eastBot == westBot == thisBot) {
            
            this.addQuads(result, this.getStraightModel(thisBot),
                    this.getStraightAngle(EBeamAxis.EW), state, side, rand);
            return result;
        }
        
        // Post
        
        this.addQuads(result, this.getPostModel(thisTop, thisBot, thisDoub),
                0, state, side, rand);
        
        // Sides
        
        if (northBlock != null) {
            
            boolean northTop = extState.getValue(BlockWall.N_TOP);
            
            if (northDoub != thisDoub) {
                
                northTop = northPri ? northTop : thisTop;
                
            } else {
                
                northTop = northTop || thisTop;
            }
            
            RenderWallAbstract northRenderer = ClientProxy.WALL_RENDERS
                    .get(northSide);
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
            
            RenderWallAbstract eastRenderer = ClientProxy.WALL_RENDERS
                    .get(eastSide);
            IModel eastModel = eastRenderer.getConnectedSide(eastTop,
                    eastBot, eastDoub);
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
            
            RenderWallAbstract southRenderer = ClientProxy.WALL_RENDERS
                    .get(southSide);
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

            RenderWallAbstract westRenderer = ClientProxy.WALL_RENDERS
                    .get(westSide);
            IModel westModel = westRenderer.getConnectedSide(westTop,
                    westBot, westDoub);
            this.addQuads(result, westModel, westRenderer
                    .getSideAngle(EnumFacing.WEST), state, side, rand);
        }
        
        return result;
    }
    
    @Override
    public IModel loadModel(ResourceLocation rl) {
        
        // Load part models
        
        this.singlePostTop = this.model("/top_post_single");
        this.singlePostBottom = this.model("/bottom_post_single");
        this.singlePostMiddle = this.model("/middle_post_single");
        this.singlePostLone = this.model("/lone_post_single");
        this.singleSideTop = this.model("/top_side_single");
        this.singleSideBottom = this.model("/bottom_side_single");
        this.singleSideMiddle = this.model("/middle_side_single");
        this.singleSideLone = this.model("/lone_side_single");
        
        this.doublePostTop = this.model("/top_post_double");
        this.doublePostBottom = this.model("/bottom_post_double");
        this.doublePostMiddle = this.model("/middle_post_double");
        this.doublePostLone = this.model("/lone_post_double");
        this.doubleSideTop = this.model("/top_side_double");
        this.doubleSideBottom = this.model("/bottom_side_double");
        this.doubleSideMiddle = this.model("/middle_side_double");
        this.doubleSideLone = this.model("/lone_side_double");
        
        this.singleStraightLone = this.model("/lone_straight_single");
        this.singleStraightTop = this.model("/top_straight_single");
        
        // Prepare texture dependencies list
         
        for (IModel model : new IModel[] {this.singlePostTop,
                this.singlePostBottom, this.singlePostMiddle,
                this.singlePostLone, this.singleSideTop, this.singleSideBottom,
                this.singleSideMiddle, this.singleSideLone, this.doublePostTop,
                this.doublePostBottom, this.doublePostMiddle,
                this.doublePostLone, this.doubleSideTop, this.doubleSideBottom,
                this.doubleSideMiddle, this.doubleSideLone,
                this.singleStraightLone, this.singleStraightTop}) {
            
            this.textures.addAll(model.getTextures());
        }
        
        return this;
    }

    @Override
    public IModel getConnectedSide(boolean isTop,
            boolean isBottom, boolean isDouble) {
        
        if (!isDouble) {
        
            return isBottom ? isTop ? this.singleSideLone :
                    this.singleSideBottom : isTop ?
                    this.singleSideTop : this.singleSideMiddle;
            
        } else {
            
            return isBottom ? isTop ? this.doubleSideLone :
                    this.doubleSideBottom : isTop ?
                    this.doubleSideTop : this.doubleSideMiddle;
        }
    }
    
    @Override
    public int getSideAngle(EnumFacing facing) {

        return (90 * facing.getHorizontalIndex());
    }
    
    /** @return Model for this wall's post with given properties. */
    protected IModel getPostModel(boolean isTop, boolean isBottom,
            boolean isDouble) {
        
        if (!isDouble) {
        
            return isBottom ? isTop ? this.singlePostLone :
                    this.singlePostBottom : isTop ?
                    this.singlePostTop : this.singlePostMiddle;
            
        } else {
            
            return isBottom ? isTop ? this.doublePostLone :
                    this.doublePostBottom : isTop ?
                    this.doublePostTop : this.doublePostMiddle;
        }
    }
    
    /** @return Model for this straight through wall with given properties. */
    public IModel getStraightModel(boolean isBottom) {
        
        return isBottom ? this.singleStraightLone : this.singleStraightTop;
    }
    
    /** @return Offset rotation angle for this block's straight models. */
    public int getStraightAngle(EBeamAxis axis) {
        
        return axis == EBeamAxis.NS ? 0 : 90;
    }
}
