package com.jj.jjmod.container.slots;

import com.jj.jjmod.init.ModBlocks;
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
        
        boolean notOffhand = !ModBlocks.OFFHAND_ONLY.contains(stack.getItem());
      //  boolean capsMatch = this.getStack().isEmpty() || this.getStack().areCapsCompatible(stack);
     //   System.out.println("checking isitemvalid, caps match? " + capsMatch);
        return notOffhand /*&& capsMatch*/;

    }
}
