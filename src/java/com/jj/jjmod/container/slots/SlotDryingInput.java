package com.jj.jjmod.container.slots;

import com.jj.jjmod.tileentities.TEDrying;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Container inventory slot for drying rack inputs. */
public class SlotDryingInput extends Slot {
    
    /** The drying rack this slot draws inventory from. */
    private final TEDrying drying;
    /** The index of this slot in the drying rack input list. */
    private final int index;

    public SlotDryingInput(TEDrying drying, int index, int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.drying = drying;
        this.index = index;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return !this.drying.recipes.getCookingResult(stack,
                this.drying.getWorld()).isEmpty();
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.drying.getInput(this.index);
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.drying.setInput(stack, this.index);
    }
    
    @Override
    public void onSlotChanged() {

        this.drying.sort();
    }
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.drying.getInput(this.index).splitStack(amount);
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
