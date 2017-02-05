package com.jj.jjmod.container.slots;

import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.DefaultCapInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArmour extends Slot {

    public EntityEquipmentSlot type;
    public EntityPlayer player;
    public InventoryPlayer playerInv;

    public SlotArmour(InventoryPlayer playerInv, EntityPlayer player, int x,
            int y, EntityEquipmentSlot type) {

        super(null, 0, x, y);
        this.type = type;
        this.player = player;
        this.playerInv = playerInv;
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

        if (stack == ItemStack.field_190927_a) {

            return false;

        } else if (this.type == EntityEquipmentSlot.OFFHAND) {

            return true;

        } else {

            return stack.getItem().isValidArmor(stack, this.type, this.player);
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
                
                return ItemStack.field_190927_a;
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
                        ItemStack.field_190927_a ? ItemStack.field_190927_a :
                        ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 3, amount);
            }

            case CHEST: {

                return this.playerInv.armorInventory.get(2) ==
                        ItemStack.field_190927_a ? ItemStack.field_190927_a :
                        ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 2, amount);
            }

            case LEGS: {

                return this.playerInv.armorInventory.get(1) ==
                        ItemStack.field_190927_a ? ItemStack.field_190927_a :
                        ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 1, amount);
            }

            case FEET: {

                return this.playerInv.armorInventory.get(0) ==
                        ItemStack.field_190927_a ? ItemStack.field_190927_a :
                        ItemStackHelper
                        .getAndSplit(this.playerInv.armorInventory, 0, amount);
            }

            case OFFHAND: {

                return this.playerInv.offHandInventory.get(0) ==
                        ItemStack.field_190927_a ? ItemStack.field_190927_a :
                        ItemStackHelper
                        .getAndSplit(this.playerInv.offHandInventory, 0, amount);
            }

            default: {

                return ItemStack.field_190927_a;
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
