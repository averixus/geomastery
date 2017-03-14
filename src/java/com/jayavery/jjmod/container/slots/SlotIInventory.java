package com.jayavery.jjmod.container.slots;

import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Container inventory slot for IInventory inventories. */
public class SlotIInventory extends Slot {

    public SlotIInventory(IInventory inventoryIn, int index, int xPosition,
            int yPosition) {
        
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
         return !ModBlocks.OFFHAND_ONLY.contains(stack.getItem());
    }
}
