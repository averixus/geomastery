package com.jayavery.jjmod.render.block;

import java.util.List;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.blocks.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;

/** Renderer for single wall blocks. */
public class WallRendererSingle extends WallRenderer {
    
    private IModel post;
    private IModel side;

    public WallRendererSingle(ResourceLocation block) {
        
        super(block);
    }
    
    @Override
    protected List<BakedQuad> getAllQuads(IBlockState state,
            EnumFacing side, long rand) {
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        List<BakedQuad> result = Lists.newArrayList();
        
        // Post
        
        this.addQuads(result, this.post, 0, state, side, rand);
        
        // Sides
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (extState.getValue(BlockWall.blocks.get(facing)) != null) {
                
                this.addQuads(result, this.side, this.getSideAngle(facing),
                        state, side, rand);
            }
        }
        
        return result;
    }
    
    @Override
    public IModel loadModel(ResourceLocation rl) {
        
        this.post = this.model("/post");
        this.side = this.model("/side");
        
        this.textures.addAll(this.post.getTextures());
        this.textures.addAll(this.side.getTextures());
        
        return this;
    }
    
    @Override
    public IModel getSideModel(boolean isTop, boolean isBottom) {
        
        return this.side;
    }
    
    @Override
    public int getSideAngle(EnumFacing facing) {
        
        return 90 * facing.getHorizontalIndex();
    }
}
