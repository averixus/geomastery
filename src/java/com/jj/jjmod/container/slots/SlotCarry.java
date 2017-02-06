package com.jj.jjmod.container.slots;

import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.DefaultCapInventory;
import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotCarry extends Slot {

    public CarryType type;
    public DefaultCapInventory capInv;

    public SlotCarry(EntityPlayer player, int x, int y, CarryType type) {

        super(null, 0, x, y);
        this.capInv = (DefaultCapInventory) player
                .getCapability(CapInventory.CAP_INVENTORY, null);
        this.type = type;
    }

    @Override
    public int getSlotStackLimit() {

        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        if (stack == ItemStack.EMPTY) {

            return false;

        } else {

            return this.type.isItemValid(stack);
        }
    }

    @Override
    public ItemStack getStack() {

        switch (this.type) {

            case BACKPACK: {
                
                return this.capInv.stacks.get(0);
            }
            
            case YOKE: {
               
                return this.capInv.stacks.get(1);
            }
            
            default: {
                
                return ItemStack.EMPTY;
            }
        }
    }

    @Override
    public void putStack(ItemStack stack) {

        switch (this.type) {

            case BACKPACK: {

                this.capInv.stacks.set(0, stack);
                break;
            }

            case YOKE: {

                this.capInv.stacks.set(1, stack);
                break;
            }
        }
    }

    @Override
    public void onSlotChanged() {}

    @Override
    public ItemStack decrStackSize(int amount) {

        switch (this.type) {

            case BACKPACK: {
               
                return this.capInv.stacks.get(0) == ItemStack.EMPTY ?
                        ItemStack.EMPTY
                        : this.capInv.stacks.get(0).splitStack(amount);
            }
            
            case YOKE: {
                
                return this.capInv.stacks.get(1) == ItemStack.EMPTY ?
                        ItemStack.EMPTY
                        : this.capInv.stacks.get(1).splitStack(amount);
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

    public enum CarryType {

        BACKPACK,
        YOKE;

        public boolean isItemValid(ItemStack stack) {

            Item item = stack.getItem();

            switch (this) {

                case BACKPACK: {
                    
                    return item == ModItems.backpack;
                }
                
                case YOKE: {
                   
                    return item == ModItems.yoke;
                }
                
                default: {
                    
                    return false;
                }
            }
        }
    }
}
