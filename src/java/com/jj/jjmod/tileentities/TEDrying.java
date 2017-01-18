package com.jj.jjmod.tileentities;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import com.jj.jjmod.crafting.CookingManager;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerDrying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TEDrying extends TileEntityLockable
        implements ITickable, IInventory {

    public static final CookingManager RECIPES = ModRecipes.DRYING;

    public List<ItemStack> stacks = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);
    public int drySpent;
    public int dryEach;

    @Override
    @Nullable
    public ItemStack getStackInSlot(int index) {

        return this.stacks.get(index);
    }

    @Override
    @Nullable
    public ItemStack decrStackSize(int index, int count) {

        return ItemStackHelper.getAndSplit(this.stacks, index, count);
    }

    @Override
    @Nullable
    public ItemStack removeStackFromSlot(int index) {

        return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    public void setInventorySlotContents(int index,
            @Nullable ItemStack stack) {

        boolean same = stack != null &&
                stack.isItemEqual(this.stacks.get(index)) &&
                ItemStack.areItemStackTagsEqual(stack, this.stacks.get(index));
        this.stacks.set(index, stack);

        if (stack != null && stack.func_190916_E() > this
                .getInventoryStackLimit()) {

            stack.func_190920_e(this.getInventoryStackLimit());
        }

        if (index == 0 && !same) {

            this.dryEach = getDryTime(stack);
            this.drySpent = 0;
            this.markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);

        NBTTagList taglist = compound.getTagList("stacks", 10);
        this.stacks =  NonNullList.<ItemStack>func_191197_a(this.getSizeInventory(), ItemStack.field_190927_a);;

        for (int i = 0; i < taglist.tagCount(); ++i) {

            NBTTagCompound tagcompound = taglist.getCompoundTagAt(i);
            int j = tagcompound.getByte("slot");

            if (j >= 0 && j < this.stacks.size()) {

                this.stacks.set(j, new ItemStack(tagcompound));
            }
        }

        this.drySpent = compound.getInteger("drySpent");
        this.dryEach = compound.getInteger("dryEach");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        compound.setInteger("drySpent", this.drySpent);
        compound.setInteger("dryEach", this.dryEach);

        NBTTagList taglist = new NBTTagList();

        for (int i = 0; i < this.stacks.size(); ++i) {
            
            if (this.stacks.get(i) != null) {
                
                NBTTagCompound tagcompound = new NBTTagCompound();
                tagcompound.setByte("slot", (byte) i);
                this.stacks.get(i).writeToNBT(tagcompound);
                taglist.appendTag(tagcompound);
            }
        }

        compound.setTag("stacks", taglist);
        return compound;
    }

    @Override
    public void update() {

        boolean isDirty = false;

        if (this.worldObj.isRemote) {

            return;
        }

        // If ready to dry
        if (this.canDry() && this.stacks.get(0) != null) {

            // If already drying
            if (this.isDrying()) {

                this.drySpent++;

                // If finished drying
                if (this.drySpent == this.dryEach) {

                    this.drySpent = 0;
                    this.dryEach = getDryTime(this.stacks.get(0));
                    this.dryItem();
                    isDirty = true;
                }
                
            // If not already drying
            } else {

                // Start new dry
                this.drySpent = 1;

                // If used last item
                if (this.stacks.get(0).func_190916_E() == 0) {

                    this.stacks.set(0, this.stacks.get(0)
                            .getItem().getContainerItem(this.stacks.get(1)));
                }

                isDirty = true;
            }
        }

        // If dirty
        if (isDirty) {

            this.markDirty();
        }
    }

    private boolean isDrying() {

        return this.drySpent != 0;
    }

    private boolean canDry() {

        // If no input
        if (this.stacks.get(0) == null) {

            return false;
        }

        ItemStack result = RECIPES.getSmeltingResult(this.stacks.get(0));

        // Check recipe and space
        if (result == null) {

            return false;
        }

        if (this.stacks.get(1) == null) {

            return true;
        }

        boolean outputCorrect = this.stacks.get(1).isItemEqual(result);
        int output = this.stacks.get(1).func_190916_E() + result.func_190916_E();
        boolean hasRoom = output < getInventoryStackLimit() &&
                output < this.stacks.get(1).getMaxStackSize();

        return outputCorrect && hasRoom;
    }

    public void dryItem() {

        // If can't dry
        if (!this.canDry()) {

            return;
        }

        ItemStack result = RECIPES.getSmeltingResult(this.stacks.get(0));

        // If output empty
        if (this.stacks.get(1) == null) {

            this.stacks.set(1, result.copy());

        // If output contains same as result
        } else if (this.stacks.get(1).getItem() == result.getItem()) {

            this.stacks.get(1).func_190917_f(result.func_190916_E());
        }

        this.stacks.get(0).func_190918_g(1);
        
        if (this.stacks.get(0).func_190916_E() <= 0) {

            this.stacks.set(0, ItemStack.field_190927_a);
        }
    }

    public int getDryTime(ItemStack stack) {

        // CONFIG drying rack dry times

        Item item = stack.getItem();

        if (item == null) {

            return 0;
        }

        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {

        return this.worldObj.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D,
                        (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {

        return new ContainerDrying(player, this.worldObj, this);
    }

    @Override
    public int getField(int id) {

        switch (id) {

            case 0: {
                
                return this.drySpent;
            }
                
            case 1: {

                return this.dryEach;
            }
            
            default: {
                
                return 0;
            }
        }
    }

    @Override
    public void setField(int id, int value) {

        switch (id) {

            case 0: {
                
                this.drySpent = value;
                break;
            }
            
            case 1: {
                
                this.dryEach = value;
                break;
            }
        }
    }

    @Override
    public int getSizeInventory() {

        return this.stacks.size();
    }

    @Override
    public int getInventoryStackLimit() {

        return 64;
    }

    @Override
    public void clear() {

        for (int i = 0; i < this.stacks.size(); i++) {

            this.stacks.set(i, ItemStack.field_190927_a);
        }
    }

    @Override
    public int getFieldCount() {

        return 2;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {

        return true;
    }

    @Override
    public String getName() {

        return null;
    }

    @Override
    public boolean hasCustomName() {

        return false;
    }

    @Override
    public String getGuiID() {

        return null;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    // Unknown?
    @Override
    public boolean func_191420_l() {

        return false;
    }
}
