package com.jj.jjmod.main;

import com.jj.jjmod.container.ContainerBox;
import com.jj.jjmod.container.ContainerCrafting;
import com.jj.jjmod.container.ContainerDrying;
import com.jj.jjmod.container.ContainerFurnace;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.gui.GuiBox;
import com.jj.jjmod.gui.GuiCrafting;
import com.jj.jjmod.gui.GuiDrying;
import com.jj.jjmod.gui.GuiFurnace;
import com.jj.jjmod.gui.GuiInventory;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.tileentities.TEDrying;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import com.jj.jjmod.tileentities.TEFurnaceClay;
import com.jj.jjmod.tileentities.TEFurnaceStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/** Handler for Gui and Container opening. */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GuiList.KNAPPING.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.KNAPPING);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.WOODWORKING);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.TEXTILES);
        }

        if (ID == GuiList.CLAYWORKS.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.CLAYWORKS);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.CANDLEMAKER);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.FORGE);
        }

        if (ID == GuiList.MASON.ordinal()) {

            return new ContainerCrafting(player, world,
                    pos, ModRecipes.MASON);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            return new ContainerCrafting(player, world,
                    pos, ModRecipes.SAWPIT);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            return new ContainerCrafting(player, world,
                    pos, ModRecipes.ARMOURER);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new ContainerFurnace(player, world, (TEFurnaceAbstract) te, pos);
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new ContainerFurnace(player, world, (TEFurnaceAbstract) te, pos);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new ContainerFurnace(player, world, furnace, master);
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new ContainerFurnace(player, world, furnace, master);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new ContainerDrying(player, world, (TEDrying) te, pos);
        }

        if (ID == GuiList.INVENTORY.ordinal()) {

            return new ContainerInventory(player, world);
        }
        
        if (ID == GuiList.BOX.ordinal()) {
            
            return new ContainerBox(player, world, (IInventory) te);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GuiList.KNAPPING.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.KNAPPING,
                    GuiList.KNAPPING.NAME);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.WOODWORKING,
                    GuiList.WOODWORKING.NAME);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.TEXTILES,
                    GuiList.TEXTILES.NAME);
        }

        if (ID == GuiList.CLAYWORKS.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.CLAYWORKS,
                    GuiList.CLAYWORKS.NAME);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.CANDLEMAKER,
                    GuiList.CANDLEMAKER.NAME);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.FORGE,
                    GuiList.FORGE.NAME);
        }

        if (ID == GuiList.MASON.ordinal()) {

            return new GuiCrafting(player, world,
                    pos, ModRecipes.MASON,
                    GuiList.MASON.NAME);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            return new GuiCrafting(player, world,
                    pos, ModRecipes.ARMOURER,
                    GuiList.ARMOURER.NAME);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            return new GuiCrafting(player, world,
                    pos, ModRecipes.SAWPIT,
                    GuiList.SAWPIT.NAME);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new GuiFurnace(player, world,
                    (TEFurnaceAbstract) te, pos, GuiList.CAMPFIRE.NAME);
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new GuiFurnace(player, world, (TEFurnaceAbstract) te,
                    pos, GuiList.POTFIRE.NAME);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new GuiFurnace(player, world, furnace,
                    master, GuiList.CLAY.NAME);
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new GuiFurnace(player, world, furnace,
                    master, GuiList.STONE.NAME);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new GuiDrying(
                    new ContainerDrying(player, world, (TEDrying) te, pos));
        }

        if (ID == GuiList.INVENTORY.ordinal()) {

            return new GuiInventory(new ContainerInventory(player, world));
        }
        
        if (ID == GuiList.BOX.ordinal()) {
            
            return new GuiBox(new ContainerBox(player, world, (IInventory) te));
        }

        return null;
    }

    /** Gui types for fixed index and name. */
    public static enum GuiList {

        KNAPPING("Knapping Block"),
        WOODWORKING("Woodworking Bench"),
        TEXTILES("Textiles Table"),
        CLAYWORKS("Clay Works"),
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
        INVENTORY("Inventory"),
        BOX("Box");

        public String NAME;

        private GuiList(String name) {

            this.NAME = name;
        }
    }
}
