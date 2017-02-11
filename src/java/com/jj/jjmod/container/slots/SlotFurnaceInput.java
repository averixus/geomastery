package com.jj.jjmod.container.slots;

import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFurnaceInput extends Slot {

    private TEFurnaceAbstract furnace;
    
    public SlotFurnaceInput(TEFurnaceAbstract furnace, int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.furnace = furnace;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return true;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.furnace.getInput();
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.furnace.setInput(stack);
    }
    
    @Override
    public void onSlotChanged() {}
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.furnace.getInput().splitStack(amount);
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
