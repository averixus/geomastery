/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import jayavery.geomastery.blocks.BlockBasket;
import jayavery.geomastery.container.slots.SlotIItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

/** Container for Basket. Mostly duplicate of Chest with 9 slots. */
public class ContainerBasket extends ContainerAbstract {
    
    /** Y-position of the start of the basket grid. */
    private static final int BASKET_Y = 36;
    /** Index of the start of the player hotbar. */
    private static final int HOT_START = 0;
    
    /** Index of the end of the player inventory. */
    private final int invEnd;
    /** Index of the start of the basket inventory. */
    private final int basketStart;
    /** Index of the end of the basket inventory. */
    private final int basketEnd;
    /** Inventory of the basket. */
    public final IItemHandler basketInv;
    /** Position of the basket. */
    private final BlockPos pos;
    
    public ContainerBasket(EntityPlayer player, BlockPos pos,
            World world, IItemHandler basketInv) {
        
        super(player, world);
        this.basketInv = basketInv;
        this.pos = pos;
        
        // Inventory
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Basket inventory
        for (int i = 0; i < 9; i++) {
            
            this.addSlotToContainer(new SlotIItemHandler(this.basketInv,
                    i, getInvX(i), BASKET_Y));
        }
        
        // Container indices
        this.invEnd = HOT_START + ROW_LENGTH + invIndex;
        this.basketStart = this.invEnd + 1;
        this.basketEnd = this.invEnd + this.basketInv.getSlots();
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
                        this.basketStart, this.basketEnd + 1, true)) {
                    
                    return ItemStack.EMPTY;
                }
                
            } else if (index >= this.basketStart && index <= this.basketEnd) {
                
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
        
        boolean correctBlock = this.world.getBlockState(this.pos)
                .getBlock() instanceof BlockBasket;

        if (correctBlock) {

            return player.getDistanceSq(this.pos.getX() + 0.5,
                    this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
        }

        return false;
    }
}
