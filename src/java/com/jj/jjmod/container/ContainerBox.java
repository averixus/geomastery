package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.container.slots.SlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Container for Box. Mostly duplicate of Chest with 9 slots. */
public class ContainerBox extends ContainerAbstract {
    
    private static final int CHEST_Y = 36;
    private static final int HOT_START = 0;
    private static final int HOT_END = 8;
    private static final int INV_START = 9;
    
    private final int invEnd;
    private final int chestStart;
    private final int chestEnd;
    
    public IInventory boxInv;
    
    public ContainerBox(EntityPlayer player, World world, IInventory boxInv) {
        
        super(player, world);
        this.boxInv = boxInv;
        boxInv.openInventory(player);
        
        // Inventory
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Box inventory
        for (int i = 0; i < 9; i++) {
            
            this.addSlotToContainer(new SlotInventory(this.boxInv,
                    i, getInvX(i), CHEST_Y));
        }
        
        // Container indices
        this.invEnd = INV_START + invIndex;
        this.chestStart = this.invEnd + 1;
        this.chestEnd = this.invEnd + this.boxInv.getSizeInventory();
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            
            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();
            
            if (index >= HOT_START && index <= this.invEnd) {
                
                if (!this.mergeItemStack(slotStack,
                        this.chestStart, this.chestEnd + 1, true)) {
                    
                    return ItemStack.EMPTY;
                }
                
            } else if (index >= this.chestStart && index <= this.chestEnd) {
                
                if (!this.mergeItemStack(slotStack,
                        HOT_START, this.invEnd + 1, true)) {
                    
                    return ItemStack.EMPTY;
                }
            }
            
            if (slotStack.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);
            }
        }
        
        return result;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        
        return this.boxInv.isUsableByPlayer(player);
    }
    
    @Override
    public void onContainerClosed(EntityPlayer player) {
        
        super.onContainerClosed(player);
        this.boxInv.closeInventory(player);
    }
}
