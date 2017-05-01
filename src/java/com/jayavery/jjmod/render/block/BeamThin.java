package com.jayavery.jjmod.render.block;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.blocks.BlockBeam;
import com.jayavery.jjmod.blocks.BlockBeam.EnumAxis;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.tileentities.TEBeam.EnumFloor;
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

public class BeamThin extends DelayedBakingAbstract {
    
    /** Cached map of quads by state. */
    protected static final Map<IBlockState, List<BakedQuad>> CACHE =
            Maps.newHashMap();
    
    /** Textures required to render this model. */
    protected static ImmutableList<ResourceLocation> textures;
    
    // Models for all possible parts
    protected static IModel thinMiddle;
    protected static IModel thinEnd;
    protected static IModel poleMiddle;
    protected static IModel poleEnd;
    protected static IModel poleLeft;
    protected static IModel poleRight;
    protected static IModel poleCornerRight;
    protected static IModel poleCornerLeft;
    protected static IModel woodMiddle;
    protected static IModel woodEnd;
    protected static IModel woodLeft;
    protected static IModel woodRight;
    protected static IModel woodCornerRight;
    protected static IModel woodCornerLeft;
    
    @Override
    public Collection<ResourceLocation> getTextures() {
        
        return textures;
    }
    
    /** @return A model whose location begins with "jjmod:block/beam/". */
    protected static IModel model(String beam) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                "jjmod:block/beam/" + beam),
                "Error loading model for delayed multipart!");
    }
    
    /** Sets up for delayed baking. */
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
            Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
                
        this.bakeInfo(format, textureGetter, "jjmod:blocks/complex/softwood1");
        return this;
    }
    
    /** Retrieves from cache or bakes as required. */
    @Override
    public List<BakedQuad> getQuads(IBlockState state,
            EnumFacing facing, long rand) {
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return Collections.emptyList();
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        if (CACHE.containsKey(state)) {
            
            return CACHE.get(state);
        }
        
        List<BakedQuad> result = Lists.newArrayList();
        
        EnumAxis axis = extState.getValue(BlockBeam.AXIS);
        
        if (axis == EnumAxis.NS) {
            
            this.addQuads(result, thinMiddle, 90, state, facing, rand);
            
        } else {
            
            this.addQuads(result, thinMiddle, state, facing, rand);
        }

        boolean front = extState.getValue(BlockBeam.FRONT);
        
        if (front) {
            
            if (axis == EnumAxis.NS) {
            
                this.addQuads(result, thinEnd, 270, state, facing, rand);
                
            } else {
                
                this.addQuads(result, thinEnd, state, facing, rand);
            }
        }
        
        boolean back = extState.getValue(BlockBeam.BACK);
        
        if (back) {
            
            if (axis == EnumAxis.NS) {
                
                this.addQuads(result, thinEnd, 90, state, facing, rand);
                
            } else {
                
                this.addQuads(result, thinEnd, 180, state, facing, rand);
            }
        }
        
        EnumFloor floor = extState.getValue(BlockBeam.FLOOR);
        
        if (floor == EnumFloor.NONE) {
            
            return result;
        }
        
        boolean left = extState.getValue(BlockBeam.LEFT);
        boolean right = extState.getValue(BlockBeam.RIGHT);
        boolean bl = extState.getValue(BlockBeam.BL);
        boolean br = extState.getValue(BlockBeam.BR);
        boolean fl = extState.getValue(BlockBeam.FL);
        boolean fr = extState.getValue(BlockBeam.FR);
        
        if (floor == EnumFloor.POLE) {
            
            if (axis == EnumAxis.NS) {
            
                this.addQuads(result, poleMiddle, 90, state, facing, rand);
                
                if (front) {
                    
                    this.addQuads(result, poleEnd, 90, state, facing, rand);
                }
                
                if (back) {
                    
                    this.addQuads(result, poleEnd, 270, state, facing, rand);
                }
                
                if (left) {
                    
                    this.addQuads(result, poleRight, 90, state, facing, rand);
                }
                
                if (right) {
                    
                    this.addQuads(result, poleLeft, 90, state, facing, rand);
                }
                
                if (bl) {
                    
                    this.addQuads(result, poleCornerLeft, 270,
                            state, facing, rand);
                }
                
                if (br) {
                    
                    this.addQuads(result, poleCornerRight, 270,
                            state, facing, rand);
                }
                
                if (fl) {
                    
                    this.addQuads(result, poleCornerRight, 90,
                            state, facing, rand);
                }
                
                if (fr) {
                    
                    this.addQuads(result, poleCornerLeft, 90,
                            state, facing, rand);
                }
                
            } else {
                
                this.addQuads(result, poleMiddle, state, facing, rand);
                
                if (front) {
                    
                    this.addQuads(result, poleEnd, 180, state, facing, rand);
                }
                
                if (back) {
                    
                    this.addQuads(result, poleEnd, state, facing, rand);
                }
                
                if (left) {
                    
                    this.addQuads(result, poleLeft, state, facing, rand);
                }
                
                if (right) {
                    
                    this.addQuads(result, poleRight, state, facing, rand);
                }
                
                if (fl) {
                    
                    this.addQuads(result, poleCornerRight, 180,
                            state, facing, rand);
                }
                
                if (fr) {
                    
                    this.addQuads(result, poleCornerLeft, 180,
                            state, facing, rand);
                }
                
                if (bl) {
                    
                    this.addQuads(result, poleCornerLeft, state, facing, rand);
                }
                
                if (br) {
                    
                    this.addQuads(result, poleCornerRight, state, facing, rand);
                }
            }
        }
        
        if (floor == EnumFloor.WOOD) {
            
            if (axis == EnumAxis.NS) {
            
                this.addQuads(result, woodMiddle, 90, state, facing, rand);
                
                if (front) {
                    
                    this.addQuads(result, woodEnd, 90, state, facing, rand);
                }
                
                if (back) {
                    
                    this.addQuads(result, woodEnd, 270,
                            state, facing, rand);
                }
                
                if (left) {
                    
                    this.addQuads(result, woodRight, 90,
                            state, facing, rand);
                }
                
                if (right) {
                    
                    this.addQuads(result, woodLeft, 90,
                            state, facing, rand);
                }
                
                if (bl) {
                    
                    this.addQuads(result, woodCornerLeft, 270,
                            state, facing, rand);
                }
                
                if (br) {
                    
                    this.addQuads(result, woodCornerRight, 270,
                            state, facing, rand);
                }
                
                if (fl) {
                    
                    this.addQuads(result, woodCornerRight, 90,
                            state, facing, rand);
                }
                
                if (fr) {
                    
                    this.addQuads(result, woodCornerLeft, 90,
                            state, facing, rand);
                }
                
            } else {
                
                this.addQuads(result, woodMiddle, state, facing, rand);
                
                if (front) {
                    
                    this.addQuads(result, woodEnd, 180,
                            state, facing, rand);
                }
                
                if (back) {
                    
                    this.addQuads(result, woodEnd, state, facing, rand);
                }
                
                if (left) {
                    
                    this.addQuads(result, woodLeft, state, facing, rand);
                }
                
                if (right) {
                    
                    this.addQuads(result, woodRight, state, facing, rand);
                }
                
                if (fl) {
                    
                    this.addQuads(result, woodCornerRight, 180,
                            state, facing, rand);
                }
                
                if (fr) {
                    
                    this.addQuads(result, woodCornerLeft, 180,
                            state, facing, rand);
                }
                
                if (bl) {
                    
                    this.addQuads(result, woodCornerLeft,
                            state, facing, rand);
                }
                
                if (br) {
                    
                    this.addQuads(result, woodCornerRight,
                            state, facing, rand);
                }
            }
        }
        
        CACHE.put(state, result);
        return result;
    }
    
    /** Loader for delayed baked model. */
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
                    ModBlocks.beamThin.getRegistryName().equals(loc);
        }
        
        /** Loads dependent models and provides the delayed baking model. */
        @Override
        public IModel loadModel(ResourceLocation modelLocation)
                throws Exception {
            
            thinMiddle = thinMiddle == null ? model("thin/middle") : thinMiddle;
            thinEnd = thinEnd == null ? model("thin/end") : thinEnd;
            poleMiddle = poleMiddle == null ? model("pole/middle") : poleMiddle;
            poleEnd = poleEnd == null ? model("pole/end") : poleEnd;
            poleLeft = poleLeft == null ? model("pole/left") : poleLeft;
            poleRight = poleRight == null ? model("pole/right") : poleRight;
            poleCornerRight = poleCornerRight == null ?
                    model("pole/corner_right") : poleCornerRight;
            poleCornerLeft = poleCornerLeft == null ?
                    model("pole/corner_left") : poleCornerLeft;
            woodMiddle = woodMiddle == null ? model("wood/middle") : woodMiddle;
            woodEnd = woodEnd == null ? model("wood/end") : woodEnd;
            woodLeft = woodLeft == null ? model("wood/left") : woodLeft;
            woodRight = woodRight == null ? model("wood/right") : woodRight;
            woodCornerRight = woodCornerRight == null ?
                    model("wood/corner_right") : woodCornerRight;
            woodCornerLeft = woodCornerLeft == null ?
                    model("wood/corner_left") : woodCornerLeft;
        
            if (textures == null) {
                
                ImmutableList.Builder<ResourceLocation> builder =
                        ImmutableList.builder();
                
                for (IModel model : new IModel[] {thinMiddle, thinEnd,
                        poleMiddle, poleEnd, poleLeft, poleRight,
                        poleCornerRight, poleCornerLeft, woodMiddle, woodEnd,
                        woodLeft, woodRight, woodCornerLeft, woodCornerRight}) {
                    
                    builder.addAll(model.getTextures());
                }
                
                textures = builder.build();
            }  
            
            return new BeamThin();
        }
    }
}
