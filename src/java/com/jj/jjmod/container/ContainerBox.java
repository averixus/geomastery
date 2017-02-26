package com.jj.jjmod.container;

import com.jj.jjmod.container.slots.SlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Container for Box. Mostly duplicate of Chest with 9 slots. */
public class ContainerBox extends ContainerAbstract {
    
    /** Y-position of the start of the chest grid. */
    private static final int BOX_Y = 36;
    /** Index of the start of the player hotbar. */
    private static final int HOT_START = 0;
    
    /** Index of the end of the player inventory. */
    private final int invEnd;
    /** Index of the start of the box inventory. */
    private final int boxStart;
    /** Index of the end of the box inventory. */
    private final int boxEnd;
    /** Inventory of the box. */
    public final IInventory boxInv;
    
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
                    i, getInvX(i), BOX_Y));
        }
        
        // Container indices
        this.invEnd = HOT_START + ROW_LENGTH + invIndex;
        this.boxStart = this.invEnd + 1;
        this.boxEnd = this.invEnd + this.boxInv.getSizeInventory();
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
                        this.boxStart, this.boxEnd + 1, true)) {
                    
                    return ItemStack.EMPTY;
                }
                
            } else if (index >= this.boxStart && index <= this.boxEnd) {
                
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
