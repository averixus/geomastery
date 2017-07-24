/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import javax.annotation.Nullable;
import jayavery.geomastery.blocks.BlockContainerAbstract;
import jayavery.geomastery.container.slots.SlotCrafting;
import jayavery.geomastery.crafting.CraftingManager;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.tileentities.TECraftingAbstract;
import jayavery.geomastery.tileentities.TECraftingArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge;
import jayavery.geomastery.tileentities.TECraftingKnapping;
import jayavery.geomastery.tileentities.TECraftingMason;
import jayavery.geomastery.tileentities.TECraftingSawpit;
import jayavery.geomastery.tileentities.TECraftingTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for crafting devices. */
public abstract class ContainerCrafting extends ContainerAbstract {

    /** X-position of the output slot. */
    private static final int OUTPUT_X = 124;
    /** Y-position of the output slot. */
    private static final int OUTPUT_Y = 35;
    /** X-position of the start of the craft grid. */
    private static final int CRAFT_X = 30;
    /** Y-position of the start of the craft grid. */
    private static final int CRAFT_Y = 17;
    /** Columns of the craft grid. */
    private static final int CRAFT_COLS = 3;
    /** Rows of the craft grid. */
    private static final int CRAFT_ROWS = 3;

    /** Index of the start of player hotbar. */
    public static final int HOT_START = 0;
    /** Index of the end of player hotbar. */
    private static final int HOT_END = 8;
    /** Index of the start of player inventory. */
    private static final int INV_START = 9;

    /** Index of the end of player inventory. */
    public final int invEnd;
    /** Index of the start of craft grid. */
    public final int craftStart;
    /** Index of the end of craft grid. */
    public final int craftEnd;
    /** Index of the output slot. */
    private final int outputI;

    /** Inventory of the craft grid. */
    private final InventoryCrafting craftMatrix;
    /** Inventory of the craft output. */
    private final IInventory craftResult = new InventoryCraftResult();
    /** Crafting manager of this crafter. */
    private final CraftingManager craftManager;
    /** Position of this crafter. */    
    private final BlockPos pos;
    /** TileEntity of this crafter. */
    public final TECraftingAbstract<?> tile;

    public ContainerCrafting(EntityPlayer player, BlockPos pos,
            CraftingManager craftManager, TECraftingAbstract<?> tile) {

        super(player);
        this.craftManager = craftManager;
        this.pos = pos;
        this.tile = tile;

        // Inventory
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Craft grid
        this.craftMatrix = this.buildCraftMatrix(CRAFT_COLS, CRAFT_ROWS,
                CRAFT_X, CRAFT_Y);
        
        // Output slot
        this.addSlotToContainer(new SlotCrafting(this.player,
                this.craftMatrix, this.craftResult, OUTPUT_X,
                OUTPUT_Y, this.craftManager));

        // Container indices
        this.invEnd = INV_START + invIndex;
        this.craftStart = this.invEnd + 1;
        this.craftEnd = this.invEnd + this.craftMatrix.getSizeInventory();
        this.outputI = this.craftEnd + 1;
        
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {

        @Nullable
        ItemStack stack = this.craftManager
                .findMatchingRecipe(this.craftMatrix, this.world);
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            stack.getCapability(GeoCaps.CAP_DECAY, null)
                    .setBirthTime(this.world.getTotalWorldTime());
        }
        
        this.craftResult.setInventorySlotContents(0, stack);
    }
    
    @Override
    public ItemStack slotClick(int slot, int dragType,
            ClickType clickType, EntityPlayer player) {
        
        ItemStack result = super.slotClick(slot, dragType, clickType, player);
        this.onCraftMatrixChanged(this.craftMatrix);
        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {

        super.onContainerClosed(player);

        if (!this.world.isRemote) {

            for (int i = 0; i < this.craftMatrix.getSizeInventory(); i++) {

                ItemStack stack = this.craftMatrix.removeStackFromSlot(i);

                if (!stack.isEmpty()) {

                    player.dropItem(stack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        boolean correctBlock = this.world.getBlockState(this.pos)
                .getBlock() instanceof BlockContainerAbstract;

        if (correctBlock) {

            return player.getDistanceSq(this.pos.getX() + 0.5,
                    this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
        }

        return false;
    }

    @Override
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();

            if (index == this.outputI) {

                if (!GeoBlocks.isOffhandOnly(slotStack.getItem()) &&
                        !this.mergeItemStack(slotStack, HOT_START,
                        this.invEnd + 1, false)) {

                    return ItemStack.EMPTY;
                    
                } else {

                    slot.onTake(player, slotStack);
                }
                
                slot.onSlotChange(slotStack, result);

            } else if (index >= HOT_START && index <= HOT_END) {

                if (!this.mergeItemStack(slotStack, INV_START,
                        this.invEnd + 1, false)) {

                    return ItemStack.EMPTY;
                }

            } else if (index >= INV_START && index <= this.invEnd) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        HOT_END + 1, false)) {

                    return ItemStack.EMPTY;
                }

            } else if (index >= this.craftStart && index <= this.craftEnd) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        this.invEnd + 1, false)) {

                    return ItemStack.EMPTY;
                }

            }

            if (slotStack.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);
                
            } else {

                slot.onSlotChanged();
            }
        }

        return result;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot) {

        return slot.inventory != this.craftResult &&
                super.canMergeSlot(stack, slot);
    }
    
    /** Knapping block. */
    public static class Knapping extends ContainerCrafting {

        public Knapping(EntityPlayer player, BlockPos pos,
                TECraftingKnapping tile) {
            
            super(player, pos, GeoRecipes.KNAPPING, tile);
        }    
    }
    
    /** Candlemaker's bench. */
    public static class Candlemaker extends ContainerCrafting {
        
        public Candlemaker(EntityPlayer player, BlockPos pos,
                TECraftingCandlemaker tile) {
            
            super(player, pos, GeoRecipes.CANDLEMAKER, tile);
        }
    }
    
    /** Forge. */
    public static class Forge extends ContainerCrafting {
        
        public Forge(EntityPlayer player, BlockPos pos,
                TECraftingForge tile) {
            
            super(player, pos, GeoRecipes.FORGE, tile);
        }
    }
    
    /** Mason's workshop. */
    public static class Mason extends ContainerCrafting {
        
        public Mason(EntityPlayer player, BlockPos pos,
                TECraftingMason tile) {
            
            super(player, pos, GeoRecipes.MASON, tile);
        }
    }
    
    /** Sawpit. */
    public static class Sawpit extends ContainerCrafting {
        
        public Sawpit(EntityPlayer player, BlockPos pos,
                TECraftingSawpit tile) {
            
            super(player, pos, GeoRecipes.SAWPIT, tile);
        }
    }
    
    /** Textiles table. */
    public static class Textiles extends ContainerCrafting {
        
        public Textiles(EntityPlayer player, BlockPos pos,
                TECraftingTextiles tile) {
            
            super(player, pos, GeoRecipes.TEXTILES, tile);
        }
    }
    
    /** Woodworking bench. */
    public static class Woodworking extends ContainerCrafting {
        
        public Woodworking(EntityPlayer player, BlockPos pos,
                TECraftingWoodworking tile) {
            
            super(player, pos, GeoRecipes.WOODWORKING, tile);
        }
    }
    
    /** Armourer. */
    public static class Armourer extends ContainerCrafting {
        
        public Armourer(EntityPlayer player, BlockPos pos,
                TECraftingArmourer tile) {
        
            super(player, pos, GeoRecipes.ARMOURER, tile);
        }
    }
}
