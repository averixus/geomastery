package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

public class DefaultCapInventory implements ICapInventory {

    private static final float SPEED_MODIFIER = 43;
    
    public EntityPlayer player;
    public NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
    
  //  private IItemHandler backpackHandler = new ItemStackHandler();
  //  private IItemHandler yokeHandler = new ItemStackHandler();
    
    private boolean canRun = true;

    public DefaultCapInventory(EntityPlayer player) {

        this.player = player;
    }

    @Override
    public boolean canRun() {
        
        return this.canRun;
    }
    
    @Override
    public void update() {
        
        double speed = 4.3;
        this.canRun = true;
        
        for (ItemStack stack : this.player.inventory.armorInventory) {

            if (stack != null && stack.getItem() != null &&
                    stack.getItem() instanceof ItemArmor) {
                
                ItemArmor armor = (ItemArmor) stack.getItem();
                
                if (armor.getArmorMaterial() == EquipMaterial.LEATHER_APPAREL) {

                    speed -= 0.2;
                    
                } else if (armor.getArmorMaterial() == EquipMaterial.STEELMAIL_APPAREL) {

                    speed -= 0.4;
                    
                } else if (armor.getArmorMaterial() == EquipMaterial.STEELPLATE_APPAREL) {
                    
                    speed -= 0.5;
                }
            }
        }
        
        if (ModBlocks.OFFHAND_ONLY.contains(this.player.getHeldItemOffhand().getItem())) {
            
            speed -= 2.0;
            this.canRun = false;
        }
        
        if (this.stacks.get(0).getItem() == ModItems.backpack) {
            
            speed -= 0.5;
        }
        
        if (this.stacks.get(1).getItem() == ModItems.yoke) {
            
            speed -= 1.5;
            this.canRun = false;
        }
        
        if (speed < 2) {
            
            speed = 2;
        }
        
        float adjustedSpeed = (float) speed / SPEED_MODIFIER;
        this.player.capabilities.setPlayerWalkSpeed(adjustedSpeed);
    }
    
//    // Put stack in inventory, return amount remaining
//    public int add(ItemStack stack) {
//        
//        if (ModBlocks.OFFHAND_ONLY.contains(stack.getItem())) {
//            
//            return this.addToOffhand(stack);
//        }
//
//        int remaining = this.putInMatchingSlot(stack);
//
//        if (remaining == 0) {
//
//            return 0;
//        }
//
//        stack.shrink(remaining);
//        remaining = this.putInEmptySlot(stack);
//        
//        return remaining;
//    }
//
//    // Put stack in matching inventory slot/s and return amount remaining
//    private int putInMatchingSlot(ItemStack stack) {
//
//        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
//        int remaining = stack.getCount();
//
//        for (int slot = 0; slot < this.getInventorySize(); slot++) {
//
//            if (inv.get(slot) != ItemStack.EMPTY && inv.get(slot).getCount() == 0) {
//
//                inv.set(slot, ItemStack.EMPTY);
//            }
//
//            if (checkMatch(stack, inv.get(slot))) {
//
//                remaining = this.addToSlot(slot, stack);
//
//                if (remaining == 0) {
//
//                    return 0;
//                }
//            }
//        }
//
//        return remaining;
//    }
//
//    // Put the stack in empty inventory slot/s and return amount remaining
//    private int putInEmptySlot(ItemStack stack) {
//
//        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
//        int remaining = stack.getCount();
//
//        for (int slot = 0; slot < this.getInventorySize(); slot++) {
//
//            if (inv.get(slot) != ItemStack.EMPTY && inv.get(slot).getCount() == 0) {
//
//                inv.set(slot, ItemStack.EMPTY);
//            }
//
//            if (inv.get(slot) == ItemStack.EMPTY) {
//
//                remaining = this.addToSlot(slot, stack);
//
//                if (remaining == 0) {
//
//                    return 0;
//                }
//            }
//        }
//
//        return remaining;
//    }
//    
//    public int addToOffhand(ItemStack stack) {
//        
//        ItemStack inSlot = this.player.inventory.offHandInventory.get(0);
//        
//        if (inSlot == ItemStack.EMPTY) {
//            
//            this.player.inventory.offHandInventory.set(0, stack);
//            ((ContainerInventory) this.player.inventoryContainer).sendUpdateOffhand();
//            
//            return 0;
//        }
//        
//        return 1;
//    }
//
//    // Add the stack to the slot and return amount remaining
//    public int addToSlot(int slot, ItemStack stack) {
//
//        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
//        int result = stack.getCount();
//
//        int inSlot = inv.get(slot).getCount();
//
//        if (stack.getMaxStackSize() >= stack.getCount() + inSlot) {
//
//            ItemStack added = stack.copy();
//            added.setCount(stack.getCount() + inSlot);
//            inv.set(slot, added);
//            result = 0;
//
//        } else {
//
//            ItemStack added = stack.copy();
//            added.shrink(stack.getMaxStackSize());
//            inv.set(slot, added);
//            result -= stack.getMaxStackSize() + inSlot;
//        }
//
//        ((ContainerInventory) this.player.inventoryContainer)
//                .sendUpdateInventory(InvType.INVENTORY, slot, inv.get(slot));
//
//        return result;
//    }

    public int getInventoryRows() {

        int rows = 0;

        if (this.stacks.get(0).getItem() == ModItems.backpack) {

            rows += 1;
        }

        if (this.stacks.get(1).getItem() == ModItems.yoke) {

            rows += 2;
        }

        return rows;
    }

    public int getInventorySize() {

        return 9 + (9 * this.getInventoryRows());
    }

    public static boolean checkMatch(ItemStack stack1, ItemStack stack2) {

        if (stack1 == ItemStack.EMPTY || stack2 == ItemStack.EMPTY) {

            return false;
        }

        boolean canStack = stack1.isStackable() &&
                (stack1.getCount() < stack1.getMaxStackSize());
        boolean sameItem = stack1.getItem() == stack2.getItem();
        boolean sameMeta = !stack1.getHasSubtypes() ||
                (stack1.getMetadata() == stack2.getMetadata());
        boolean sameTags = ItemStack.areItemStackTagsEqual(stack1, stack2);

        return canStack && sameItem && sameMeta && sameTags;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        NBTTagCompound nbt = new NBTTagCompound();

        if (!this.stacks.get(0).isEmpty()) {

            NBTTagCompound backpack = new NBTTagCompound();
            this.stacks.get(0).writeToNBT(backpack);
            nbt.setTag("backpack", backpack);
        }

        if (!this.stacks.get(1).isEmpty()) {

            NBTTagCompound yoke = new NBTTagCompound();
            this.stacks.get(1).writeToNBT(yoke);
            nbt.setTag("yoke", yoke);
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        if (nbt.hasKey("backpack")) {

            NBTTagCompound backpack = nbt.getCompoundTag("backpack");
            this.stacks.set(0, new ItemStack(backpack));
        }

        if (nbt.hasKey("yoke")) {

            NBTTagCompound yoke = nbt.getCompoundTag("yoke");
            this.stacks.set(1, new ItemStack(yoke));
        }
    }
}
