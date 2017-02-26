package com.jayavery.jjmod.main;

import com.jayavery.jjmod.container.ContainerBox;
import com.jayavery.jjmod.container.ContainerCrafting;
import com.jayavery.jjmod.container.ContainerDrying;
import com.jayavery.jjmod.container.ContainerFurnaceClay;
import com.jayavery.jjmod.container.ContainerFurnaceSingle;
import com.jayavery.jjmod.container.ContainerFurnaceStone;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.gui.GuiBox;
import com.jayavery.jjmod.gui.GuiCrafting;
import com.jayavery.jjmod.gui.GuiDrying;
import com.jayavery.jjmod.gui.GuiFurnace;
import com.jayavery.jjmod.gui.GuiInventory;
import com.jayavery.jjmod.init.ModRecipes;
import com.jayavery.jjmod.tileentities.TEDrying;
import com.jayavery.jjmod.tileentities.TEFurnaceAbstract;
import com.jayavery.jjmod.tileentities.TEFurnaceClay;
import com.jayavery.jjmod.tileentities.TEFurnaceCookfire;
import com.jayavery.jjmod.tileentities.TEFurnaceStone;
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

            return new ContainerFurnaceSingle(player, world,
                    (TEFurnaceAbstract) te, pos);
        }

        if (ID == GuiList.COOKFIRE.ordinal()) {

            return new ContainerFurnaceSingle(player, world,
                    (TEFurnaceAbstract) te, pos);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new ContainerFurnaceClay(player, world, furnace, master);
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new ContainerFurnaceStone(player, world, furnace, master);
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

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.KNAPPING), GuiList.KNAPPING.name);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.WOODWORKING), GuiList.WOODWORKING.name);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.TEXTILES), GuiList.TEXTILES.name);
        }

        if (ID == GuiList.CLAYWORKS.ordinal()) {

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.CLAYWORKS), GuiList.CLAYWORKS.name);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.CANDLEMAKER), GuiList.CANDLEMAKER.name);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.FORGE), GuiList.FORGE.name);
        }

        if (ID == GuiList.MASON.ordinal()) {

            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.MASON), GuiList.MASON.name);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.ARMOURER), GuiList.ARMOURER.name);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, ModRecipes.SAWPIT), GuiList.SAWPIT.name);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle(player, world,
                    (TEFurnaceAbstract) te, pos), GuiList.CAMPFIRE.name);
        }

        if (ID == GuiList.COOKFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle(player, world,
                    (TEFurnaceCookfire) te, pos), GuiList.COOKFIRE.name);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceClay(player, world,
                    (TEFurnaceClay) te, pos), GuiList.CLAY.name);
        }

        if (ID == GuiList.STONE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceStone(player, world,
                    (TEFurnaceStone) te, pos), GuiList.STONE.name);
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
        COOKFIRE("Campfire with Pot"),
        CLAY("Clay Oven"),
        STONE("Stone Furnace"),
        DRYING("Drying Rack"),
        INVENTORY("Inventory"),
        BOX("Box");

        public final String name;

        private GuiList(String name) {

            this.name = name;
        }
    }
}
