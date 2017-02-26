package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockComplexAbstract;
import com.jj.jjmod.container.slots.SlotDryingInput;
import com.jj.jjmod.container.slots.SlotDryingOutput;
import com.jj.jjmod.tileentities.TEDrying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for Drying Rack. */
public class ContainerDrying extends ContainerAbstract {

    /** X-position of start of input slots. */
    private static final int INPUT_X = 49;
    /** Y_position of start of input slots. */
    private static final int INPUT_Y = 35;
    /** X-position of start of output slots. */
    private static final int OUTPUT_X = 113;

    /** Index of first input slot. */
    private static final int INPUT_START = 0;
    /** Index of last input slot. */
    private static final int INPUT_END = 1;
    /** Index of first output slot. */
    private static final int OUTPUT_START = 2;
    /** Index of last output slot. */
    private static final int OUTPUT_END = 3;
    /** Index of first hotbar slot. */
    private static final int HOT_START = 4;
    /** Index of last hotbar slot. */
    private static final int HOT_END = 12;
    /** Index of first inventory slot. */
    private static final int INV_START = 13;

    /** Index of last inventory slot. */
    private final int invEnd;

    /** Drying rack TileEntity of this container. */
    public final TEDrying drying;
    /** Position of this container. */
    private BlockPos pos;

    public ContainerDrying(EntityPlayer player, World world,
            TEDrying drying, BlockPos pos) {

        super(player, world);
        this.drying = drying;
        this.pos = pos;

        // Input slots
        for (int i = 0; i < 2; i++) {
            
            this.addSlotToContainer(new SlotDryingInput(drying, i,
                    INPUT_X - (i * SLOT_SIZE), INPUT_Y));
        }
        
        // Output slots
        for (int i = 0; i < 2; i++) {
                
            this.addSlotToContainer(new SlotDryingOutput(drying,
                    i, OUTPUT_X + (i * SLOT_SIZE), INPUT_Y));
        }

        // Inventory grid
        this.buildHotbar();
        int invIndex = this.buildInvgrid();

        // Container indices
        this.invEnd = INV_START + invIndex;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        boolean correctBlock = this.world.getBlockState(this.pos)
                .getBlock() instanceof BlockComplexAbstract;

        if (correctBlock) {

            return player.getDistanceSq(this.pos.getX() + 0.5,
                    this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
        }

        return false;
    }
    
    /** Sorts the inputs list when any slot is clicked. */
    @Override
    public ItemStack slotClick(int slot, int drag, ClickType click,
            EntityPlayer player) {
        
        ItemStack result = super.slotClick(slot, drag, click, player);
        this.drying.sort();
        return result;
    }
    
    /** Prevents slot click from being repeated if the
     * same item moves into the slot after sorting. */
    @Override
    public void retrySlotClick(int slot, int click,
            boolean mode, EntityPlayer player) {}

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {

            ItemStack inSlot = slot.getStack();
            result = inSlot.copy();

            if (index >= OUTPUT_START && index <= OUTPUT_END) {

                if (!this.mergeItemStack(inSlot, HOT_START,
                        this.invEnd + 1, true)) {

                    return ItemStack.EMPTY;
                }

            } else if (!(index >= INPUT_START && index <= INPUT_END)) {

                if (!this.drying.recipes.getCookingResult(inSlot, this.world)
                        .isEmpty()) {

                    if (!this.mergeItemStack(inSlot, INPUT_START,
                            INPUT_END + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (index >= INV_START && index <= this.invEnd) {

                    if (!this.mergeItemStack(inSlot, HOT_START,
                            HOT_END + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (index >= HOT_START && index <= HOT_END)

                    if (!this.mergeItemStack(inSlot, INV_START,
                            this.invEnd + 1, false)) {

                        return ItemStack.EMPTY;
                    }

            } else if (!this.mergeItemStack(inSlot, HOT_START,
                    this.invEnd + 1, false)) {

                return ItemStack.EMPTY;
            }

            if (inSlot.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);

            } else {

                slot.onSlotChanged();
            }

            if (inSlot.getCount() == result.getCount()) {

                return ItemStack.EMPTY;
            }

            slot.onTake(player, inSlot);
        }

        return result;
    }
}
