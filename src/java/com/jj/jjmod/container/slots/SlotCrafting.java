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

public class SlotCrafting extends Slot {

    private final CraftingManager craftingManager;
    private final InventoryCrafting craftInv;
    private final EntityPlayer player;
    private int amountCrafted;

    public SlotCrafting(EntityPlayer player,
            InventoryCrafting craftingInv, IInventory inventory,
            int slotIndex, int xPosition, int yPosition,
            CraftingManager craftingManager) {

        super(inventory, slotIndex, xPosition, yPosition);
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
                    this.getStack().func_190916_E());
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

            stack.onCrafting(this.player.worldObj, this.player,
                    this.amountCrafted);
        }

        this.amountCrafted = 0;
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {

        net.minecraftforge.fml.common.FMLCommonHandler.instance()
                .firePlayerCraftingEvent(player, stack, this.craftInv);
        this.onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        NonNullList<ItemStack> stacks = this.craftingManager
                .getRemainingItems(this.craftInv, player.worldObj);
        int[] amountsUsed = this.craftingManager
                .getAmountsUsed(this.craftInv, player.worldObj);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
        for (int i = 0; i < stacks.size(); ++i) {

            ItemStack itemstack1 = this.craftInv.getStackInSlot(i);
            ItemStack itemstack2 = stacks.get(i);

            // Use up correct amount of all the input items
            if (itemstack1 != null) {

                this.craftInv.decrStackSize(i, amountsUsed[i]);
                itemstack1 = this.craftInv.getStackInSlot(i);
            }

            // Add any extra remaining items from recipe
            if (itemstack2 != ItemStack.field_190927_a) {

                if (itemstack1 == ItemStack.field_190927_a) {

                    this.craftInv.setInventorySlotContents(i,
                            itemstack2);

                } else if (ItemStack.areItemsEqual(itemstack1, itemstack2) &&
                        ItemStack.areItemStackTagsEqual(itemstack1,
                        itemstack2)) {

                    itemstack2.func_190917_f(itemstack1.func_190916_E());
                    this.craftInv.setInventorySlotContents(i,
                            itemstack2);

                } else if (!this.player.inventory
                        .addItemStackToInventory(itemstack2)) {

                    this.player.dropItem(itemstack2, false);
                }
            }
        }
    }
}