package com.jayavery.jjmod.container;

import com.jayavery.jjmod.blocks.BlockBox;
import com.jayavery.jjmod.container.slots.SlotIItemHandler;
import com.jayavery.jjmod.tileentities.TEBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/** Container for box. Mostly duplicate of chest with 18 slots. */
public class ContainerBox extends ContainerAbstract {
    
    /** Y-position of the start of the box grid. */
    private static final int BOX_Y = 26;
    /** Index of the start of the player hotbar. */
    private static final int HOT_START = 0;
    
    /** Index of the end of the player inventory. */
    private final int invEnd;
    /** Index of the start of the box inventory. */
    private final int boxStart;
    /** Index of the end of the box inventory. */
    private final int boxEnd; 
    /** TileEntity of the box. */
    private final TEBox box;
    /** Inventory of the box. */
    public final IItemHandler boxInv;
    /** Position of the box. */
    private final BlockPos pos;
    
    
    public ContainerBox(EntityPlayer player, World world,
            BlockPos pos, TEBox box) {
        
        super(player, world);
        this.boxInv = box.getCapability(CapabilityItemHandler
                .ITEM_HANDLER_CAPABILITY, null);
        this.box = box;
        this.pos = pos;
        this.box.open();
        
        // Inventory
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Box inventory
        for (int i = 0; i < 2; i++) {
            
            for (int j = 0; j < 9; j++) {
                
                this.addSlotToContainer(new SlotIItemHandler(this.boxInv,
                        j + (i * ROW_LENGTH), getInvX(j),
                        BOX_Y + (i * SLOT_SIZE)));
            }
        }
        
        // Container indices
        this.invEnd = HOT_START + ROW_LENGTH + invIndex;
        this.boxStart = this.invEnd + 1;
        this.boxEnd = this.invEnd + this.boxInv.getSlots();
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
        
        boolean correctBlock = this.world.getBlockState(this.pos)
                .getBlock() instanceof BlockBox;

        if (correctBlock) {

            return player.getDistanceSq(this.pos.getX() + 0.5,
                    this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
        }

        return false;
    }
    
    @Override
    public void onContainerClosed(EntityPlayer player) {
        
        super.onContainerClosed(player);
        this.box.close();
    }
}
