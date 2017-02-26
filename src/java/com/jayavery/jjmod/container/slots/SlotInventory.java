package com.jayavery.jjmod.container.slots;

import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Container inventory slot for main inventory. */
public class SlotInventory extends Slot {

    public SlotInventory(IInventory inventoryIn, int index, int xPosition,
            int yPosition) {
        
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
         return !ModBlocks.OFFHAND_ONLY.contains(stack.getItem());
    }
}
