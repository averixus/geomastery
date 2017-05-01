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
public class WallBrickDouble extends DelayedBakingAbstract {
    
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
    protected static IModel bottomSideDouble;
    protected static IModel bottomSideSingle;
    protected static IModel lonePost;
    protected static IModel loneSideDouble;
    protected static IModel loneSideSingle;
    protected static IModel middlePost;
    protected static IModel middleSideDouble;
    protected static IModel middleSideSingle;
    protected static IModel topPost;
    protected static IModel topSideDouble;
    protected static IModel topSideSingle;

    @Override
    public Collection<ResourceLocation> getTextures() {

        return textures;
    }
    
    /** @return A model whose location begins with "jjmod:block/wall_brick_". */
    protected static IModel model(String wallBrick) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                "jjmod:block/wall_brick_" + wallBrick),
                "Error loading model for delayed multipart!");
    }

    /** Sets up for delayed baking. */
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
            Function<ResourceLocation, TextureAtlasSprite> textureGetter) {

        connections.put(EnumConnection.BOTTOM_SINGLE, bottomSideSingle);
        connections.put(EnumConnection.BOTTOM_DOUBLE, bottomSideDouble);
        connections.put(EnumConnection.LONE_DOUBLE, loneSideDouble);
        connections.put(EnumConnection.LONE_SINGLE, loneSideSingle);
        connections.put(EnumConnection.MIDDLE_DOUBLE, middleSideDouble);
        connections.put(EnumConnection.MIDDLE_SINGLE, middleSideSingle);
        connections.put(EnumConnection.TOP_DOUBLE, topSideDouble);
        connections.put(EnumConnection.TOP_SINGLE, topSideSingle);
        
        positions.put(EnumPosition.BOTTOM, bottomPost);
        positions.put(EnumPosition.LONE, lonePost);
        positions.put(EnumPosition.MIDDLE, middlePost);
        positions.put(EnumPosition.TOP, topPost);
        
        properties.put(BlockWallComplex.NORTH, 180);
        properties.put(BlockWallComplex.EAST, 270);
        properties.put(BlockWallComplex.SOUTH, 0);
        properties.put(BlockWallComplex.WEST, 90);
        
        this.bakeInfo(format, textureGetter, "jjmod:blocks/complex/brickpave1");
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
                    ModBlocks.wallBrickDouble.getRegistryName().equals(loc);
        }

        /** Loads dependent parts and provides the delayed baking model. */
        @Override
        public IModel loadModel(ResourceLocation modelLocation)
                throws Exception {
            
             bottomPost = bottomPost == null ?
                     model("double/bottom_post") : bottomPost;
             bottomSideDouble = bottomSideDouble == null ?
                     model("double/bottom_side") : bottomSideDouble;
             bottomSideSingle = bottomSideSingle == null ?
                     model("single/bottom_side") : bottomSideSingle;
             lonePost = lonePost == null ? model("double/lone_post") : lonePost;
             loneSideDouble = loneSideDouble == null ?
                     model("double/lone_side") : loneSideDouble;
             loneSideSingle = loneSideSingle == null ?
                     model("single/lone_side") : loneSideSingle;
             middlePost = middlePost == null ?
                     model("double/middle_post") : middlePost;
             middleSideDouble = middleSideDouble == null ?
                     model("double/middle_side") : middleSideDouble;
             middleSideSingle = middleSideSingle == null ?
                     model("single/middle_side") : middleSideSingle;
             topPost = topPost == null ? model("double/top_post") : topPost;
             topSideDouble = topSideDouble == null ?
                     model("double/top_side") : topSideDouble;
             topSideSingle = topSideSingle == null ?
                     model("single/top_side") : topSideSingle;
             
             if (textures == null) {
                 
                 ImmutableList.Builder<ResourceLocation> builder =
                         ImmutableList.builder();
                 
                 for (IModel model : new IModel[] {bottomSideSingle,
                         bottomSideDouble, loneSideSingle, loneSideDouble,
                         middleSideSingle, middleSideDouble, topSideSingle,
                         topSideDouble, bottomPost, middlePost,
                         topPost, lonePost}) {
                     
                     builder.addAll(model.getTextures());
                 }
                 
                 textures = builder.build();
             }

            return new WallBrickDouble();
        }
    }
}
