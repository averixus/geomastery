package com.jayavery.jjmod.container.slots;

import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/** Container inventory slot for ItemStackHandler inventories. */
public class SlotIItemHandler extends SlotItemHandler {

    public SlotIItemHandler(IItemHandler inventoryIn, int index, int xPosition,
            int yPosition) {
        
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
         return !ModBlocks.OFFHAND_ONLY.contains(stack.getItem());
    }
}
