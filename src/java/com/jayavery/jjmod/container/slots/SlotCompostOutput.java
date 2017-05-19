package com.jayavery.jjmod.container.slots;

import com.jayavery.jjmod.tileentities.TECompost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCompostOutput extends Slot {

    /** The compost heap this slot output is from. */
    private final TECompost compost;
    
    public SlotCompostOutput(TECompost compost, int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.compost = compost;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return false;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.compost.getOutput();
    }
    
    @Override
    public void putStack(ItemStack stack) {}
    
    @Override
    public void onSlotChanged() {}
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack) {
        
        this.onSlotChanged();
        this.compost.reduceOutput(stack.getCount());
        return stack;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.compost.getOutput().splitStack(amount);
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
