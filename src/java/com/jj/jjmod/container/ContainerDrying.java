package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.container.slots.SlotCrafting;
import com.jj.jjmod.tileentities.TEDrying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerDrying extends ContainerAbstract {

    public static final int INPUT_X = 56;
    public static final int OUTPUT_X = 116;
    public static final int SLOTS_Y = 35;

    public static final int INPUT_I = 0;
    public static final int OUTPUT_I = 1;
    public static final int HOT_START = 2;
    public static final int HOT_END = 10;
    public static final int INV_START = 11;

    public final int INV_END;

    public final IInventory dryingInv;
    public int drySpent;
    public int dryEach;

    public ContainerDrying(EntityPlayer player, World world,
            IInventory dryingInv) {

        super(player, world);
        this.dryingInv = dryingInv;

        // Drying slots
        this.addSlotToContainer(new Slot(this.dryingInv,
                INPUT_I, INPUT_X, SLOTS_Y));
        this.addSlotToContainer(new SlotFurnaceOutput(player,
                this.dryingInv, OUTPUT_I, OUTPUT_X, SLOTS_Y));

        // Inventory grid
        int invIndex = this.buildInvGrid();

        this.INV_END = HOT_START + invIndex;
    }

    @Override
    public void addListener(IContainerListener listener) {

        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.dryingInv);
    }

    @Override
    public void detectAndSendChanges() {

        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            
            IContainerListener icontainerlistener =
                    this.listeners.get(i);

            if (this.drySpent != this.dryingInv.getField(0)) {

                icontainerlistener.sendProgressBarUpdate(this, 0,
                        this.dryingInv.getField(0));
            }

            if (this.dryEach != this.dryingInv.getField(1)) {

                icontainerlistener.sendProgressBarUpdate(this, 1,
                        this.dryingInv.getField(1));
            }
        }

        this.drySpent = this.dryingInv.getField(0);
        this.dryEach = this.dryingInv.getField(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {

        this.dryingInv.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return this.dryingInv.isUseableByPlayer(player);
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

            } else if (index != INPUT_I) {

                if (((TEDrying) this.dryingInv).recipes
                        .getSmeltingResult(stack1) != null) {

                    if (!this.mergeItemStack(stack1, INPUT_I, INPUT_I + 1,
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
