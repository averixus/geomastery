/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container.slots;

import jayavery.geomastery.main.GeoBlocks;
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
        
         return !GeoBlocks.OFFHAND_ONLY.contains(stack.getItem());
    }
}
