package com.jj.jjmod.container.slots;

import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Container slot for backpack. */
public class SlotBackpack extends Slot {
    
    /** The player capability of this slot. */
    private ICapPlayer playerCap;
    /** The player who owns this slot. */
    private EntityPlayer player;
    
    public SlotBackpack(EntityPlayer player, int x, int y) {
        
        super(null, 0, x, y);
        this.player = player;
        this.playerCap = player.getCapability(ModCaps.CAP_PLAYER,
                null);
        this.backgroundName = "jjmod:gui/backpack_slot";
    }
    
    @Override
    public int getSlotStackLimit() {
        
        return 1;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return stack.getItem() == ModItems.backpack;
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.playerCap.getBackpack();
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.playerCap.putBackpack(stack);
        this.onSlotChanged();
    }
    
    @Override
    public void onSlotChanged() {
        
        ContainerInventory.refresh(this.player);
    }
    
    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack stack) {
        
        this.onSlotChanged();
        return stack;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.playerCap.getBackpack().splitStack(amount);
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
