package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.container.slots.SlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBox extends ContainerAbstract {

    public IInventory boxInv;
    
    public static final int CHEST_Y = 36;
    
    public static final int HOT_START = 0;
    public static final int HOT_END = 8;
    public static final int INV_START = 9;
    
    public final int INV_END;
    public final int CHEST_START;
    public final int CHEST_END;
    
    public ContainerBox(EntityPlayer player, World world, IInventory boxInv) {
        
        super(player, world);
        this.boxInv = boxInv;
        boxInv.openInventory(player);
        
        // Inventory
        int invIndex = this.buildInvGrid();
        
        // Box inventory
        for (int i = 0; i < 9; i++) {
            
            this.addSlotToContainer(new SlotInventory(this.boxInv,
                    i, getInvX(i), CHEST_Y));
        }
        
        this.INV_END = HOT_START + invIndex;
        this.CHEST_START = this.INV_END + 1;
        this.CHEST_END = this.INV_END + this.boxInv.getSizeInventory();
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        
        ItemStack result = ItemStack.field_190927_a;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            
            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();
            
            if (index >= HOT_START && index <= this.INV_END) {
                
                if (!this.mergeItemStack(slotStack,
                        this.CHEST_START, this.CHEST_END + 1, true)) {
                    
                    return ItemStack.field_190927_a;
                }
                
            } else if (index >= this.CHEST_START && index <= this.CHEST_END) {
                
                if (!this.mergeItemStack(slotStack,
                        HOT_START, this.INV_END + 1, true)) {
                    
                    return ItemStack.field_190927_a;
                }
            }
            
            if (slotStack.func_190916_E() == 0) {

                slot.putStack(ItemStack.field_190927_a);
            }
        }
        
        return result;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        
        return this.boxInv.isUseableByPlayer(player);
    }
    
    @Override
    public void onContainerClosed(EntityPlayer player) {
        
        super.onContainerClosed(player);
        this.boxInv.closeInventory(player);
    }
}
