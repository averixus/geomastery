package jayavery.geomastery.render.block;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jayavery.geomastery.blocks.BlockBeam;
import jayavery.geomastery.blocks.BlockBeam.EnumAxis;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEBeam.EnumFloor;
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
public class BeamThin extends DelayedBakingAbstract {
    
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
    
    /** Map of axis -> rotation for main beam. */
    protected static Map<EnumAxis, Integer> beams =
            Maps.newEnumMap(EnumAxis.class);
    /** 3D map of axis -> property -> rotation for beam ends. */
    protected static Map<EnumAxis, Map<UnlistedPropertyBool, Integer>> ends =
            Maps.newEnumMap(EnumAxis.class);
    /** 3D map of axis -> floor -> model part and rotation for floor middles. */
    protected static Map<EnumAxis, Map<EnumFloor,
            Pair<IModel, Integer>>> middles = Maps.newEnumMap(EnumAxis.class);
    /** 4D map of axis -> floor -> property -> model part and rotation
     * for floor surrounds. */
    protected static Map<EnumAxis, Map<EnumFloor, Map<UnlistedPropertyBool,
            Pair<IModel, Integer>>>> surrounds = Maps.newEnumMap(EnumAxis.class);

    public BeamThin() {
        
        super(GeoBlocks.BEAM_THIN.getRegistryName());
    }

    @Override
    public IModel loadModel(ResourceLocation rl) throws Exception {
        
        // Load part models
        
        thinMiddle = model("thin/middle");
        thinEnd = model("thin/end");
        poleMiddle = model("pole/middle");
        poleEnd = model("pole/end");
        poleLeft = model("pole/left");
        poleRight = model("pole/right");
        poleCornerRight = model("pole/corner_right");
        poleCornerLeft = model("pole/corner_left");
        woodMiddle = model("wood/middle");
        woodEnd = model("wood/end");
        woodLeft = model("wood/left");
        woodRight = model("wood/right");
        woodCornerRight = model("wood/corner_right");
        woodCornerLeft = model("wood/corner_left");
        
        // Prepare texture dependencies list
                    
        for (IModel model : new IModel[] {thinMiddle, thinEnd,
                poleMiddle, poleEnd, poleLeft, poleRight,
                poleCornerRight, poleCornerLeft, woodMiddle, woodEnd,
                woodLeft, woodRight, woodCornerLeft, woodCornerRight}) {
            
            this.textures.addAll(model.getTextures());
        }
        
        // Set up multipart mappings
        
        beams.put(EnumAxis.NS, 90);
        beams.put(EnumAxis.EW, 0);
        
        Map<UnlistedPropertyBool, Integer> endsNS = Maps.newHashMap();
        endsNS.put(BlockBeam.FRONTBEAM, 270);
        endsNS.put(BlockBeam.BACKBEAM, 90);
        ends.put(EnumAxis.NS, endsNS);
        
        Map<UnlistedPropertyBool, Integer> endsEW = Maps.newHashMap();
        endsEW.put(BlockBeam.FRONTBEAM, 0);
        endsEW.put(BlockBeam.BACKBEAM, 180);
        ends.put(EnumAxis.EW, endsEW);
        
        Map<EnumFloor, Pair<IModel, Integer>> floorsNS =
                Maps.newEnumMap(EnumFloor.class);
        floorsNS.put(EnumFloor.POLE, Pair.of(poleMiddle, 90));
        floorsNS.put(EnumFloor.WOOD, Pair.of(woodMiddle, 90));
        middles.put(EnumAxis.NS, floorsNS);
        
        Map<EnumFloor, Pair<IModel, Integer>> floorsEW =
                Maps.newEnumMap(EnumFloor.class);
        floorsEW.put(EnumFloor.POLE, Pair.of(poleMiddle, 0));
        floorsEW.put(EnumFloor.WOOD, Pair.of(woodMiddle, 0));
        middles.put(EnumAxis.EW, floorsEW);
        
        Map<EnumFloor, Map<UnlistedPropertyBool, Pair<IModel, Integer>>>
                floorNSParts = Maps.newEnumMap(EnumFloor.class);
        
        Map<UnlistedPropertyBool, Pair<IModel, Integer>> poleNSParts =
                Maps.newHashMap();
        poleNSParts.put(BlockBeam.FRONT, Pair.of(poleEnd, 90));
        poleNSParts.put(BlockBeam.BACK, Pair.of(poleEnd, 270));
        poleNSParts.put(BlockBeam.LEFT, Pair.of(poleRight, 90));
        poleNSParts.put(BlockBeam.RIGHT, Pair.of(poleLeft, 90));
        poleNSParts.put(BlockBeam.BL, Pair.of(poleCornerLeft, 270));
        poleNSParts.put(BlockBeam.BR, Pair.of(poleCornerRight, 270));
        poleNSParts.put(BlockBeam.FL, Pair.of(poleCornerRight, 90));
        poleNSParts.put(BlockBeam.FR, Pair.of(poleCornerLeft, 90));
        floorNSParts.put(EnumFloor.POLE, poleNSParts);
        
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
        floorNSParts.put(EnumFloor.WOOD, woodNSParts);
        
        Map<EnumFloor, Map<UnlistedPropertyBool, Pair<IModel, Integer>>>
                floorEWParts = Maps.newEnumMap(EnumFloor.class);

        Map<UnlistedPropertyBool, Pair<IModel, Integer>> poleEWParts =
                Maps.newHashMap();
        poleEWParts.put(BlockBeam.FRONT, Pair.of(poleEnd, 180));
        poleEWParts.put(BlockBeam.BACK, Pair.of(poleEnd, 0));
        poleEWParts.put(BlockBeam.LEFT, Pair.of(poleLeft, 0));
        poleEWParts.put(BlockBeam.RIGHT, Pair.of(poleRight, 0));
        poleEWParts.put(BlockBeam.BL, Pair.of(poleCornerLeft, 0));
        poleEWParts.put(BlockBeam.BR, Pair.of(poleCornerRight, 0));
        poleEWParts.put(BlockBeam.FL, Pair.of(poleCornerRight, 180));
        poleEWParts.put(BlockBeam.FR, Pair.of(poleCornerLeft, 180));
        floorEWParts.put(EnumFloor.POLE, poleEWParts);
        
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
        floorEWParts.put(EnumFloor.WOOD, woodEWParts);
        
        surrounds.put(EnumAxis.NS, floorNSParts);
        surrounds.put(EnumAxis.EW, floorEWParts);
        
        return this;
    }
    
    @Override
    protected List<BakedQuad> getAllQuads(IBlockState state,
            EnumFacing facing, long rand) {

        IExtendedBlockState extState = (IExtendedBlockState) state;
        List<BakedQuad> result = Lists.newArrayList();
        
        EnumAxis axis = extState.getValue(BlockBeam.AXIS);
        EnumFloor floor = extState.getValue(BlockBeam.FLOOR);
        
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
        
        if (floor == EnumFloor.NONE) {
            
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
