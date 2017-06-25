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
import jayavery.geomastery.blocks.BlockBeam.EnumAxis;
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
    
    private IModel postTop;
    private IModel postBottom;
    private IModel postMiddle;
    private IModel postLone;
    private IModel sideTop;
    private IModel sideBottom;
    private IModel sideMiddle;
    private IModel sideLone;
    private IModel straightTop;
    private IModel straightLone;
    private final boolean isDouble;
    
    public RenderWallComplex(ResourceLocation block, boolean isDouble) {
        
        super(block);
        this.isDouble = isDouble;
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

        // Straight models if applicable
        
        if (!this.isDouble && !northPri && !southPri && thisTop &&
                eastSide == null && westSide == null &&
                northBot == southBot == thisBot) {
            
            this.addQuads(result, this.getStraightModel(thisBot),
                    this.getStraightAngle(EnumAxis.NS), state, side, rand);
            return result;
            
        } else if (!this.isDouble && !eastPri && !westPri && thisTop &&
                northSide == null && southSide == null &&
                eastBot == westBot == thisBot) {
            
            this.addQuads(result, this.getStraightModel(thisBot),
                    this.getStraightAngle(EnumAxis.EW), state, side, rand);
            return result;
        }
        
        // Post
        
        this.addQuads(result, this.getPostModel(thisTop, thisBot),
                0, state, side, rand);
        
        // Sides
        
        if (northBlock != null) {
            
            boolean northTop = extState.getValue(BlockWall.N_TOP);
            
            if (northBlock.isDouble() != thisBlock.isDouble()) {
                
                northTop = northPri ? northTop : thisTop;
                
            } else {
                
                northTop = northTop || thisTop;
            }
            
            RenderWallAbstract northRenderer = ClientProxy.WALL_RENDERS.get(northSide);
            IModel northModel = northRenderer
                    .getConnectedSide(northTop, northBot);
            this.addQuads(result, northModel, northRenderer
                    .getSideAngle(EnumFacing.NORTH), state, side, rand);
        }
        
        if (eastBlock != null) {
            
            boolean eastTop = extState.getValue(BlockWall.E_TOP);
            
            if (eastBlock.isDouble() != thisBlock.isDouble()) {
                
                eastTop = eastPri ? eastTop : thisTop;
                
            } else {
                
                eastTop = eastTop || thisTop;
            }
            
            RenderWallAbstract eastRenderer = ClientProxy.WALL_RENDERS.get(eastSide);
            IModel eastModel = eastRenderer.getConnectedSide(eastTop, eastBot);
            this.addQuads(result, eastModel, eastRenderer
                    .getSideAngle(EnumFacing.EAST), state, side, rand);
        }
        
        if (southBlock != null) {
            
            boolean southTop = extState.getValue(BlockWall.S_TOP);
            
            if (southBlock.isDouble() != thisBlock.isDouble()) {
                
                southTop = southPri ? southTop : thisTop;
                
            } else {
                
                southTop = southTop || thisTop;
            }
            
            RenderWallAbstract southRenderer = ClientProxy.WALL_RENDERS.get(southSide);
            IModel southModel = southRenderer
                    .getConnectedSide(southTop, southBot);
            this.addQuads(result, southModel, southRenderer
                    .getSideAngle(EnumFacing.SOUTH), state, side, rand);
        }
        
        if (westBlock != null) {
            
            boolean westTop = extState.getValue(BlockWall.W_TOP);
            
            if (westBlock.isDouble() != thisBlock.isDouble()) {
                
                westTop = westPri ? westTop : thisTop;
                
            } else {
                
                westTop = westTop || thisTop;
            }

            RenderWallAbstract westRenderer = ClientProxy.WALL_RENDERS.get(westSide);
            IModel westModel = westRenderer.getConnectedSide(westTop, westBot);
            this.addQuads(result, westModel, westRenderer
                    .getSideAngle(EnumFacing.WEST), state, side, rand);
        }
        
        return result;
    }
    
    @Override
    public IModel loadModel(ResourceLocation rl) {
        
        // Load part models
        
        this.postTop = this.model("/top_post");
        this.postBottom = this.model("/bottom_post");
        this.postMiddle = this.model("/middle_post");
        this.postLone = this.model("/lone_post");
        this.sideTop = this.model("/top_side");
        this.sideBottom = this.model("/bottom_side");
        this.sideMiddle = this.model("/middle_side");
        this.sideLone = this.model("/lone_side");
        
        // Prepare texture dependencies list
         
        for (IModel model : new IModel[] {this.postTop, this.postBottom,
                this.postMiddle, this.postLone, this.sideTop, this.sideBottom,
                this.sideMiddle, this.sideLone}) {
            
            this.textures.addAll(model.getTextures());
        }
        
        // Prepare models and textures for straight walls
        
        if (!this.isDouble) {
            
            this.straightLone = this.model("/lone_straight");
            this.straightTop = this.model("/top_straight");
            this.textures.addAll(this.straightLone.getTextures());
            this.textures.addAll(this.straightTop.getTextures());
        }
        
        return this;
    }

    @Override
    public IModel getConnectedSide(boolean isTop, boolean isBottom) {
        
        return isBottom ? isTop ? this.sideLone : this.sideBottom :
                isTop ? this.sideTop : this.sideMiddle;
    }
    
    @Override
    public int getSideAngle(EnumFacing facing) {

        return (90 * facing.getHorizontalIndex());
    }
    
    /** @return Model for this wall's post with given properties. */
    protected IModel getPostModel(boolean isTop, boolean isBottom) {
        
        return isBottom ? isTop ? this.postLone : this.postBottom :
            isTop ? this.postTop : this.postMiddle;
    }
    
    /** @return Model for this straight through wall with given properties. */
    public IModel getStraightModel(boolean isBottom) {
        
        return isBottom ? this.straightLone : this.straightTop;
    }
    
    /** @return Offset rotation angle for this block's straight models. */
    public int getStraightAngle(EnumAxis axis) {
        
        return axis == EnumAxis.NS ? 0 : 90;
    }
}
