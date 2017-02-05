package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.container.slots.SlotCrafting;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFurnace extends ContainerAbstract {

    public static final int INPUTS_X = 56;
    public static final int INPUT_Y = 17;
    public static final int FUEL_Y = 53;
    public static final int OUTPUT_X = 116;
    public static final int OUTPUT_Y = 35;

    public static final int INPUT_I = 0;
    public static final int FUEL_I = 1;
    public static final int OUTPUT_I = 2;
    public static final int HOT_START = 3;
    public static final int HOT_END = 11;
    public static final int INV_START = 12;

    public final int INV_END;

    public final IInventory furnaceInv;
    public int fuelLeft;
    public int fuelEach;
    public int cookSpent;
    public int cookEach;

    public ContainerFurnace(EntityPlayer player, World world,
            IInventory furnaceInv) {

        super(player, world);
        this.furnaceInv = furnaceInv;

        // Furnace slots
        this.addSlotToContainer(new Slot(this.furnaceInv,
                INPUT_I, INPUTS_X, INPUT_Y));
        this.addSlotToContainer(new SlotFurnaceFuel(this.furnaceInv,
                FUEL_I, INPUTS_X, FUEL_Y) {
            
            @Override
            public boolean isItemValid(ItemStack stack) {

                return ((TEFurnaceAbstract) ContainerFurnace.this.furnaceInv).isItemFuel(stack);
            }
        });
        this.addSlotToContainer(new SlotFurnaceOutput(player,
                this.furnaceInv, OUTPUT_I, OUTPUT_X, OUTPUT_Y));

        // Inventory grid
        int invIndex = this.buildInvGrid();

        this.INV_END = HOT_START + invIndex;
    }

    @Override
    public void addListener(IContainerListener listener) {

        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.furnaceInv);
    }

    @Override
    public void detectAndSendChanges() {

        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            
            IContainerListener icontainerlistener =
                    this.listeners.get(i);

            if (this.fuelLeft != this.furnaceInv.getField(0)) {

                icontainerlistener.sendProgressBarUpdate(this, 0,
                        this.furnaceInv.getField(0));
            }

            if (this.fuelEach != this.furnaceInv.getField(1)) {

                icontainerlistener.sendProgressBarUpdate(this, 1,
                        this.furnaceInv.getField(1));
            }

            if (this.cookSpent != this.furnaceInv.getField(2)) {

                icontainerlistener.sendProgressBarUpdate(this, 2,
                        this.furnaceInv.getField(2));
            }

            if (this.cookEach != this.furnaceInv.getField(3)) {

                icontainerlistener.sendProgressBarUpdate(this, 3,
                        this.furnaceInv.getField(3));
            }
        }

        this.fuelLeft = this.furnaceInv.getField(0);
        this.fuelEach = this.furnaceInv.getField(1);
        this.cookSpent = this.furnaceInv.getField(2);
        this.cookEach = this.furnaceInv.getField(3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {

        this.furnaceInv.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return this.furnaceInv.isUseableByPlayer(player);
    }

    @Override
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack itemstack = ItemStack.field_190927_a;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack stack1 = slot.getStack();
            itemstack = stack1.copy();

            if (index == OUTPUT_I) {

                if (!this.mergeItemStack(stack1, HOT_START, this.INV_END + 1,
                        true)) {

                    return ItemStack.field_190927_a;
                }

                slot.onSlotChange(stack1, itemstack);

            } else if (index != INPUT_I && index != FUEL_I) {

                if (((TEFurnaceAbstract) this.furnaceInv).recipes
                        .getSmeltingResult(stack1) != null) {

                    if (!this.mergeItemStack(stack1, INPUT_I, INPUT_I + 1,
                            false)) {

                        return ItemStack.field_190927_a;
                    }

                } else if (((TEFurnaceAbstract) this.furnaceInv)
                        .isItemFuel(stack1)) {

                    if (!this.mergeItemStack(stack1, FUEL_I, FUEL_I + 1,
                            false)) {

                        return ItemStack.field_190927_a;
                    }

                } else if (index >= INV_START && index <= this.INV_END) {

                    if (!this.mergeItemStack(stack1, HOT_START, HOT_END + 1,
                            false)) {

                        return ItemStack.field_190927_a;
                    }

                } else if (index >= HOT_START && index <= HOT_END)

                    if (!this.mergeItemStack(stack1, INV_START, this.INV_END + 1,
                            false)) {

                        return ItemStack.field_190927_a;
                    }

            } else if (!this.mergeItemStack(stack1, HOT_START, this.INV_END + 1,
                    false)) {

                return ItemStack.field_190927_a;
            }

            if (stack1.func_190916_E() == 0) {

                slot.putStack(ItemStack.field_190927_a);

            } else {

                slot.onSlotChanged();
            }

            if (stack1.func_190916_E() == itemstack.func_190916_E()) {

                return ItemStack.field_190927_a;
            }

            ((SlotCrafting) slot).func_190901_a(player, stack1);
        }

        return itemstack;
    }
}
