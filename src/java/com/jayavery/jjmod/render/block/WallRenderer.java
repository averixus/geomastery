package com.jayavery.jjmod.render.block;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.blocks.BlockBeam.EnumAxis;
import com.jayavery.jjmod.blocks.BlockWall;
import com.jayavery.jjmod.blocks.BlockWallComplex;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Abstract class for rendering walls which can connect to each other. */
public abstract class WallRenderer extends DelayedBakingAbstract {
   
    public WallRenderer(ResourceLocation block) {

        super(block);
    }

    /** @return Model for this wall's side with given properties. */
    protected abstract IModel getSideModel(boolean isTop, boolean isBottom);
    /** @return Offset rotation angle for this block's side models. */
    protected abstract int getSideAngle(EnumFacing facing);
    
    /** @return A model whose name begins with this block's name. */
    protected IModel model(String name) {
        
        String path = this.block.toString().replace("block_", "block/");
        return ModelLoaderRegistry.getModelOrLogError(
                new ResourceLocation(path + name),
                "Error loading model for delayed multipart wall "
                + path + name + "!"); 
    }
}
