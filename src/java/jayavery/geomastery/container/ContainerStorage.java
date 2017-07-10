/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import jayavery.geomastery.container.slots.SlotStorage;
import jayavery.geomastery.tileentities.TEStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/** Abstract superclass for storage containers. */
public class ContainerStorage extends ContainerAbstract {
    
    /** Index of the start of the player hotbar. */
    private static final int HOT_START = 0;
    
    /** Y-position of the start of the storage grid. */
    private final int storageY;
    /** Index of the end of the player inventory. */
    private final int invEnd;
    /** Index of the start of the storage inventory. */
    private final int storageStart;
    /** Index of the end of the storage inventory. */
    private final int storageEnd; 
    /** TileEntity of the storage. */
    private final TEStorage storage;
    /** Position of the storage block. */
    private final BlockPos pos;

    public ContainerStorage(EntityPlayer player, BlockPos pos,
            TEStorage storage, int storageY) {
        
        super(player);
        this.storage = storage;
        this.pos = pos;
        this.storageY = storageY;
        this.storage.open();
        
        // Inventory
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Storage inventory
        for (int row = 0; row < this.storage.getRows(); row++) {
            
            for (int col = 0; col < 9; col++) {
                
                this.addSlotToContainer(new SlotStorage(this.storage,
                        col + (row * ROW_LENGTH), getInvX(col),
                        this.storageY + (row * SLOT_SIZE)));
            }
        }
        
        // Container indices
        this.invEnd = HOT_START + ROW_LENGTH + invIndex;
        this.storageStart = this.invEnd + 1;
        this.storageEnd = this.invEnd + this.storage.getSlots();
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            
            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();
            
            if (index >= HOT_START && index <= this.invEnd) {
                
                if (!this.mergeItemStack(slotStack,
                        this.storageStart, this.storageEnd + 1, false)) {
                    
                    return ItemStack.EMPTY;
                }
                
            } else if (index >= this.storageStart && index <= this.storageEnd) {
                
                if (!this.mergeItemStack(slotStack,
                        HOT_START, this.invEnd + 1, false)) {
                    
                    return ItemStack.EMPTY;
                }
            }
            
            if (slotStack.isEmpty()) {

                slot.putStack(ItemStack.EMPTY);
                
            } else {
                
                slot.onSlotChanged();
            }
        }
        
        return result;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return player.getDistanceSq(this.pos.getX() + 0.5,
                this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
    }
    
    @Override
    public void onContainerClosed(EntityPlayer player) {
        
        super.onContainerClosed(player);
        this.storage.close();
    }
    
    /** Basket implementation. */
    public static class Basket extends ContainerStorage {
        
        public Basket(EntityPlayer player, BlockPos pos, TEStorage storage) {
            
            super(player, pos, storage, 36);
        }
    }
    
    /** Box implementation. */
    public static class Box extends ContainerStorage {
        
        public Box(EntityPlayer player, BlockPos pos, TEStorage storage) {
            
            super(player, pos, storage, 26);
        }
    }
    
    /** Chest implementation. */
    public static class Chest extends ContainerStorage {
        
        public Chest(EntityPlayer player, BlockPos pos, TEStorage storage) {
            
            super(player, pos, storage, 18);
        }
    }
}
