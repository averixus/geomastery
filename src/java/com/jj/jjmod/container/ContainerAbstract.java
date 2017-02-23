package com.jj.jjmod.container;

import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.container.slots.SlotInventory;
import com.jj.jjmod.init.ModCaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Abstract superclass of Containers, with utilities for ICapPlayer. */ 
public abstract class ContainerAbstract extends Container {

    /** Size of a slot in pixels. */
    protected static final int SLOT_SIZE = 18;
    /** Y-position of the start of hotbar. */
    protected static final int HOT_Y = 142;
    /** Number of slots in a row. */
    protected static final int ROW_LENGTH = 9;
    /** Y-position of the start of the inventory grid. */
    protected static final int INV_Y = 84;
    /** X-position of the start of the inventory grid. */
    protected static final int INV_X = 8;

    /** The player who owns this container. */
    protected EntityPlayer player;
    /** The player capability of this container. */
    public ICapPlayer capability;
    /** The player inventory of this container. */
    protected InventoryPlayer playerInv;
    /** The world of this container. */
    protected World world;

    public ContainerAbstract(EntityPlayer player, World world) {

        this.player = player;
        this.capability = player
                .getCapability(ModCaps.CAP_PLAYER, null);
        this.playerInv = player.inventory;
        this.world = world;

    }

    /** Makes an InventoryCrafting of the given size, and builds grid of slots
     * starting at the given co-ordinates.
     * @return The new InventoryCrafting. */
    protected InventoryCrafting buildCraftMatrix(int columns, int rows,
            int startX, int startY) {

        InventoryCrafting craftMatrix =
                new InventoryCrafting(this, columns, rows);
        
        for (int j = 0; j < rows; j++) {

            for (int i = 0; i < columns; i++) {
                
                this.addSlotToContainer(new Slot(craftMatrix,
                        j * columns + i, startX + (i * SLOT_SIZE),
                        startY + (j * SLOT_SIZE)));
            }
        }

        return craftMatrix;
    }

    /** Builds a row of inventory slots for the hotbar. */
    protected void buildHotbar() {

        for (int m = 0; m < ROW_LENGTH; m++) {

            this.addSlotToContainer(new SlotInventory(this.playerInv,
                    m, getInvX(m), HOT_Y));
        }
    }
    
    /** Builds a grid of inventory slots of size defined by
     * the player capability.
     * @return The difference between the index of the first main inventory
     * slot, and the last total inventory slot:
     * -1 if there are 0 inventory rows. */
    protected int buildInvgrid() { 
        
        int invIndex = -1;

        if (this.capability.getInventoryRows() == 0) {
            
            return invIndex;
        }

        for (int k = 0; k < this.capability.getInventoryRows(); k++) {

            for (int l = 0; l < ROW_LENGTH; l++) {

                this.addSlotToContainer(new SlotInventory(
                        this.playerInv, ++invIndex + ROW_LENGTH,
                        getInvX(l), getInvY(invertRowIndex(k))));
            }
        }
        
        return invIndex;
    }

    /** @return The x co-ordinate for an inventory slot of this index (0-8). */
    protected static int getInvX(int index) {

        return INV_X + index * SLOT_SIZE;
    }

    /** @return The y co-ordinate for an inventory slot of this row (0-2). */
    protected static int getInvY(int index) {

        return INV_Y + index * SLOT_SIZE;
    }
    
    /** Inverts the given row index so that rows are built from bottom to top.
     * @return The inverted row index (0-2). */
    protected static int invertRowIndex(int index) {
        
        return index == 0 ? 2 : index == 2 ? 0 : index;
    }
}
