package jayavery.geomastery.main;

import jayavery.geomastery.container.ContainerBasket;
import jayavery.geomastery.container.ContainerBox;
import jayavery.geomastery.container.ContainerCompost;
import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerDrying;
import jayavery.geomastery.container.ContainerFurnaceClay;
import jayavery.geomastery.container.ContainerFurnaceSingle;
import jayavery.geomastery.container.ContainerFurnaceStone;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.gui.GuiBasket;
import jayavery.geomastery.gui.GuiBox;
import jayavery.geomastery.gui.GuiCompost;
import jayavery.geomastery.gui.GuiCrafting;
import jayavery.geomastery.gui.GuiDrying;
import jayavery.geomastery.gui.GuiFurnace;
import jayavery.geomastery.gui.GuiInventory;
import jayavery.geomastery.tileentities.TEBasket;
import jayavery.geomastery.tileentities.TEBox;
import jayavery.geomastery.tileentities.TECompost;
import jayavery.geomastery.tileentities.TECraftingAbstract;
import jayavery.geomastery.tileentities.TEDrying;
import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import jayavery.geomastery.tileentities.TEFurnaceCampfire;
import jayavery.geomastery.tileentities.TEFurnaceClay;
import jayavery.geomastery.tileentities.TEFurnacePotfire;
import jayavery.geomastery.tileentities.TEFurnaceStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;

/** Handler for Gui and Container opening. */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GuiList.KNAPPING.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            return new ContainerCrafting(player, world, pos,
                    GeoRecipes.KNAPPING, crafting);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.WOODWORKING, crafting);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.TEXTILES, crafting);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.CANDLEMAKER, crafting);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.FORGE, crafting);
        }

        if (ID == GuiList.MASON.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.MASON, crafting);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.SAWPIT, crafting);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new ContainerCrafting(player, world, master,
                    GeoRecipes.ARMOURER, crafting);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new ContainerFurnaceSingle(player, world,
                    (TEFurnaceAbstract<?>) te, pos);
        }

        if (ID == GuiList.COOKFIRE.ordinal()) {

            return new ContainerFurnaceSingle(player, world,
                    (TEFurnaceAbstract<?>) te, pos);
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
            
            return new ContainerBox(player, world, pos, (TEBox) te);
        }
        
        if (ID == GuiList.BASKET.ordinal()) {
            
            return new ContainerBasket(player, pos, world, ((TEBasket) te)
                    .getCapability(CapabilityItemHandler
                    .ITEM_HANDLER_CAPABILITY, null));
        }
        
        if (ID == GuiList.COMPOST.ordinal()) {
            
            return new ContainerCompost(player, world, pos, (TECompost) te);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player,
            World world, int x, int y, int z) {

        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GuiList.KNAPPING.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.KNAPPING, crafting), GuiList.KNAPPING.name);
        }

        if (ID == GuiList.WOODWORKING.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.WOODWORKING, crafting),
                    GuiList.WOODWORKING.name);
        }

        if (ID == GuiList.TEXTILES.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.TEXTILES, crafting), GuiList.TEXTILES.name);
        }

        if (ID == GuiList.CANDLEMAKER.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.CANDLEMAKER, crafting),
                    GuiList.CANDLEMAKER.name);
        }

        if (ID == GuiList.FORGE.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.FORGE, crafting), GuiList.FORGE.name);
        }

        if (ID == GuiList.MASON.ordinal()) {

            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.MASON, crafting), GuiList.MASON.name);
        }
        
        if (ID == GuiList.ARMOURER.ordinal()) {
            
            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.ARMOURER, crafting), GuiList.ARMOURER.name);
        }
        
        if (ID == GuiList.SAWPIT.ordinal()) {
            
            TECraftingAbstract<?> crafting = (TECraftingAbstract<?>) te;
            BlockPos master = crafting.getMaster();
            crafting = (TECraftingAbstract<?>) world.getTileEntity(master);
            return new GuiCrafting(new ContainerCrafting(player, world,
                    pos, GeoRecipes.SAWPIT, crafting), GuiList.SAWPIT.name);
        }

        if (ID == GuiList.CAMPFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle(player, world,
                    (TEFurnaceCampfire) te, pos), GuiList.CAMPFIRE.name);
        }

        if (ID == GuiList.COOKFIRE.ordinal()) {

            return new GuiFurnace(new ContainerFurnaceSingle(player, world,
                    (TEFurnacePotfire) te, pos), GuiList.COOKFIRE.name);
        }

        if (ID == GuiList.CLAY.ordinal()) {

            TEFurnaceClay furnace = (TEFurnaceClay) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceClay) world.getTileEntity(master);
            return new GuiFurnace(new ContainerFurnaceClay(player, world,
                    furnace, master), GuiList.CLAY.name);
        }

        if (ID == GuiList.STONE.ordinal()) {

            TEFurnaceStone furnace = (TEFurnaceStone) te;
            BlockPos master = furnace.getMaster();
            furnace = (TEFurnaceStone) world.getTileEntity(master);
            return new GuiFurnace(new ContainerFurnaceStone(player, world,
                    furnace, master), GuiList.STONE.name);
        }

        if (ID == GuiList.DRYING.ordinal()) {

            return new GuiDrying(new ContainerDrying(player,
                    world, (TEDrying) te, pos));
        }

        if (ID == GuiList.INVENTORY.ordinal()) {

            return new GuiInventory(new ContainerInventory(player, world));
        }
        
        if (ID == GuiList.BOX.ordinal()) {
            
            return new GuiBox(new ContainerBox(player, world, pos, (TEBox) te));
        }
        
        if (ID == GuiList.BASKET.ordinal()) {
            
            return new GuiBasket(new ContainerBasket(player, pos,
                    world, ((TEBasket) te).getCapability(CapabilityItemHandler
                    .ITEM_HANDLER_CAPABILITY, null)));
        }
        
        if (ID == GuiList.COMPOST.ordinal()) {
            
            return new GuiCompost(new ContainerCompost(player,
                    world, pos, (TECompost) te));
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
        COOKFIRE("Campfire with Pot"),
        CLAY("Clay Oven"),
        STONE("Stone Furnace"),
        DRYING("Drying Rack"),
        INVENTORY("Inventory"),
        BOX("Box"),
        BASKET("Basket"),
        COMPOST("Compost Heap");

        public final String name;

        private GuiList(String name) {

            this.name = name;
        }
    }
}
