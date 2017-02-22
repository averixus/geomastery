package com.jj.jjmod.container.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.EntityEquipmentSlot.Type;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Container slot for EntityEquipmentSlots. */
public class SlotArmour extends Slot {

    /** The equipment type of this slot. */
    private final EntityEquipmentSlot type;
    /** The player inventory for this slot. */
    private final InventoryPlayer playerInv;

    public SlotArmour(InventoryPlayer playerInv, int x,
            int y, EntityEquipmentSlot type) {

        super(null, 0, x, y);
        this.type = type;
        this.playerInv = playerInv;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getSlotTexture() {

        return this.type.getSlotType() == Type.ARMOR ?
                ItemArmor.EMPTY_SLOT_NAMES[this.type.getIndex()] : null;
    }

    @Override
    public int getSlotStackLimit() {

        if (this.type == EntityEquipmentSlot.OFFHAND) {

            return 64;

        } else {

            return 1;
        }
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        if (stack == ItemStack.EMPTY) {

            return false;

        } else if (this.type == EntityEquipmentSlot.OFFHAND) {

            return true;

        } else {

            return stack.getItem().isValidArmor(stack,
                    this.type, this.playerInv.player);
        }
    }

    @Override
    public ItemStack getStack() {

        switch (this.type) {

            case HEAD: {
                
                return this.playerInv.armorInventory.get(3);
            }

            case CHEST: {
                
                return this.playerInv.armorInventory.get(2);
            }

            case LEGS: {
                
                return this.playerInv.armorInventory.get(1);
            }
            
            case FEET: {
                
                return this.playerInv.armorInventory.get(0);
            }
            
            case OFFHAND: {
               
                return this.playerInv.offHandInventory.get(0);
            }
            
            default: {
                
                return ItemStack.EMPTY;
            }
        }
    }

    @Override
    public void putStack(ItemStack stack) {

        switch (this.type) {

            case HEAD: {

                this.playerInv.armorInventory.set(3, stack);
                break;
            }

            case CHEST: {

                this.playerInv.armorInventory.set(2, stack);
                break;
            }

            case LEGS: {

                this.playerInv.armorInventory.set(1, stack);
                break;
            }

            case FEET: {

                this.playerInv.armorInventory.set(0, stack);
                break;
            }

            case OFFHAND: {

                this.playerInv.offHandInventory.set(0, stack);
                break;
            }

            default: {

                break;
            }
        }
    }

    @Override
    public void onSlotChanged() {}

    @Override
    public ItemStack decrStackSize(int amount) {

        switch (this.type) {

            case HEAD: {

                return this.playerInv.armorInventory.get(3) ==
                        ItemStack.EMPTY ? ItemStack.EMPTY : ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 3, amount);
            }

            case CHEST: {

                return this.playerInv.armorInventory.get(2) ==
                        ItemStack.EMPTY ? ItemStack.EMPTY : ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 2, amount);
            }

            case LEGS: {

                return this.playerInv.armorInventory.get(1) ==
                        ItemStack.EMPTY ? ItemStack.EMPTY : ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 1, amount);
            }

            case FEET: {

                return this.playerInv.armorInventory.get(0) ==
                        ItemStack.EMPTY ? ItemStack.EMPTY : ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 0, amount);
            }

            case OFFHAND: {

                return this.playerInv.offHandInventory.get(0) ==
                        ItemStack.EMPTY ? ItemStack.EMPTY : ItemStackHelper
                        .getAndSplit(this.playerInv
                        .offHandInventory, 0, amount);
            }

            default: {

                return ItemStack.EMPTY;
            }
        }
    }

    @Override
    public boolean isHere(IInventory inv, int slot) {

        return false;
    }

    @Override
    public boolean isSameInventory(Slot other) {

        return false;
    }
}
