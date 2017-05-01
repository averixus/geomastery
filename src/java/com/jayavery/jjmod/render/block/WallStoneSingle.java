package com.jayavery.jjmod.render.block;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.blocks.BlockWallComplex;
import com.jayavery.jjmod.blocks.BlockWallComplex.EnumConnection;
import com.jayavery.jjmod.blocks.BlockWallComplex.EnumPosition;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.utilities.UnlistedPropertyEnum;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

/** Model to wrap delayed baking of multipart. */
public class WallStoneSingle extends DelayedBakingAbstract {
    
    /** Cached map of quads by state. */
    protected static final Map<IBlockState, List<BakedQuad>> CACHE =
            Maps.newHashMap();
    
    /** Model names for wall connection types. */
    protected static final Map<EnumConnection, IModel> connections =
            Maps.newEnumMap(EnumConnection.class);
    
    /** Model names for wall post positions. */
    protected static final Map<EnumPosition, IModel> positions =
            Maps.newEnumMap(EnumPosition.class);
    
    /** Rotation angles for wall connection properties. */
    protected static final Map<UnlistedPropertyEnum<EnumConnection>, Integer>
            properties = Maps.newHashMap();
    
    /** Textures required to render this model. */
    protected static ImmutableList<ResourceLocation> textures;
    
    // Models for all possible parts
    protected static IModel bottomPost;
    protected static IModel bottomSide;
    protected static IModel lonePost;
    protected static IModel loneSide;
    protected static IModel middlePost;
    protected static IModel middleSide;
    protected static IModel topPost;
    protected static IModel topSide;

    @Override
    public Collection<ResourceLocation> getTextures() {

        return textures;
    }
    
    /** @return A model whose location begins with
     * "jjmod:block/wall_stone_single/". */
    protected static IModel model(String wallStoneSingle) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                "jjmod:block/wall_stone_single/" + wallStoneSingle),
                "Error loading model for delayed multipart!");
    }

    /** Sets up for delayed baking. */
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
            Function<ResourceLocation, TextureAtlasSprite> textureGetter) {

        connections.put(EnumConnection.BOTTOM_SINGLE, bottomSide);
        connections.put(EnumConnection.LONE_SINGLE, loneSide);
        connections.put(EnumConnection.MIDDLE_SINGLE, middleSide);
        connections.put(EnumConnection.TOP_SINGLE, topSide);
        
        positions.put(EnumPosition.BOTTOM, bottomPost);
        positions.put(EnumPosition.LONE, lonePost);
        positions.put(EnumPosition.MIDDLE, middlePost);
        positions.put(EnumPosition.TOP, topPost);
        
        properties.put(BlockWallComplex.NORTH, 180);
        properties.put(BlockWallComplex.EAST, 270);
        properties.put(BlockWallComplex.SOUTH, 0);
        properties.put(BlockWallComplex.WEST, 90);
        
        this.bakeInfo(format, textureGetter, "jjmod:blocks/complex/stone1");
        return this;
    }
    
    /** Retrieves from cache or bakes as required. */
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side,
            long rand) {
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return Collections.emptyList();
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        if (CACHE.containsKey(state)) {
            
            return CACHE.get(state);
        }

        List<BakedQuad> result = Lists.newArrayList();
        
        this.addQuads(result, positions.get(extState.getValue(BlockWallComplex
                .POSITION)), state, side, rand);
        
        for (Entry<UnlistedPropertyEnum<EnumConnection>, Integer> entry :
                properties.entrySet()) {
            
            EnumConnection connection = extState.getValue(entry.getKey());
            
            if (connection != EnumConnection.NONE) {
            
                this.addQuads(result, connections.get(connection),
                        entry.getValue(), state, side, rand);
            }
        }

        CACHE.put(state, result);
        return result;
    }
    
    /** Loader for delayed baking model. */
    public static class Loader implements ICustomModelLoader {

        @Override
        public void onResourceManagerReload(IResourceManager rm) {
            
            CACHE.clear();
        }

        @Override
        public boolean accepts(ResourceLocation loc) {

            return loc instanceof ModelResourceLocation &&
                    ((ModelResourceLocation) loc).getVariant()
                    .contains("delayedbake") &&
                    ModBlocks.wallStoneSingle.getRegistryName().equals(loc);
        }

        /** Loads dependent parts and provides the delayed baking model. */
        @Override
        public IModel loadModel(ResourceLocation modelLocation)
                throws Exception {
            
             bottomPost = bottomPost == null ?
                     model("bottom_post") : bottomPost;
             bottomSide = bottomSide == null ?
                     model("bottom_side") : bottomSide;
             lonePost = lonePost == null ? model("lone_post") : lonePost;
             loneSide = loneSide == null ?
                     model("lone_side") : loneSide;
             middlePost = middlePost == null ?
                     model("middle_post") : middlePost;
             middleSide = middleSide == null ?
                     model("middle_side") : middleSide;
             topPost = topPost == null ? model("top_post") : topPost;
             topSide = topSide == null ?
                     model("top_side") : topSide;
             
             if (textures == null) {
                 
                 ImmutableList.Builder<ResourceLocation> builder =
                         ImmutableList.builder();
                 
                 for (IModel model : new IModel[] {bottomSide,
                         loneSide, middleSide, topSide, bottomPost,
                         middlePost, topPost, lonePost}) {
                     
                     builder.addAll(model.getTextures());
                 }
                 
                 textures = builder.build();
             }

            return new WallStoneSingle();
        }
    }
}
