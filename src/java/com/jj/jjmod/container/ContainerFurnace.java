package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockComplexAbstract;
import com.jj.jjmod.container.slots.SlotFurnaceFuel;
import com.jj.jjmod.container.slots.SlotFurnaceInput;
import com.jj.jjmod.container.slots.SlotFurnaceOutput;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for Furnace devices. */
public class ContainerFurnace extends ContainerAbstract {

    private static final int INPUTS_X = 56;
    private static final int INPUT_Y = 17;
    private static final int FUEL_Y = 53;
    private static final int OUTPUT_X = 116;
    private static final int OUTPUT_Y = 35;

    private static final int INPUT_I = 0;
    private static final int FUEL_I = 1;
    private static final int OUTPUT_I = 2;
    private static final int HOT_START = 3;
    private static final int HOT_END = 11;
    private static final int INV_START = 12;

    private final int invEnd;

    public final TEFurnaceAbstract furnace;
    private BlockPos pos;
    private int fuelLeft;
    private int fuelEach;
    private int cookSpent;
    private int cookEach;

    public ContainerFurnace(EntityPlayer player, World world,
            TEFurnaceAbstract furnace, BlockPos pos) {

        super(player, world);
        this.furnace = furnace;
        this.pos = pos;

        // Furnace slots
        this.addSlotToContainer(new SlotFurnaceInput(this.furnace,
                INPUTS_X, INPUT_Y));
        this.addSlotToContainer(new SlotFurnaceFuel(this.furnace,
                INPUTS_X, FUEL_Y));
        this.addSlotToContainer(new SlotFurnaceOutput(this.furnace,
                OUTPUT_X, OUTPUT_Y));

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

            } else if (index != INPUT_I && index != FUEL_I) {

                if (!this.furnace.recipes
                        .getCookingResult(stack1).isEmpty()) {

                    if (!this.mergeItemStack(stack1, INPUT_I,
                            INPUT_I + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (this.furnace
                        .isItemFuel(stack1)) {

                    if (!this.mergeItemStack(stack1, FUEL_I,
                            FUEL_I + 1, false)) {

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
