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
import com.jayavery.jjmod.blocks.BlockWall;
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
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Delayed baking cached multipart block model. */
public abstract class DelayedBakingAbstract
        implements IBakedModel, IModel, ICustomModelLoader {

    /** Cached model quads for states. */
    protected final Map<Pair<ImmutableMap<IProperty<?>,Comparable<?>>,
            ImmutableMap<IUnlistedProperty<?>,Optional<?>>>,
            List<BakedQuad>> cache = Maps.newHashMap();
    /** VertexFormat from the original {@code bake}. */
    protected VertexFormat format;
    /** Texture getter from the original {@code bake}. */
    protected Function<ResourceLocation, TextureAtlasSprite> textureGetter;
    /** Registry name of this model's block. */
    protected final ResourceLocation block;
    /** Textures this model needs to load. */
    protected final List<ResourceLocation> textures = Lists.newArrayList();
    
    public DelayedBakingAbstract(ResourceLocation block) {
        
        this.block = block;
    }
    
    /** @return Make the list of quads for this state. */
    protected abstract List<BakedQuad> getAllQuads(IBlockState state,
            EnumFacing side, long rand);
    
    @Override
    public List<BakedQuad> getQuads(IBlockState state,
            EnumFacing side, long rand) {
        
        if (!(state instanceof IExtendedBlockState) || 
                !(state.getBlock() instanceof BlockWall)) {
            
            return Collections.emptyList();
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        ImmutableMap<IUnlistedProperty<?>,Optional<?>> extProps =
                extState.getUnlistedProperties();
        ImmutableMap<IProperty<?>,Comparable<?>> props =
                extState.getProperties();
        Pair<ImmutableMap<IProperty<?>,Comparable<?>>,
                ImmutableMap<IUnlistedProperty<?>,Optional<?>>>
                pair = Pair.of(props, extProps);
        
        if (this.cache.containsKey(pair)) {
            
            return this.cache.get(pair);
        }

        List<BakedQuad> result = this.getAllQuads(state, side, rand);
        this.cache.put(pair, result);
        return result;
    }
    
    /** Adds the quads for the given model with given rotation to the list. */
    protected void addQuads(List<BakedQuad> list, IModel model,
            int yRotate, IBlockState state, EnumFacing side, long rand) {
        
        if (model == null) {
            
            return;
        }

        TRSRTransformation transform = new TRSRTransformation(ModelRotation
                .getModelRotation(0, yRotate));
        IBakedModel baked = model.bake(transform, this.format,
                this.textureGetter);
        list.addAll(baked.getQuads(state, side, rand));
    }
    
    /** Convenience for adding quads of model with 0 rotation. */
    protected void addQuads(List<BakedQuad> list, IModel model,
            IBlockState state, EnumFacing side, long rand) {
        
        this.addQuads(list, model, 0, state, side, rand);
    }
    
    @Override
    public Collection<ResourceLocation> getTextures() {

        return this.textures;
    }
    
    /** Stores information from {@code bake} for later use. */
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
            Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        
        this.format = format;
        this.textureGetter = textureGetter;
        return this;
    }
    
    @Override
    public void onResourceManagerReload(IResourceManager rm) {
        
        this.cache.clear();
    }
    
    @Override
    public boolean accepts(ResourceLocation loc) {
        
        return loc instanceof ModelResourceLocation &&
                ((ModelResourceLocation) loc).getVariant()
                .contains("delayedbake") &&  this.block.equals(loc);
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture() {

        return Minecraft.getMinecraft().getTextureMapBlocks()
                .getAtlasSprite(this.textures.get(0).toString());
    }
        
    @Override
    public boolean isAmbientOcclusion() {

        return false;
    }

    @Override
    public boolean isGui3d() {

        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {

        return false;
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {

        return ItemCameraTransforms.DEFAULT;
    }
    
    @Override
    public ItemOverrideList getOverrides() {

        return ItemOverrideList.NONE;
    }
    
    @Override
    public IModelState getDefaultState() {

        return TRSRTransformation.identity();
    }
    
    @Override
    public Collection<ResourceLocation> getDependencies() {

        return ImmutableList.of();
    }
}
