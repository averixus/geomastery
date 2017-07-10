/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import jayavery.geomastery.container.ContainerCompost;
import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerDrying;
import jayavery.geomastery.container.ContainerFurnaceClay;
import jayavery.geomastery.container.ContainerFurnaceSingle;
import jayavery.geomastery.container.ContainerFurnaceStone;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.container.ContainerStorage;
import jayavery.geomastery.gui.GuiBasket;
import jayavery.geomastery.gui.GuiBox;
import jayavery.geomastery.gui.GuiChest;
import jayavery.geomastery.gui.GuiCompost;
import jayavery.geomastery.gui.GuiCrafting;
import jayavery.geomastery.gui.GuiDrying;
import jayavery.geomastery.gui.GuiFurnace;
import jayavery.geomastery.gui.GuiInventory;
import jayavery.geomastery.tileentities.TECompost;
import jayavery.geomastery.tileentities.TECraftingAbstract;
import jayavery.geomastery.tileentities.TECraftingArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge;
import jayavery.geomastery.tileentities.TECraftingKnapping;
import jayavery.geomastery.tileentities.TECraftingMason;
import jayavery.geomastery.tileentities.TECraftingSawpit;
import jayavery.geomastery.tileentities.TECraftingTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking;
import jayavery.geomastery.tileentities.TEDrying;
import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import jayavery.geomastery.tileentities.TEFurnaceCampfire;
import jayavery.geomastery.tileentities.TEFurnaceClay;
import jayavery.geomastery.tileentities.TEFurnacePotfire;
import jayavery.geomastery.tileentities.TEFurnaceStone;
import jayavery.geomastery.tileentities.TEStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/** Handler for GUI and container opening. */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GuiList.KNAPPING.ordinal()) {

            TECraftingKnapping crafting = (TECraftingKnapping) te;
            return new ContainerCrafting.Knapping(player, pos, crafting);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            TECraftingWoodworking crafting = (TECraftingWoodworking) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingWoodworking) world.getTileEntity(master);
            return new ContainerCrafting.Woodworking(player, master, crafting);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            TECraftingTextiles crafting = (TECraftingTextiles) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingTextiles) world.getTileEntity(master);
            return new ContainerCrafting.Textiles(player, master, crafting);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            TECraftingCandlemaker crafting = (TECraftingCandlemaker) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingCandlemaker) world.getTileEntity(master);
            return new ContainerCrafting.Candlemaker(player, master, crafting);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            TECraftingForge crafting = (TECraftingForge) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingForge) world.getTileEntity(master);
            return new ContainerCrafting.Forge(player, master, crafting);
        }

        if (ID == GuiList.MASON.ordinal()) {

            TECraftingMason crafting = (TECraftingMason) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingMason) world.getTileEntity(master);
            return new ContainerCrafting.Mason(player, master, crafting);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            TECraftingSawpit crafting = (TECraftingSawpit) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingSawpit) world.getTileEntity(master);
            return new ContainerCrafting.Sawpit(player, master, crafting);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            TECraftingArmourer crafting = (TECraftingArmourer) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingArmourer) world.getTileEntity(master);
            return new ContainerCrafting.Armourer(player, master, crafting);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new ContainerFurnaceSingle.Camp(player,
                    (TEFurnaceAbstract<?>) te, pos);
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new ContainerFurnaceSingle.Pot(player,
                    (TEFurnaceAbstract<?>) te, pos);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new ContainerFurnaceClay(player, furnace, master);
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new ContainerFurnaceStone(player, furnace, master);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new ContainerDrying(player, (TEDrying) te, pos);
        }
        
        if (ID == GuiList.CHEST.ordinal()) {
            
            return new ContainerStorage.Chest(player, pos, (TEStorage) te);
        }
        
        if (ID == GuiList.BOX.ordinal()) {
            
            return new ContainerStorage.Box(player, pos, (TEStorage) te);
        }
        
        if (ID == GuiList.BASKET.ordinal()) {
            
            return new ContainerStorage.Basket(player, pos,(TEStorage) te);
        }
        
        if (ID == GuiList.COMPOST.ordinal()) {
            
            return new ContainerCompost(player, pos, (TECompost) te);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GuiList.KNAPPING.ordinal()) {

            TECraftingKnapping crafting = (TECraftingKnapping) te;
            return new GuiCrafting(new ContainerCrafting.Knapping(player,
                    pos, crafting), GuiList.KNAPPING.name);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            TECraftingWoodworking crafting = (TECraftingWoodworking) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingWoodworking) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Woodworking(player,
                    pos, crafting), GuiList.WOODWORKING.name);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            TECraftingTextiles crafting = (TECraftingTextiles) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingTextiles) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Textiles(player,
                    pos, crafting), GuiList.TEXTILES.name);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            TECraftingCandlemaker crafting = (TECraftingCandlemaker) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingCandlemaker) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Candlemaker(player,
                    pos, crafting), GuiList.CANDLEMAKER.name);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            TECraftingForge crafting = (TECraftingForge) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingForge) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Forge(player,
                    pos, crafting), GuiList.FORGE.name);
        }

        if (ID == GuiList.MASON.ordinal()) {

            TECraftingMason crafting = (TECraftingMason) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingMason) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Mason(player,
                    pos, crafting), GuiList.MASON.name);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            TECraftingArmourer crafting = (TECraftingArmourer) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingArmourer) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Armourer(player,
                    pos, crafting), GuiList.ARMOURER.name);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            TECraftingSawpit crafting = (TECraftingSawpit) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingSawpit) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Sawpit(player,
                    pos, crafting), GuiList.SAWPIT.name);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle.Camp(player,
                    (TEFurnaceCampfire) te, pos), GuiList.CAMPFIRE.name);
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle.Pot(player,
                    (TEFurnacePotfire) te, pos), GuiList.POTFIRE.name);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new GuiFurnace(new ContainerFurnaceClay(player,
                    furnace, master), GuiList.CLAY.name);
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new GuiFurnace(new ContainerFurnaceStone(player,
                    furnace, master), GuiList.STONE.name);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new GuiDrying(new ContainerDrying(player,
                    (TEDrying) te, pos));
        }
        
        if (ID == GuiList.CHEST.ordinal()) {
            
            return new GuiChest(new ContainerStorage.Chest(player,
                    pos, (TEStorage) te));
        }
        
        if (ID == GuiList.BOX.ordinal()) {
            
            return new GuiBox(new ContainerStorage.Box(player,
                    pos, (TEStorage) te));
        }
        
        if (ID == GuiList.BASKET.ordinal()) {
            
            return new GuiBasket(new ContainerStorage.Basket(player,
                    pos, (TEStorage) te));
        }
        
        if (ID == GuiList.COMPOST.ordinal()) {
            
            return new GuiCompost(new ContainerCompost(player,
                    pos, (TECompost) te));
        }

        return null;
    }

    /** Gui types for fixed index and name. */
    public static enum GuiList {

        KNAPPING("Knapping Block"),
        WOODWORKING("Woodworking Bench"),
        TEXTILES("Textiles Table"),
        CANDLEMAKER("Candlemaker's Bench"),
        FORGE("Forge"),
        MASON("Mason's Workshop"),
        SAWPIT("Sawpit"),
        ARMOURER("Armourer's Workshop"),
        CAMPFIRE("Campfire"),
        POTFIRE("Campfire with Pot"),
        CLAY("Clay Oven"),
        STONE("Stone Furnace"),
        DRYING("Drying Rack"),
        CHEST("Chest"),
        BOX("Box"),
        BASKET("Basket"),
        COMPOST("Compost Heap");

        public final String name;

        private GuiList(String name) {

            this.name = name;
        }
    }
}
