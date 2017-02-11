package com.jj.jjmod.container.slots;

import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFurnaceOutput extends Slot {
    
    private TEFurnaceAbstract furnace;
    
    public SlotFurnaceOutput(TEFurnaceAbstract furnace, int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.furnace = furnace;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return false;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.furnace.getOutput();
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.furnace.setOutput(stack);
    }
    
    @Override
    public void onSlotChanged() {}
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.furnace.getOutput().splitStack(amount);
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
