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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == GuiList.KNAPPING.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.KNAPPING);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.WOODWORKING);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.TEXTILES);
        }

        if (ID == GuiList.CLAYWORKS.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.CLAYWORKS);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.CANDLEMAKER);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.FORGE);
        }

        if (ID == GuiList.MASON.ordinal()) {

            return new ContainerCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.MASON);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new ContainerFurnace(player, world, (IInventory) te);
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new ContainerFurnace(player, world, (IInventory) te);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            return new ContainerFurnace(player, world, (IInventory) te);
        }

        if (ID == GuiList.STONE.ordinal()) {

            return new ContainerFurnace(player, world, (IInventory) te);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new ContainerDrying(player, world, (IInventory) te);
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

        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == GuiList.KNAPPING.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.KNAPPING,
                    GuiList.KNAPPING.NAME);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.WOODWORKING,
                    GuiList.WOODWORKING.NAME);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.TEXTILES,
                    GuiList.TEXTILES.NAME);
        }

        if (ID == GuiList.CLAYWORKS.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.CLAYWORKS,
                    GuiList.CLAYWORKS.NAME);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.CANDLEMAKER,
                    GuiList.CANDLEMAKER.NAME);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.FORGE,
                    GuiList.FORGE.NAME);
        }

        if (ID == GuiList.MASON.ordinal()) {

            return new GuiCrafting(player, world,
                    new BlockPos(x, y, z), ModRecipes.MASON,
                    GuiList.MASON.NAME);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new GuiFurnace(player, world,
                    (IInventory) te, GuiList.CAMPFIRE.NAME);
        }

        if (ID == GuiList.POTFIRE.ordinal()) {

            return new GuiFurnace(player, world, (IInventory) te,
                    GuiList.POTFIRE.NAME);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            return new GuiFurnace(player, world,
                    (IInventory) te, GuiList.CLAY.NAME);
        }

        if (ID == GuiList.STONE.ordinal()) {

            return new GuiFurnace(player, world, (IInventory) te,
                    GuiList.STONE.NAME);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new GuiDrying(
                    new ContainerDrying(player, world, (IInventory) te));
        }

        if (ID == GuiList.INVENTORY.ordinal()) {

            return new GuiInventory(new ContainerInventory(player, world));
        }
        
        if (ID == GuiList.BOX.ordinal()) {
            
            return new GuiBox(new ContainerBox(player, world, (IInventory) te));
        }

        return null;
    }

    public static enum GuiList {

        KNAPPING("Knapping Block"),
        WOODWORKING("Woodworking Bench"),
        TEXTILES("Textiles Table"),
        CLAYWORKS("Clay Works"),
        CANDLEMAKER("Candlemaker's Bench"),
        FORGE("Forge"),
        MASON("Mason's Workshop"),
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
