package com.jj.jjmod.container.slots;

import com.jj.jjmod.tileentities.TEDrying;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDryingOutput extends Slot {
    
    private TEDrying drying;

    public SlotDryingOutput(TEDrying drying, int xPos,
            int yPos) {
        
        super(null, 0, xPos, yPos);
        this.drying = drying;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return false;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.drying.getOutput();
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.drying.setOutput(stack);
    }
    
    @Override
    public void onSlotChanged() {}
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.drying.getOutput().splitStack(amount);
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

