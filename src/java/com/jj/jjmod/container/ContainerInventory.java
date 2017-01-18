package com.jj.jjmod.container;

import javax.annotation.Nullable;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.slots.SlotArmour;
import com.jj.jjmod.container.slots.SlotCarry;
import com.jj.jjmod.container.slots.SlotCarry.CarryType;
import com.jj.jjmod.container.slots.SlotCrafting;
import com.jj.jjmod.packets.InventoryUpdateClient;
import com.jj.jjmod.packets.InventoryUpdateServer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerInventory extends ContainerAbstract {

    public static final int CRAFT_COLS = 3;
    public static final int CRAFT_ROWS = 2;

    public static final int OUTPUT_X = 154;
    public static final int OUTPUT_Y = 28;
    public static final int CRAFT_X = 80;
    public static final int CRAFT_Y = 18;
    public static final int ARMOUR_X = 8;
    public static final int HEAD_Y = 8;
    public static final int CHEST_Y = 26;
    public static final int LEGS_Y = 44;
    public static final int FEET_Y = 62;
    public static final int EQUIP_Y = 62;
    public static final int SHIELD_X = 77;
    public static final int BACKPACK_X = 95;
    public static final int YOKE_X = 113;

    public static final int FEET_I = 0;
    public static final int LEGS_I = 1;
    public static final int CHEST_I = 2;
    public static final int HEAD_I = 3;
    public static final int SHIELD_I = 4;
    public static final int BACKPACK_I = 5;
    public static final int YOKE_I = 6;
    public static final int HOT_START = 7;
    public static final int HOT_END = 15;
    public static final int INV_START = 16;

    public final int INV_END;
    public final int CRAFT_START;
    public final int CRAFT_END;
    public final int OUTPUT_I;

    public InventoryCrafting craftMatrix;
    public IInventory craftResult = new InventoryCraftResult();

    public ContainerInventory(EntityPlayer player, World world) {

        super(player, world);
        this.player.openContainer = this;
        
        // Equipment slots
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, FEET_Y, EntityEquipmentSlot.FEET));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, LEGS_Y, EntityEquipmentSlot.LEGS));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, CHEST_Y, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, HEAD_Y, EntityEquipmentSlot.HEAD));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                SHIELD_X, EQUIP_Y, EntityEquipmentSlot.OFFHAND));
        this.addSlotToContainer(new SlotCarry(this.player,
                BACKPACK_X, EQUIP_Y, CarryType.BACKPACK));
        this.addSlotToContainer(new SlotCarry(this.player,
                YOKE_X, EQUIP_Y, CarryType.YOKE));

        // Inventory and craft grid slots
        int invIndex = this.buildInvGrid();
        this.craftMatrix = this.buildCraftMatrix(CRAFT_COLS, CRAFT_ROWS,
                CRAFT_X, CRAFT_Y);

        // Container indices
        this.INV_END = HOT_START + invIndex;
        this.CRAFT_START = this.INV_END + 1;
        this.CRAFT_END = this.INV_END + this.craftMatrix.getSizeInventory();
        this.OUTPUT_I = this.CRAFT_END + 1;

        // Output slot
        this.addSlotToContainer(new SlotCrafting(player,
                this.craftMatrix, this.craftResult, this.OUTPUT_I, OUTPUT_X,
                OUTPUT_Y, ModRecipes.INVENTORY));

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory craftMatrix) {

        @Nullable
        ItemStack stack = ModRecipes.INVENTORY
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

                if (stack != ItemStack.field_190927_a) {

                    player.dropItem(stack, false);
                    this.sendUpdateInventory(InvType.CRAFTGRID, i, ItemStack.field_190927_a);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return true;
    }

    @Override
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack result = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) {

            return null;
        }

        ItemStack slotStack = slot.getStack();
        Item slotItem = slotStack.getItem();
        result = slotStack.copy();

        if (slotItem instanceof ItemArmor) {

            EntityEquipmentSlot armourType = ((ItemArmor) slotItem).armorType;

            if (armourType == EntityEquipmentSlot.HEAD && !this.inventorySlots
                    .get(HEAD_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        HEAD_I, HEAD_I + 1, true)) {

                    result = null;
                }

            } else if (armourType == EntityEquipmentSlot.CHEST &&
                    !this.inventorySlots.get(CHEST_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack, CHEST_I, CHEST_I + 1,
                        true)) {

                    result = null;
                }

            } else if (armourType == EntityEquipmentSlot.LEGS &&
                    !this.inventorySlots.get(LEGS_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        LEGS_I, LEGS_I + 1, true)) {

                    result = null;
                }

            } else if (armourType == EntityEquipmentSlot.FEET &&
                    !this.inventorySlots.get(FEET_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        FEET_I, FEET_I + 1, true)) {

                    result = null;
                }
            }
        }

        if (slotItem instanceof ItemShield &&
                !this.inventorySlots.get(SHIELD_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack,
                    SHIELD_I, SHIELD_I + 1, true)) {

                result = null;
            }
        }

        if (slotItem == ModItems.backpack && !this.inventorySlots
                .get(BACKPACK_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack, BACKPACK_I, BACKPACK_I + 1,
                    true)) {
                
                result = null;
            }
        }

        if (slotItem == ModItems.yoke && !this.inventorySlots.get(YOKE_I)
                .getHasStack()) {

            if (!this.mergeItemStack(slotStack, YOKE_I, YOKE_I + 1, true)) {

                result = null;
            }
        }

        if (index == this.OUTPUT_I) {

            if (!this.mergeItemStack(slotStack, HOT_START, this.INV_END + 1,
                    true)) {

                result = null;
            }

            slot.onSlotChange(slotStack, result);

        } else if ((index >= this.CRAFT_START && index <= this.CRAFT_END) ||
                (index >= FEET_I && index <= YOKE_I)) {

            if (!this.mergeItemStack(slotStack, HOT_START, this.INV_END + 1,
                    true)) {

                result = null;
            }

        } else if (index >= HOT_START && index <= HOT_END) {

            if (!this.mergeItemStack(slotStack, INV_START, this.INV_END + 1,
                    true)) {

                result = null;
            }

        } else if (index >= INV_START && index <= this.INV_END) {

            if (!this.mergeItemStack(slotStack, HOT_START, HOT_END + 1, true)) {

                result = null;
            }
        }

        if (slot.getStack().func_190916_E() == 0) {

            slot.putStack(ItemStack.field_190927_a);
            
        } else {

            slot.onSlotChanged();
        }

        ((SlotCrafting) slot).onPickupFromSlot(player, slotStack);
        return result;
    }

    public void setStack(InvType type, int slot, ItemStack stack) {

        ItemStack replace = (stack == null ||
                stack == ItemStack.field_190927_a ||
                stack.func_190916_E() == 0) ?
                ItemStack.field_190927_a : stack;

        switch (type) {

            case INVENTORY: {

                this.playerInv.mainInventory.set(slot, replace);
                break;
            }

            case OFFHAND: {

                this.playerInv.offHandInventory.set(slot, replace);
                break;
            }

            case CRAFTGRID: {

                this.craftMatrix.setInventorySlotContents(slot, replace);
                break;
            }
            
            case CRAFTOUT : {
                
                this.craftResult.setInventorySlotContents(slot, replace);
                break;
            }
        }
    }

    public void swapHands() {

        ItemStack toMove =
                this.playerInv.mainInventory.get(this.playerInv.currentItem);
        this.playerInv.mainInventory.set(this.playerInv.currentItem,
                this.playerInv.offHandInventory.get(0));
        this.playerInv.offHandInventory.set(0, toMove);
    }

    public void sendUpdateOffhand() {

        sendUpdateInventory(InvType.INVENTORY, this.playerInv.currentItem,
                this.playerInv.mainInventory.get(this.playerInv.currentItem));
        sendUpdateInventory(InvType.OFFHAND, 0,
                this.playerInv.offHandInventory.get(0));
    }

    public void sendUpdateHighlight() {

        sendUpdateInventory(InvType.INVENTORY, this.playerInv.currentItem,
                this.playerInv.mainInventory.get(this.playerInv.currentItem));
    }

    public void sendUpdateInventory(InvType type, int slot, ItemStack stack) {

        if (this.player instanceof EntityPlayerMP) {

            ModPackets.INSTANCE
                    .sendTo(new InventoryUpdateClient(type, slot, stack),
                    (EntityPlayerMP) this.player);

        } else {

            ModPackets.INSTANCE.sendToServer(new InventoryUpdateServer(type,
                    slot, stack));
        }
    }

    public enum InvType {

        INVENTORY,
        OFFHAND,
        CRAFTGRID,
        CRAFTOUT;
    }
}
