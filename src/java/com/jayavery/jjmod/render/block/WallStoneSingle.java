package com.jayavery.jjmod.render.block;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.blocks.BlockWallComplex;
import com.jayavery.jjmod.blocks.BlockWallComplex.EnumConnection;
import com.jayavery.jjmod.blocks.BlockWallComplex.EnumPosition;
import com.jayavery.jjmod.blocks.BlockWallLog.EnumStraight;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.utilities.UnlistedPropertyEnum;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Model to wrap delayed baking of multipart. */
public class WallStoneSingle extends DelayedBakingAbstract {
    
    /** Model names for wall connection types. */
    protected static final Map<EnumConnection, IModel> connections =
            Maps.newEnumMap(EnumConnection.class);
    
    /** Model names for wall post positions. */
    protected static final Map<EnumPosition, IModel> posts =
            Maps.newEnumMap(EnumPosition.class);
    
    /** Model names for straight wall positions. */
    protected static final Map<EnumPosition, IModel> straights =
            Maps.newEnumMap(EnumPosition.class);
    
    /** Rotation angles for straight walls. */
    protected static final Map<EnumStraight, Integer> axes =
            Maps.newEnumMap(EnumStraight.class);
    
    /** Rotation angles for wall connection properties. */
    protected static final Map<UnlistedPropertyEnum<EnumConnection>, Integer>
            properties = Maps.newHashMap();
    
    // Models for all possible parts
    protected static IModel bottomPost;
    protected static IModel bottomSide;
    protected static IModel lonePost;
    protected static IModel loneSide;
    protected static IModel middlePost;
    protected static IModel middleSide;
    protected static IModel topPost;
    protected static IModel topSide;
    protected static IModel topStraight;
    protected static IModel loneStraight;
    
    public WallStoneSingle() {
        
        super("jjmod:blocks/complex/stone1",
                ModBlocks.wallStoneSingle.getRegistryName());
    }
    
    /** Loads dependent parts and provides the delayed baking model. */
    @Override
    public IModel loadModel(ResourceLocation modelLocation)
            throws Exception {
        
        // Load part models
        
         bottomPost = model("bottom_post");
         bottomSide = model("bottom_side");
         lonePost = model("lone_post");
         loneSide = model("lone_side");
         middlePost = model("middle_post");
         middleSide = model("middle_side");
         topPost = model("top_post");
         topSide = model("top_side");
         topStraight = model("top_straight");
         loneStraight = model("lone_straight");

         // Prepare texture dependencies list
         
         for (IModel model : new IModel[] {bottomSide, loneSide, middleSide,
                 topSide, bottomPost, middlePost, topPost, lonePost,
                 topStraight, loneStraight}) {
             
             this.textures.addAll(model.getTextures());
         }
         
         // Set up multipart mappings
         
         connections.put(EnumConnection.BOTTOM_SINGLE, bottomSide);
         connections.put(EnumConnection.LONE_SINGLE, loneSide);
         connections.put(EnumConnection.MIDDLE_SINGLE, middleSide);
         connections.put(EnumConnection.TOP_SINGLE, topSide);
         
         posts.put(EnumPosition.BOTTOM, bottomPost);
         posts.put(EnumPosition.LONE, lonePost);
         posts.put(EnumPosition.MIDDLE, middlePost);
         posts.put(EnumPosition.TOP, topPost);
         
         straights.put(EnumPosition.TOP, topStraight);
         straights.put(EnumPosition.LONE, loneStraight);
         
         axes.put(EnumStraight.NS, 0);
         axes.put(EnumStraight.EW, 90);
         
         properties.put(BlockWallComplex.NORTH, 180);
         properties.put(BlockWallComplex.EAST, 270);
         properties.put(BlockWallComplex.SOUTH, 0);
         properties.put(BlockWallComplex.WEST, 90);

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
        ImmutableMap<IUnlistedProperty<?>,Optional<?>> extProps =
                extState.getUnlistedProperties();
        
        if (this.cache.containsKey(extProps)) {

            return this.cache.get(extProps);
        }

        List<BakedQuad> result = Lists.newArrayList();
        
        EnumStraight straight = extState.getValue(BlockWallComplex.STRAIGHT);
        EnumPosition position = extState.getValue(BlockWallComplex.POSITION);
        
        if (straight != EnumStraight.NO) {
            
            // Straight
            
            this.addQuads(result, straights.get(position), axes.get(straight),
                    extState, side, rand);
            
        } else {
            
            // Post
            
            this.addQuads(result, posts.get(position), state, side, rand);
                    
            // Sides
            
            for (Entry<UnlistedPropertyEnum<EnumConnection>, Integer> entry :
                    properties.entrySet()) {
                
                EnumConnection connection = extState.getValue(entry.getKey());
                
                if (connection != EnumConnection.NONE) {
                
                    this.addQuads(result, connections.get(connection),
                            entry.getValue(), state, side, rand);
                }
            }
        }

        this.cache.put(extProps, result);
        return result;
    }
    
    /** @return A model whose location begins with
     * "jjmod:block/wall_stone_single/". */
    protected static IModel model(String wallStoneSingle) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                "jjmod:block/wall_stone_single/" + wallStoneSingle),
                "Error loading model for delayed multipart single stone wall!");
    }
}
