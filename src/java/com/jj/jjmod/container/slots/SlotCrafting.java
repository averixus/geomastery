package com.jj.jjmod.container.slots;

import javax.annotation.Nullable;
import com.jj.jjmod.crafting.CraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/** Container slot for crafting recipe output. */
public class SlotCrafting extends Slot {

    /** The crafting manager for this slot. */
    private final CraftingManager craftingManager;
    /** The crafting grid that leads to this slot. */
    private final InventoryCrafting craftInv;
    /** The player who owns this slot. */
    private final EntityPlayer player;
    /** The amount of items crafted at this slot. */
    private int amountCrafted;

    public SlotCrafting(EntityPlayer player,
            InventoryCrafting craftingInv, IInventory inventory,
            int xPosition, int yPosition,
            CraftingManager craftingManager) {

        super(inventory, 0, xPosition, yPosition);
        this.player = player;
        this.craftInv = craftingInv;
        this.craftingManager = craftingManager;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {

        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount) {

        if (this.getHasStack()) {

            this.amountCrafted += Math.min(amount,
                    this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    @Override
    protected void onCrafting(ItemStack stack, int amount) {

        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onCrafting(ItemStack stack) {

        if (this.amountCrafted > 0) {

            stack.onCrafting(this.player.world, this.player,
                    this.amountCrafted);
        }

        this.amountCrafted = 0;
    }

    @Override 
    public ItemStack onTake(EntityPlayer player, ItemStack stack) {

        this.onCrafting(stack);
        NonNullList<ItemStack> stacks = this.craftingManager
                .getRemainingItems(this.craftInv, player.world);
        int[] amountsUsed = this.craftingManager
                .getAmountsUsed(this.craftInv, player.world);

        for (int i = 0; i < stacks.size(); i++) {

            ItemStack inSlot = this.craftInv.getStackInSlot(i);
            ItemStack recipeRemaining = stacks.get(i);

            // Use up correct amount of all the input items
            if (!inSlot.isEmpty()) {

                this.craftInv.decrStackSize(i, amountsUsed[i]);
                inSlot = this.craftInv.getStackInSlot(i);
            }

            // Add any extra remaining items from recipe
            if (!recipeRemaining.isEmpty()) {

                if (!inSlot.isEmpty()) {

                    this.craftInv.setInventorySlotContents(i,
                            recipeRemaining);

                } else if (ItemStack.areItemsEqual(inSlot, recipeRemaining) &&
                        ItemStack.areItemStackTagsEqual(inSlot,
                        recipeRemaining)) {

                    recipeRemaining.grow(inSlot.getCount());
                    this.craftInv.setInventorySlotContents(i,
                            recipeRemaining);

                } else if (!this.player.inventory
                        .addItemStackToInventory(recipeRemaining)) {

                    this.player.dropItem(recipeRemaining, false);
                }
            }
        }
        
        return stack;
    }
}