package com.jayavery.jjmod.render.block;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
import net.minecraftforge.common.property.IUnlistedProperty;

/** Model to wrap delayed baking of multipart. */
public class WallStoneDouble extends DelayedBakingAbstract {

    /** Model names for wall connection types. */
    protected static final Map<EnumConnection, IModel> connections =
            Maps.newEnumMap(EnumConnection.class);
    
    /** Model names for wall post positions. */
    protected static final Map<EnumPosition, IModel> positions =
            Maps.newEnumMap(EnumPosition.class);
    
    /** Rotation angles for wall connection properties. */
    protected static final Map<UnlistedPropertyEnum<EnumConnection>, Integer>
            properties = Maps.newHashMap();
    
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
    
    public WallStoneDouble() {
        
        super("jjmod:blocks/complex/stone1",
                ModBlocks.wallStoneDouble.getRegistryName());
    }
    
    /** Loads dependent parts and provides the delayed baking model. */
    @Override
    public IModel loadModel(ResourceLocation rl) throws Exception {
        
        // Load part models
        
        bottomPost = model("double/bottom_post");
        bottomSideDouble = model("double/bottom_side");
        bottomSideSingle = model("single/bottom_side");
        lonePost = model("double/lone_post");
        loneSideDouble = model("double/lone_side");
        loneSideSingle = model("single/lone_side");
        middlePost = model("double/middle_post");
        middleSideDouble = model("double/middle_side");
        middleSideSingle = model("single/middle_side");
        topPost = model("double/top_post");
        topSideDouble = model("double/top_side");
        topSideSingle = model("single/top_side");
        
        // Prepare texture dependencies list
    
        for (IModel model : new IModel[] {bottomSideSingle, bottomSideDouble,
                loneSideSingle, loneSideDouble, middleSideSingle,
                middleSideDouble, topSideSingle, topSideDouble, bottomPost,
                middlePost, topPost, lonePost}) {
         
            this.textures.addAll(model.getTextures());
        }
        
        // Set up multipart mappings
     
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
        
        return this;
    }
    
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side,
            long rand) {
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return Collections.emptyList();
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        ImmutableMap<IUnlistedProperty<?>,Optional<?>> extProps = extState.getUnlistedProperties();
        
        if (this.cache.containsKey(extProps)) {
            
            return this.cache.get(extProps);
        }

        List<BakedQuad> result = Lists.newArrayList();
        
        // Post
        
        this.addQuads(result, positions.get(extState.getValue(BlockWallComplex
                .POSITION)), state, side, rand);
        
        // Sides
        
        for (Entry<UnlistedPropertyEnum<EnumConnection>, Integer> entry :
                properties.entrySet()) {
            
            EnumConnection connection = extState.getValue(entry.getKey());
            
            if (connection != EnumConnection.NONE) {
            
                this.addQuads(result, connections.get(connection),
                        entry.getValue(), state, side, rand);
            }
        }

        this.cache.put(extProps, result);
        return result;
    }
    
    /** @return A model whose location begins with "jjmod:block/wall_stone_". */
    protected static IModel model(String wallStone) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                "jjmod:block/wall_stone_" + wallStone),
                "Error loading model for delayed multipart double stone wall!");
    }
}
