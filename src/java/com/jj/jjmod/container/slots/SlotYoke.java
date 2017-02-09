package com.jj.jjmod.container.slots;

import com.jj.jjmod.capabilities.CapPlayer;
import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotYoke extends Slot {
    
    private ICapPlayer capability;
    private EntityPlayer player;
    
    public SlotYoke(EntityPlayer player, int x, int y) {
        
        super(null, 0, x, y);
        this.player = player;
        this.capability = player.getCapability(CapPlayer.CAP_PLAYER, null);
    }
    
    @Override
    public int getSlotStackLimit() {
        
        return 1;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return stack.getItem() == ModItems.yoke;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.capability.getYoke();
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.capability.putYoke(stack);
        this.onSlotChanged();
    }
    
    @Override
    public void onSlotChanged() {
        
        ((ContainerInventory) this.player.inventoryContainer).refresh();
    }
    
    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack) {
        
        this.onSlotChanged();
        return stack;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.capability.getYoke().splitStack(amount);
    }
    
    @Override
    public boolean isHere(IInventory inv, int slot) {
        
        return false;
    }
    
    @Override
    public boolean isSameInventory(Slot other) {
        
        return false;
    }
}
