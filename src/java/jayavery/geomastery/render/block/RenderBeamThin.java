/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.block;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jayavery.geomastery.blocks.BlockBeam;
import jayavery.geomastery.blocks.BlockBeam.EBeamAxis;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEBeam.ETypeFloor;
import jayavery.geomastery.utilities.UnlistedPropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Renderer for thin beams. */
@SideOnly(Side.CLIENT)
public class RenderBeamThin extends RenderDelayedBakingAbstract {
    
    // Models for all possible parts
    protected static IModel thinMiddle;
    protected static IModel thinEnd;
    protected static IModel woodMiddle;
    protected static IModel woodEnd;
    protected static IModel woodLeft;
    protected static IModel woodRight;
    protected static IModel woodCornerRight;
    protected static IModel woodCornerLeft;
    
    /** Map of axis -> rotation for main beam. */
    protected static Map<EBeamAxis, Integer> beams = Maps.newEnumMap(EBeamAxis.class);
    /** 3D map of axis -> property -> rotation for beam ends. */
    protected static Map<EBeamAxis, Map<UnlistedPropertyBool, Integer>> ends = Maps.newEnumMap(EBeamAxis.class);
    /** 3D map of axis -> floor -> model part and rotation for floor middles. */
    protected static Map<EBeamAxis, Map<ETypeFloor, Pair<IModel, Integer>>> middles = Maps.newEnumMap(EBeamAxis.class);
    /** 4D map of axis -> floor -> property -> model part and rotation for floor surrounds. */
    protected static Map<EBeamAxis, Map<ETypeFloor, Map<UnlistedPropertyBool, Pair<IModel, Integer>>>> surrounds = Maps.newEnumMap(EBeamAxis.class);

    public RenderBeamThin() {
        
        super(GeoBlocks.BEAM_THIN.getRegistryName());
    }

    @Override
    public IModel loadModel(ResourceLocation rl) throws Exception {
        
        // Load part models
        
        thinMiddle = model("thin/middle");
        thinEnd = model("thin/end");
        woodMiddle = model("wood/middle");
        woodEnd = model("wood/end");
        woodLeft = model("wood/left");
        woodRight = model("wood/right");
        woodCornerRight = model("wood/corner_right");
        woodCornerLeft = model("wood/corner_left");
        
        // Prepare texture dependencies list
                    
        for (IModel model : new IModel[] {thinMiddle, thinEnd,
                woodMiddle, woodEnd,
                woodLeft, woodRight, woodCornerLeft, woodCornerRight}) {
            
            this.textures.addAll(model.getTextures());
        }
        
        // Set up multipart mappings
        
        beams.put(EBeamAxis.NS, 90);
        beams.put(EBeamAxis.EW, 0);
        
        Map<UnlistedPropertyBool, Integer> endsNS = Maps.newHashMap();
        endsNS.put(BlockBeam.FRONTBEAM, 270);
        endsNS.put(BlockBeam.BACKBEAM, 90);
        ends.put(EBeamAxis.NS, endsNS);
        
        Map<UnlistedPropertyBool, Integer> endsEW = Maps.newHashMap();
        endsEW.put(BlockBeam.FRONTBEAM, 0);
        endsEW.put(BlockBeam.BACKBEAM, 180);
        ends.put(EBeamAxis.EW, endsEW);
        
        Map<ETypeFloor, Pair<IModel, Integer>> floorsNS =
                Maps.newEnumMap(ETypeFloor.class);
        floorsNS.put(ETypeFloor.WOOD, Pair.of(woodMiddle, 90));
        middles.put(EBeamAxis.NS, floorsNS);
        
        Map<ETypeFloor, Pair<IModel, Integer>> floorsEW =
                Maps.newEnumMap(ETypeFloor.class);
        floorsEW.put(ETypeFloor.WOOD, Pair.of(woodMiddle, 0));
        middles.put(EBeamAxis.EW, floorsEW);
        
        Map<ETypeFloor, Map<UnlistedPropertyBool, Pair<IModel, Integer>>>
                floorNSParts = Maps.newEnumMap(ETypeFloor.class);
        
        Map<UnlistedPropertyBool, Pair<IModel, Integer>> woodNSParts =
                Maps.newHashMap();
        woodNSParts.put(BlockBeam.FRONT, Pair.of(woodEnd, 90));
        woodNSParts.put(BlockBeam.BACK, Pair.of(woodEnd, 270));
        woodNSParts.put(BlockBeam.LEFT, Pair.of(woodLeft, 90));
        woodNSParts.put(BlockBeam.RIGHT, Pair.of(woodRight, 90));
        woodNSParts.put(BlockBeam.BL, Pair.of(woodCornerLeft, 270));
        woodNSParts.put(BlockBeam.BR, Pair.of(woodCornerRight, 270));
        woodNSParts.put(BlockBeam.FL, Pair.of(woodCornerRight, 90));
        woodNSParts.put(BlockBeam.FR, Pair.of(woodCornerLeft, 90));
        floorNSParts.put(ETypeFloor.WOOD, woodNSParts);
        
        Map<ETypeFloor, Map<UnlistedPropertyBool, Pair<IModel, Integer>>>
                floorEWParts = Maps.newEnumMap(ETypeFloor.class);
        
        Map<UnlistedPropertyBool, Pair<IModel, Integer>> woodEWParts =
                Maps.newHashMap();
        woodEWParts.put(BlockBeam.FRONT, Pair.of(woodEnd, 180));
        woodEWParts.put(BlockBeam.BACK, Pair.of(woodEnd, 0));
        woodEWParts.put(BlockBeam.LEFT, Pair.of(woodRight, 0));
        woodEWParts.put(BlockBeam.RIGHT, Pair.of(woodLeft, 0));
        woodEWParts.put(BlockBeam.BL, Pair.of(woodCornerLeft, 0));
        woodEWParts.put(BlockBeam.BR, Pair.of(woodCornerRight, 0));
        woodEWParts.put(BlockBeam.FL, Pair.of(woodCornerRight, 180));
        woodEWParts.put(BlockBeam.FR, Pair.of(woodCornerLeft, 180));
        floorEWParts.put(ETypeFloor.WOOD, woodEWParts);
        
        surrounds.put(EBeamAxis.NS, floorNSParts);
        surrounds.put(EBeamAxis.EW, floorEWParts);
        
        return this;
    }
    
    @Override
    protected List<BakedQuad> getAllQuads(IBlockState state,
            EnumFacing facing, long rand) {

        IExtendedBlockState extState = (IExtendedBlockState) state;
        List<BakedQuad> result = Lists.newArrayList();
        
        EBeamAxis axis = extState.getValue(BlockBeam.AXIS);
        ETypeFloor floor = extState.getValue(BlockBeam.FLOOR);
        
        if (axis == null || floor == null) {
            
            return Collections.emptyList();
        }
        
        // Beam middle
        
        this.addQuads(result, thinMiddle, beams.get(axis), state, facing, rand);
        
        // Beam ends
        
        for (Entry<UnlistedPropertyBool, Integer> entry :
                ends.get(axis).entrySet()) {
            
            if (extState.getValue(entry.getKey())) {
                
                this.addQuads(result, thinEnd, entry.getValue(),
                        state, facing, rand);
            }
        }
        
        if (floor == ETypeFloor.NONE) {
            
            return result;
        }
        
        // Floor middle
        
        Pair<IModel, Integer> model = middles.get(axis).get(floor);
        this.addQuads(result, model.getLeft(), model.getRight(),
                extState, facing, rand);
        
        // Floor surrounds
        
        Map<UnlistedPropertyBool, Pair<IModel, Integer>> apply =
                surrounds.get(axis).get(floor);
        
        for (Entry<UnlistedPropertyBool, Pair<IModel, Integer>> entry :
                apply.entrySet()) {
            
            if (extState.getValue(entry.getKey())) {
                
                Pair<IModel, Integer> amodel = entry.getValue();
                this.addQuads(result, amodel.getLeft(), amodel.getRight(),
                        state, facing, rand);
            }
        }
        
        return result;
    }
    
    /** @return A model whose location begins with "jjmod:block/beam/". */
    protected static IModel model(String beam) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                Geomastery.MODID, "block/beam/" + beam),
                "Error loading model for delayed multipart thin beam!");
    }
}
