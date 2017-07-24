/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container.slots;

import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.tileentities.TEStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Slot for storage tileentities. */
public class SlotStorage extends Slot {
    
    /** The tileentity of this slot. */
    private final TEStorage storage;
    /** The index of this slot in the storage inventory. */
    private final int index;
    
    public SlotStorage(TEStorage storage, int index, int x, int y) {
        
        super(null, 0, x, y);
        this.storage = storage;
        this.index = index;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
         return !GeoBlocks.isOffhandOnly(stack.getItem());
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.storage.getInventory(this.index);
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.storage.setInventory(stack, this.index);
    }
    
    @Override
    public void onSlotChanged() {
        
        this.storage.markDirty();
    }
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.storage.getInventory(this.index).splitStack(amount);
    }

    @Override
    public boolean isHere(IInventory inv, int slot) {
        
        return false;
    }
    
    @Override
    public boolean isSameInventory(Slot slot) {
        
        return false;
    }
}
