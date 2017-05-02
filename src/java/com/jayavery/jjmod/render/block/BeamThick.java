package com.jayavery.jjmod.render.block;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.blocks.BlockBeam;
import com.jayavery.jjmod.blocks.BlockBeam.EnumAxis;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.tileentities.TEBeam.EnumFloor;
import com.jayavery.jjmod.utilities.Modeller;
import com.jayavery.jjmod.utilities.UnlistedPropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BeamThick extends DelayedBakingAbstract {
    
    // Models for all possible parts
    protected static IModel thickMiddle;
    protected static IModel thickEnd;
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
            Modeller>> middles = Maps.newEnumMap(EnumAxis.class);
    /** 4D map of axis -> floor -> property -> model part and rotation
     * for floor surrounds. */
    protected static Map<EnumAxis, Map<EnumFloor, Map<UnlistedPropertyBool,
            Modeller>>> surrounds = Maps.newEnumMap(EnumAxis.class);


    public BeamThick() {
        
        super("jjmod:blocks/complex/softwood1",
                ModBlocks.beamThick.getRegistryName());
    }

    @Override
    public IModel loadModel(ResourceLocation rl) throws Exception {
        
        // Load part models
        
        thickMiddle = model("thick/middle");
        thickEnd = model("thick/end");
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
                    
        for (IModel model : new IModel[] {thickMiddle, thickEnd,
                poleMiddle, poleEnd, poleLeft, poleRight,
                poleCornerRight, poleCornerLeft, woodMiddle, woodEnd,
                woodLeft, woodRight, woodCornerLeft, woodCornerRight}) {
            
            this.textures.addAll(model.getTextures());
        }
        
        // Set up multipart mappings
        
        beams.put(EnumAxis.NS, 90);
        beams.put(EnumAxis.EW, 0);
        
        Map<UnlistedPropertyBool, Integer> endsNS = Maps.newHashMap();
        endsNS.put(BlockBeam.FRONTBEAM, 90);
        endsNS.put(BlockBeam.BACKBEAM, 270);
        ends.put(EnumAxis.NS, endsNS);
        
        Map<UnlistedPropertyBool, Integer> endsEW = Maps.newHashMap();
        endsEW.put(BlockBeam.FRONTBEAM, 0);
        endsEW.put(BlockBeam.BACKBEAM, 180);
        ends.put(EnumAxis.EW, endsEW);
        
        Map<EnumFloor, Modeller> floorsNS = Maps.newEnumMap(EnumFloor.class);
        floorsNS.put(EnumFloor.POLE, new Modeller(poleMiddle, 90));
        floorsNS.put(EnumFloor.WOOD, new Modeller(woodMiddle, 90));
        middles.put(EnumAxis.NS, floorsNS);
        
        Map<EnumFloor, Modeller> floorsEW = Maps.newEnumMap(EnumFloor.class);
        floorsEW.put(EnumFloor.POLE, new Modeller(poleMiddle, 0));
        floorsEW.put(EnumFloor.WOOD, new Modeller(woodMiddle, 0));
        middles.put(EnumAxis.EW, floorsEW);
        
        Map<EnumFloor, Map<UnlistedPropertyBool, Modeller>>
                floorNSParts = Maps.newEnumMap(EnumFloor.class);
        
        Map<UnlistedPropertyBool, Modeller> poleNSParts = Maps.newHashMap();
        poleNSParts.put(BlockBeam.FRONT, new Modeller(poleEnd, 90));
        poleNSParts.put(BlockBeam.BACK, new Modeller(poleEnd, 270));
        poleNSParts.put(BlockBeam.LEFT, new Modeller(poleRight, 90));
        poleNSParts.put(BlockBeam.RIGHT, new Modeller(poleLeft, 90));
        poleNSParts.put(BlockBeam.BL, new Modeller(poleCornerLeft, 270));
        poleNSParts.put(BlockBeam.BR, new Modeller(poleCornerRight, 270));
        poleNSParts.put(BlockBeam.FL, new Modeller(poleCornerRight, 90));
        poleNSParts.put(BlockBeam.FR, new Modeller(poleCornerLeft, 90));
        floorNSParts.put(EnumFloor.POLE, poleNSParts);
        
        Map<UnlistedPropertyBool, Modeller> woodNSParts = Maps.newHashMap();
        woodNSParts.put(BlockBeam.FRONT, new Modeller(woodEnd, 90));
        woodNSParts.put(BlockBeam.BACK, new Modeller(woodEnd, 270));
        woodNSParts.put(BlockBeam.LEFT, new Modeller(woodLeft, 90));
        woodNSParts.put(BlockBeam.RIGHT, new Modeller(woodRight, 90));
        woodNSParts.put(BlockBeam.BL, new Modeller(woodCornerLeft, 270));
        woodNSParts.put(BlockBeam.BR, new Modeller(woodCornerRight, 270));
        woodNSParts.put(BlockBeam.FL, new Modeller(woodCornerRight, 90));
        woodNSParts.put(BlockBeam.FR, new Modeller(woodCornerLeft, 90));
        floorNSParts.put(EnumFloor.WOOD, woodNSParts);
        
        Map<EnumFloor, Map<UnlistedPropertyBool, Modeller>>
                floorEWParts = Maps.newEnumMap(EnumFloor.class);

        Map<UnlistedPropertyBool, Modeller> poleEWParts = Maps.newHashMap();
        poleEWParts.put(BlockBeam.FRONT, new Modeller(poleEnd, 180));
        poleEWParts.put(BlockBeam.BACK, new Modeller(poleEnd, 0));
        poleEWParts.put(BlockBeam.LEFT, new Modeller(poleLeft, 0));
        poleEWParts.put(BlockBeam.RIGHT, new Modeller(poleRight, 0));
        poleEWParts.put(BlockBeam.BL, new Modeller(poleCornerLeft, 0));
        poleEWParts.put(BlockBeam.BR, new Modeller(poleCornerRight, 0));
        poleEWParts.put(BlockBeam.FL, new Modeller(poleCornerRight, 180));
        poleEWParts.put(BlockBeam.FR, new Modeller(poleCornerLeft, 180));
        floorEWParts.put(EnumFloor.POLE, poleEWParts);
        
        Map<UnlistedPropertyBool, Modeller> woodEWParts = Maps.newHashMap();
        woodEWParts.put(BlockBeam.FRONT, new Modeller(woodEnd, 180));
        woodEWParts.put(BlockBeam.BACK, new Modeller(woodEnd, 0));
        woodEWParts.put(BlockBeam.LEFT, new Modeller(woodRight, 0));
        woodEWParts.put(BlockBeam.RIGHT, new Modeller(woodLeft, 0));
        woodEWParts.put(BlockBeam.BL, new Modeller(woodCornerLeft, 0));
        woodEWParts.put(BlockBeam.BR, new Modeller(woodCornerRight, 0));
        woodEWParts.put(BlockBeam.FL, new Modeller(woodCornerRight, 180));
        woodEWParts.put(BlockBeam.FR, new Modeller(woodCornerLeft, 180));
        floorEWParts.put(EnumFloor.WOOD, woodEWParts);
        
        surrounds.put(EnumAxis.NS, floorNSParts);
        surrounds.put(EnumAxis.EW, floorEWParts);
        
        return this;
    }
    
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing facing,
            long rand) {

        if (!(state instanceof IExtendedBlockState)) {
            
            return Collections.emptyList();
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        if (this.cache.containsKey(state)) {
            
            return this.cache.get(state);
        }
        
        List<BakedQuad> result = Lists.newArrayList();
        
        EnumAxis axis = extState.getValue(BlockBeam.AXIS);
        EnumFloor floor = extState.getValue(BlockBeam.FLOOR);
        
        // Beam middle
        
        this.addQuads(result, thickMiddle, beams.get(axis), state, facing, rand);
        
        // Beam ends
        
        for (Entry<UnlistedPropertyBool, Integer> entry :
                ends.get(axis).entrySet()) {
            
            if (extState.getValue(entry.getKey())) {
                
                this.addQuads(result, thickEnd, entry.getValue(),
                        state, facing, rand);
            }
        }
        
        if (floor == EnumFloor.NONE) {
            
            return result;
        }
        
        // Floor middle
        
        Modeller model = middles.get(axis).get(floor);
        this.addQuads(result, model.model(), model.rot(),
                extState, facing, rand);
        
        // Floor surrounds
        
        Map<UnlistedPropertyBool, Modeller> apply =
                surrounds.get(axis).get(floor);
        
        for (Entry<UnlistedPropertyBool, Modeller> entry :
                apply.entrySet()) {
            
            if (extState.getValue(entry.getKey())) {
                
                Modeller amodel = entry.getValue();
                this.addQuads(result, amodel.model(), amodel.rot(),
                        state, facing, rand);
            }
        }
        
        this.cache.put(state, result);
        return result;
    }
    
    /** @return A model whose location begins with "jjmod:block/beam/". */
    protected static IModel model(String beam) {
        
        return ModelLoaderRegistry.getModelOrLogError(new ResourceLocation(
                "jjmod:block/beam/" + beam),
                "Error loading model for delayed multipart thick beam!");
    }
}
