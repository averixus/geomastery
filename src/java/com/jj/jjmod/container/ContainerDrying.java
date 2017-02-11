package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockComplexAbstract;
import com.jj.jjmod.container.slots.SlotDryingInput;
import com.jj.jjmod.container.slots.SlotDryingOutput;
import com.jj.jjmod.tileentities.TEDrying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for Drying Rack. */
public class ContainerDrying extends ContainerAbstract {

    private static final int INPUT_X = 56;
    private static final int OUTPUT_X = 116;
    private static final int SLOTS_Y = 35;

    private static final int INPUT_I = 0;
    private static final int OUTPUT_I = 1;
    private static final int HOT_START = 2;
    private static final int HOT_END = 10;
    private static final int INV_START = 11;

    private final int invEnd;

    public final TEDrying drying;
    private BlockPos pos;

    public ContainerDrying(EntityPlayer player, World world,
            TEDrying drying, BlockPos pos) {

        super(player, world);
        this.drying = drying;
        this.pos = pos;

        // Drying slots
        this.addSlotToContainer(new SlotDryingInput(drying,
                INPUT_X, SLOTS_Y));
        this.addSlotToContainer(new SlotDryingOutput(drying,
                OUTPUT_X, SLOTS_Y));

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

    @Override
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack stack1 = slot.getStack();
            itemstack = stack1.copy();

            if (index == OUTPUT_I) {

                if (!this.mergeItemStack(stack1, HOT_START,
                        this.invEnd + 1, true)) {

                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(stack1, itemstack);

            } else if (index != INPUT_I) {

                if (!this.drying.recipes
                        .getCookingResult(stack1).isEmpty()) {

                    if (!this.mergeItemStack(stack1, INPUT_I,
                            INPUT_I + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (index >= INV_START && index <= this.invEnd) {

                    if (!this.mergeItemStack(stack1, HOT_START,
                            HOT_END + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (index >= HOT_START && index <= HOT_END)

                    if (!this.mergeItemStack(stack1, INV_START,
                            this.invEnd + 1, false)) {

                        return ItemStack.EMPTY;
                    }

            } else if (!this.mergeItemStack(stack1, HOT_START,
                    this.invEnd + 1, false)) {

                return ItemStack.EMPTY;
            }

            if (stack1.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);

            } else {

                slot.onSlotChanged();
            }

            if (stack1.getCount() == itemstack.getCount()) {

                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack1);
        }

        return itemstack;
    }
}
