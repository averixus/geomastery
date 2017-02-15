package com.jj.jjmod.init;

import com.jj.jjmod.render.tileentity.TileEntityBoxRenderer;
import com.jj.jjmod.tileentities.TEBeam;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.tileentities.TEBox;
import com.jj.jjmod.tileentities.TECarcass;
import com.jj.jjmod.tileentities.TECraftingArmourer;
import com.jj.jjmod.tileentities.TECraftingForge;
import com.jj.jjmod.tileentities.TECraftingMason;
import com.jj.jjmod.tileentities.TECraftingSawpit;
import com.jj.jjmod.tileentities.TECraftingWoodworking;
import com.jj.jjmod.tileentities.TEDrying;
import com.jj.jjmod.tileentities.TEFurnaceCampfire;
import com.jj.jjmod.tileentities.TEFurnaceClay;
import com.jj.jjmod.tileentities.TEFurnaceCookfire;
import com.jj.jjmod.tileentities.TEFurnaceStone;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void init() {
        
        GameRegistry.registerTileEntity(TEFurnaceCampfire.class,
                "furnace_campfire");
        GameRegistry.registerTileEntity(TEFurnaceCookfire.class,
                "furnace_potfire");
        GameRegistry.registerTileEntity(TEFurnaceClay.class, "furnace_clay");
        GameRegistry.registerTileEntity(TEFurnaceStone.class,
                "furnace_stone");

        GameRegistry.registerTileEntity(TECraftingMason.class,
                "crafting_mason");
        GameRegistry.registerTileEntity(TECraftingForge.class,
                "crafting_forge");
        GameRegistry.registerTileEntity(TECraftingWoodworking.class,
                "crafting_woodworking");
        GameRegistry.registerTileEntity(TECraftingSawpit.class,
                "crafting_sawpit");
        GameRegistry.registerTileEntity(TECraftingArmourer.class,
                "crafting_armourer");

        GameRegistry.registerTileEntity(TEDrying.class, "drying");
        
        GameRegistry.registerTileEntity(TEBox.class, "box");
        
        GameRegistry.registerTileEntity(TEBed.class, "bed");
        
        GameRegistry.registerTileEntity(TEBeam.class, "beam");
        
        GameRegistry.registerTileEntity(TECarcass.class, "carcass");
    }
    
    public static void preInitClient() {
        
        ClientRegistry.bindTileEntitySpecialRenderer(TEBox.class,
                new TileEntityBoxRenderer());
    }
}
