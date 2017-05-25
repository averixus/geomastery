package com.jayavery.jjmod.render.block;

import java.util.List;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.blocks.BlockBeam.EnumAxis;
import com.jayavery.jjmod.blocks.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;

/** Renderer for straight wall blocks. */
public class WallRendererStraight extends WallRenderer {
    
    private IModel straight;
    private IModel cross;
    private IModel side;
    
    public WallRendererStraight(ResourceLocation block) {
        
        super(block);
    }
    
    @Override
    protected List<BakedQuad> getAllQuads(IBlockState state,
            EnumFacing side, long rand) {
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        List<BakedQuad> result = Lists.newArrayList();
        
        // Extract properties
        
        boolean hasNorth = extState.getValue(BlockWall.NORTH) != null;
        boolean hasEast = extState.getValue(BlockWall.EAST) != null;
        boolean hasSouth = extState.getValue(BlockWall.SOUTH) != null;
        boolean hasWest = extState.getValue(BlockWall.WEST) != null;
        
        // Straight or cross
        
        if ((hasNorth || hasSouth) && !hasEast && !hasWest) {
            
            this.addQuads(result, this.straight, this
                    .getStraightAngle(EnumAxis.NS), state, side, rand);
            
        } else if ((hasEast || hasWest) && !hasNorth && !hasSouth) {
            
            this.addQuads(result, this.straight, this.
                    getStraightAngle(EnumAxis.EW), state, side, rand);
            
        } else {
            
            this.addQuads(result, this.cross, 0, state, side, rand);
        }
        
        return result;
    }
    
    @Override
    public IModel loadModel(ResourceLocation rl) {
        
        this.straight = this.model("/straight");
        this.cross = this.model("/cross");
        this.side = this.model("/side");
        
        this.textures.addAll(this.straight.getTextures());
        this.textures.addAll(this.cross.getTextures());
        this.textures.addAll(this.side.getTextures());
        
        return this;
    }

    @Override
    protected IModel getSideModel(boolean isTop, boolean isBottom) {
        
        return this.side;
    }
    
    @Override
    protected int getSideAngle(EnumFacing facing) {
        
        return 180 + (90 * facing.getHorizontalIndex());
    }
    
    /** @return Offset rotation angle for this block's straight models. */
    protected int getStraightAngle(EnumAxis axis) {
        
        return axis == EnumAxis.NS ? 0 : 90;
    }
}
