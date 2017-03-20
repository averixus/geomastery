package com.jayavery.jjmod.init;

import com.jayavery.jjmod.render.tileentity.TileEntityBoxRenderer;
import com.jayavery.jjmod.tileentities.TEBasket;
import com.jayavery.jjmod.tileentities.TEBeam;
import com.jayavery.jjmod.tileentities.TEBed;
import com.jayavery.jjmod.tileentities.TEBox;
import com.jayavery.jjmod.tileentities.TECarcass;
import com.jayavery.jjmod.tileentities.TECraftingArmourer;
import com.jayavery.jjmod.tileentities.TECraftingCandlemaker;
import com.jayavery.jjmod.tileentities.TECraftingForge;
import com.jayavery.jjmod.tileentities.TECraftingKnapping;
import com.jayavery.jjmod.tileentities.TECraftingMason;
import com.jayavery.jjmod.tileentities.TECraftingSawpit;
import com.jayavery.jjmod.tileentities.TECraftingTextiles;
import com.jayavery.jjmod.tileentities.TECraftingWoodworking;
import com.jayavery.jjmod.tileentities.TEDrying;
import com.jayavery.jjmod.tileentities.TEFurnaceCampfire;
import com.jayavery.jjmod.tileentities.TEFurnaceClay;
import com.jayavery.jjmod.tileentities.TEFurnacePotfire;
import com.jayavery.jjmod.tileentities.TEFurnaceStone;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTileEntities {

    public static void init() {
        
        GameRegistry.registerTileEntity(TEFurnaceCampfire.class,
                "furnace_campfire");
        GameRegistry.registerTileEntity(TEFurnacePotfire.class,
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
        GameRegistry.registerTileEntity(TECraftingCandlemaker.class,
                "crafting_candlemaker");
        GameRegistry.registerTileEntity(TECraftingKnapping.class,
                "crafting_knapping");
        GameRegistry.registerTileEntity(TECraftingTextiles.class,
                "crafting_textiles");

        GameRegistry.registerTileEntity(TEDrying.class, "drying");
        GameRegistry.registerTileEntity(TEBox.class, "box");
        GameRegistry.registerTileEntity(TEBasket.class, "basket");
        GameRegistry.registerTileEntity(TEBed.class, "bed");
        GameRegistry.registerTileEntity(TEBeam.class, "beam");
        GameRegistry.registerTileEntity(TECarcass.class, "carcass");
    }
    
    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        
        ClientRegistry.bindTileEntitySpecialRenderer(TEBox.class,
                new TileEntityBoxRenderer());
    }
}
