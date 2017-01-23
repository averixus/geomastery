package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;
import com.jj.jjmod.crafting.CookingManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class TEFurnaceAbstract extends TileEntityLockable
        implements ITickable, IInventory {

    public NonNullList<ItemStack> stacks = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);
    public int fuelLeft;
    public int fuelEach;
    public int cookSpent;
    public int cookEach;
    
    public final CookingManager recipes;

    public TEFurnaceAbstract(CookingManager recipes) {

        this.recipes = recipes;
    }
    
    public boolean isHeating() {
        
        return true;
    }

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

        // Check if the same item
        boolean same = stack != null &&
                stack.isItemEqual(this.stacks.get(index)) &&
                ItemStack.areItemStackTagsEqual(stack, this.stacks.get(index));
        this.stacks.set(index, stack);

        // Make sure the stack is not illegal size
        if (stack != null &&
                stack.func_190916_E() > this.getInventoryStackLimit()) {

            stack.func_190920_e(this.getInventoryStackLimit());
        }

        // If a new item and in the cooking slot then update cook info
        if (index == 0 && !same) {

            this.cookEach = getCookTime(stack);
            this.cookSpent = 0;
            this.markDirty();
        }
    }

    public abstract int getFuelTime(ItemStack stack);

    public abstract int getCookTime(ItemStack stack);

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);

        NBTTagList taglist = compound.getTagList("stacks", 10);
        this.stacks = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);

        for (int i = 0; i < taglist.tagCount(); ++i) {

            NBTTagCompound tagcompound = taglist.getCompoundTagAt(i);
            int j = tagcompound.getByte("slot");

            if (j >= 0 && j < this.stacks.size()) {

                this.stacks.set(j, new ItemStack(tagcompound));
            }
        }

        this.fuelLeft = compound.getInteger("fuelLeft");
        this.cookSpent = compound.getInteger("cookSpent");
        this.cookEach = compound.getInteger("cookEach");
        this.fuelEach = getFuelTime(this.stacks.get(1));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        compound.setInteger("fuelLeft", this.fuelLeft);
        compound.setInteger("cookSpent", this.cookSpent);
        compound.setInteger("cookEach", this.cookEach);

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

    public boolean isBurning() {

        return this.fuelLeft > 0;
    }

    @Override
    public void update() {

        boolean isDirty = false;

        // Reduce cook time
        if (this.isBurning()) {

            this.fuelLeft--;
        }

        // Do nothing if wrong side
        if (this.worldObj.isRemote) {

            return;
        }

        // If ready to cook
        if (this.canSmelt() && this.stacks.get(0) != null && this.stacks.get(1) != null) {

            // If already cooking
            if (this.isBurning()) {

                this.cookSpent++;

                // If finished cooking
                if (this.cookSpent == this.cookEach) {

                    this.cookSpent = 0;
                    this.cookEach = getCookTime(this.stacks.get(0));
                    this.smeltItem();
                    isDirty = true;
                }
            
            // If not already cooking
            } else {

                // Start new cook
                this.fuelEach = getFuelTime(this.stacks.get(1));
                this.fuelLeft = this.fuelEach;
                this.stacks.get(1).func_190920_e(1);

                // If used last item
                if (this.stacks.get(0).func_190916_E() == 0) {

                    this.stacks.set(1, this.stacks.get(1).getItem()
                            .getContainerItem(this.stacks.get(1)));
                }

                isDirty = true;
            }
        }

        // If cooking from last fuel?
        if (!this.isBurning() && this.cookSpent > 0) {

            this.cookSpent =
                    MathHelper.clamp_int(this.cookSpent - 2, 0, this.cookEach);
        }

        // If dirty
        if (isDirty) {

            this.markDirty();
        }
    }

    protected boolean canSmelt() {

        // If no input
        if (this.stacks.get(0) == null) {

            return false;
        }

        ItemStack result = this.recipes.getSmeltingResult(this.stacks.get(0));

        if (result == null) {

            return false;
        }

        if (this.stacks.get(2) == null) {

            return true;
        }

        boolean outputCorrect = this.stacks.get(2).isItemEqual(result);
        int output = this.stacks.get(2).func_190916_E() + result.func_190916_E();
        boolean hasRoom = output < getInventoryStackLimit() &&
                output < this.stacks.get(2).getMaxStackSize();

        return outputCorrect && hasRoom;
    }

    public void smeltItem() {

        // If can't cook
        if (!this.canSmelt()) {

            return;
        }

        ItemStack result = this.recipes.getSmeltingResult(this.stacks.get(0));

        // If output empty
        if (this.stacks.get(2) == null) {

            this.stacks.set(2, result.copy());

        // If output contains same as result
        } else if (this.stacks.get(2).getItem() == result.getItem()) {

            this.stacks.get(2).func_190917_f(result.func_190916_E());
        }

        this.stacks.get(0).func_190918_g(1);

        if (this.stacks.get(0).func_190916_E() <= 0) {

            this.stacks.set(0, ItemStack.field_190927_a);
        }
    }

    public boolean isItemFuel(ItemStack stack) {

        return getFuelTime(stack) > 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {

        return this.worldObj.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq(this.pos.getX() + 0.5D,
                        this.pos.getY() + 0.5D,
                        this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public abstract Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player);

    @Override
    public int getField(int id) {

        switch (id) {

            case 0: {
                
                return this.fuelLeft;
            }
            
            case 1: {
                
                return this.fuelEach;
            }
            
            case 2: {
                
                return this.cookSpent;
            }
            
            case 3: {
                
                return this.cookEach;
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
                
                this.fuelLeft = value;
                break;
            }
            
            case 1: {
                
                this.fuelEach = value;
                break;
            }
            
            case 2: {
                
                this.cookSpent = value;
                break;
            }
            
            case 3: {
                
                this.cookEach = value;
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
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {

        return true;
    }

    @Override
    public int getFieldCount() {

        return 4;
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
    
    // Unknown?
    @Override
    public boolean func_191420_l() {

        return false;
    }
}
