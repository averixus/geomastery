/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container.slots;

import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFurnaceOutput extends Slot {
    
    /** The furnace this slot draws inventory from. */
    private final TEFurnaceAbstract<?> furnace;
    /** The index of this slot in the furnace outputs list. */
    private final int index;
    
    public SlotFurnaceOutput(TEFurnaceAbstract<?> furnace, int index,
            int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.furnace = furnace;
        this.index = index;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return false;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.furnace.getOutput(this.index);
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.furnace.setOutput(stack, this.index);
    }
    
    @Override
    public void onSlotChanged() {}
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.furnace.getOutput(this.index).splitStack(amount);
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
