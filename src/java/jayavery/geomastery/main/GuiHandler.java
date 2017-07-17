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
import jayavery.geomastery.container.ContainerStorage;
import jayavery.geomastery.gui.GuiBasket;
import jayavery.geomastery.gui.GuiBox;
import jayavery.geomastery.gui.GuiChest;
import jayavery.geomastery.gui.GuiCompost;
import jayavery.geomastery.gui.GuiCrafting;
import jayavery.geomastery.gui.GuiDrying;
import jayavery.geomastery.gui.GuiFurnace;
import jayavery.geomastery.tileentities.TECompostheap;
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
import jayavery.geomastery.tileentities.TEFurnaceClay;
import jayavery.geomastery.tileentities.TEFurnaceSingle.Campfire;
import jayavery.geomastery.tileentities.TEFurnaceSingle.Potfire;
import jayavery.geomastery.utilities.Lang;
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
        
        if (ID == GuiList.COMPOSTHEAP.ordinal()) {
            
            return new ContainerCompost(player, pos, (TECompostheap) te);
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
                    pos, crafting), GuiList.KNAPPING.title());
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            TECraftingWoodworking crafting = (TECraftingWoodworking) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingWoodworking) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Woodworking(player,
                    pos, crafting),
                    GuiList.WOODWORKING.title());
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            TECraftingTextiles crafting = (TECraftingTextiles) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingTextiles) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Textiles(player,
                    pos, crafting), GuiList.TEXTILES.title());
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            TECraftingCandlemaker crafting = (TECraftingCandlemaker) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingCandlemaker) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Candlemaker(player,
                    pos, crafting),
                    GuiList.CANDLEMAKER.title());
        }

        if (ID == GuiList.FORGE.ordinal()) {

            TECraftingForge crafting = (TECraftingForge) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingForge) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Forge(player,
                    pos, crafting), GuiList.FORGE.title());
        }

        if (ID == GuiList.MASON.ordinal()) {

            TECraftingMason crafting = (TECraftingMason) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingMason) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Mason(player,
                    pos, crafting), GuiList.MASON.title());
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            TECraftingArmourer crafting = (TECraftingArmourer) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingArmourer) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Armourer(player,
                    pos, crafting), GuiList.ARMOURER.title());
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            TECraftingSawpit crafting = (TECraftingSawpit) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingSawpit) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting.Sawpit(player,
                    pos, crafting), GuiList.SAWPIT.title());
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle.Camp(player,
                    (Campfire) te, pos),
                    GuiList.CAMPFIRE.title());
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle.Pot(player,
                    (Potfire) te, pos),
                    GuiList.POTFIRE.title());
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new GuiFurnace(new ContainerFurnaceClay(player,
                    furnace, master), GuiList.CLAY.title());
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new GuiFurnace(new ContainerFurnaceStone(player,
                    furnace, master), GuiList.STONE.title());
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
        
        if (ID == GuiList.COMPOSTHEAP.ordinal()) {
            
            return new GuiCompost(new ContainerCompost(player,
                    pos, (TECompostheap) te));
        }

        return null;
    }

    /** Gui types for fixed index and name. */
    public static enum GuiList {

        KNAPPING(Lang.CONTAINER_KNAPPING),
        WOODWORKING(Lang.CONTAINER_WOODOWORKING),
        TEXTILES(Lang.CONTAINER_TEXTILES),
        CANDLEMAKER(Lang.CONTAINER_CANDLEMAKER),
        FORGE(Lang.CONTAINER_FORGE),
        MASON(Lang.CONTAINER_MASON),
        SAWPIT(Lang.CONTAINER_SAWPIT),
        ARMOURER(Lang.CONTAINER_ARMOURER),
        CAMPFIRE(Lang.CONTAINER_CAMPFIRE),
        POTFIRE(Lang.CONTAINER_POTFIRE),
        CLAY(Lang.CONTAINER_CLAY),
        STONE(Lang.CONTAINER_STONE),
        DRYING(Lang.CONTAINER_DRYING),
        CHEST(Lang.CONTAINER_CHEST),
        BOX(Lang.CONTAINER_BOX),
        BASKET(Lang.CONTAINER_BASKET),
        COMPOSTHEAP(Lang.CONTAINER_COMPOSTHEAP);
        
        private final String title;
        
        private GuiList(String title) {
            
            this.title = title;
        }
        
        /** @return Unlocalised title for this container. */
        public String title() {
            
            return this.title;
        }
    }
}
