/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container.slots;

import jayavery.geomastery.tileentities.TECompost;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Input slot for compost heap. */
public class SlotCompostInput extends Slot {

    /** The compost heap this slot belongs to. */
    private final TECompost compost;
    
    public SlotCompostInput(TECompost compost, int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.compost = compost;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return TECompost.isNitrogen(stack) ||
                TECompost.isCarbon(stack);
    }
    
    @Override
    public ItemStack getStack() {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.compost.addInput(stack);
    }
    
    @Override
    public void onSlotChanged() {}
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return ItemStack.EMPTY;
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
