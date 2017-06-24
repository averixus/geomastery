/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import jayavery.geomastery.capabilities.ICapPlayer;
import jayavery.geomastery.container.slots.SlotIInventory;
import jayavery.geomastery.main.GeoCaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
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
    protected final EntityPlayer player;
    /** The player capability of this container. */
    public final ICapPlayer capability;
    /** The player inventory of this container. */
    protected final InventoryPlayer playerInv;
    /** The world of this container. */
    protected final World world;

    public ContainerAbstract(EntityPlayer player, World world) {

        this.player = player;
        this.capability = player
                .getCapability(GeoCaps.CAP_PLAYER, null);
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
        
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < columns; col++) {
                
                this.addSlotToContainer(new Slot(craftMatrix,
                        row * columns + col, startX + (col * SLOT_SIZE),
                        startY + (row * SLOT_SIZE)));
            }
        }

        return craftMatrix;
    }

    /** Builds a row of inventory slots for the hotbar. */
    protected void buildHotbar() {

        for (int slot = 0; slot < ROW_LENGTH; slot++) {

            this.addSlotToContainer(new SlotIInventory(this.playerInv,
                    slot, getInvX(slot), HOT_Y));
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

        for (int row = 0; row < this.capability.getInventoryRows(); row++) {

            for (int col = 0; col < ROW_LENGTH; col++) {

                this.addSlotToContainer(new SlotIInventory(
                        this.playerInv, ++invIndex + ROW_LENGTH,
                        getInvX(col), getInvY(invertRowIndex(row))));
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
