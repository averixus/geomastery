package com.jj.jjmod.container;

import javax.annotation.Nullable;

import com.jj.jjmod.blocks.BlockComplexAbstract;
import com.jj.jjmod.crafting.CraftingManager;
import com.jj.jjmod.container.slots.SlotCrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerCrafting extends ContainerAbstract {

    public static final int OUTPUT_X = 124;
    public static final int OUTPUT_Y = 35;
    public static final int CRAFT_X = 30;
    public static final int CRAFT_Y = 17;
    public static final int CRAFT_COLS = 3;
    public static final int CRAFT_ROWS = 3;

    public static final int HOT_START = 0;
    public static final int HOT_END = 8;
    public static final int INV_START = 9;

    public final int INV_END;
    public final int CRAFT_START;
    public final int CRAFT_END;
    public final int OUTPUT_I;

    public InventoryCrafting craftMatrix;
    public IInventory craftResult = new InventoryCraftResult();
    public CraftingManager craftManager;
    public BlockPos pos;

    public ContainerCrafting(EntityPlayer player, World world,
            BlockPos pos, CraftingManager craftManager) {

        super(player, world);
        this.craftManager = craftManager;
        this.pos = pos;

        // Inventory and craft grid
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        this.craftMatrix = this.buildCraftMatrix(CRAFT_COLS, CRAFT_ROWS,
                CRAFT_X, CRAFT_Y);
        
        // Output slot
        this.addSlotToContainer(new SlotCrafting(this.player,
                this.craftMatrix, this.craftResult, 0, OUTPUT_X,
                OUTPUT_Y, this.craftManager));

        // Container indices
        this.INV_END = INV_START + invIndex;
        this.CRAFT_START = this.INV_END + 1;
        this.CRAFT_END = this.INV_END + this.craftMatrix.getSizeInventory();
        this.OUTPUT_I = this.CRAFT_END + 1;
        
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {

        @Nullable
        ItemStack stack = this.craftManager
                .findMatchingRecipe(this.craftMatrix, this.world);
        this.craftResult.setInventorySlotContents(0, stack);
    }
    
    @Override
    public ItemStack slotClick(int slot, int dragType,
            ClickType clickType, EntityPlayer player) {
        
        ItemStack result = super.slotClick(slot, dragType, clickType, player);
        this.onCraftMatrixChanged(this.craftMatrix);
        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {

        super.onContainerClosed(player);

        if (!this.world.isRemote) {

            for (int i = 0; i < this.craftMatrix.getSizeInventory(); i++) {

                ItemStack stack = this.craftMatrix.removeStackFromSlot(i);

                if (stack != ItemStack.EMPTY) {

                    player.dropItem(stack, false);
                }
            }
        }
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

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();

            if (index == this.OUTPUT_I) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        this.INV_END + 1, true)) {

                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, result);
                slot.onTake(player, slotStack);

            } else if (index >= HOT_START && index <= HOT_END) {

                if (!this.mergeItemStack(slotStack, INV_START,
                        this.INV_END + 1, true)) {

                    return ItemStack.EMPTY;
                }

            } else if (index >= INV_START && index <= this.INV_END) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        HOT_END + 1, true)) {

                    return ItemStack.EMPTY;
                }

            } else if (index >= this.CRAFT_START && index <= this.CRAFT_END) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        this.INV_END + 1, true)) {

                    return ItemStack.EMPTY;
                }

            }

            if (slotStack.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);
                
            } else {

                slot.onSlotChanged();
            }
        }

        return result;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot) {

        return slot.inventory != this.craftResult &&
                super.canMergeSlot(stack, slot);
    }
}
