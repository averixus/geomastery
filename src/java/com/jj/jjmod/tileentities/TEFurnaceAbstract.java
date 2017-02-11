package com.jj.jjmod.tileentities;

import com.jj.jjmod.crafting.CookingManager;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.packets.FurnacePacketClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

/** Abstract superclass TileEntity for Furnace blocks. */
public abstract class TEFurnaceAbstract extends TileEntity
        implements ITickable {

    public final CookingManager recipes;

    protected ItemStack input = ItemStack.EMPTY;
    protected ItemStack fuel = ItemStack.EMPTY;
    protected ItemStack output = ItemStack.EMPTY;
    protected int fuelLeft;
    protected int fuelEach;
    protected int cookSpent;
    protected int cookEach;
    
    public TEFurnaceAbstract(CookingManager recipes) {

        this.recipes = recipes;
    }
    
    /** @return The ItemStack in the input slot. */
    public ItemStack getInput() {
        
        return this.input;
    }
    
    /** @return The ItemStack in the fuel slot. */
    public ItemStack getFuel() {
        
        return this.fuel;
    }
    
    /** @return The ItemStack in the output slot. */
    public ItemStack getOutput() {
        
        return this.output;
    }
    
    /** @return The ticks of burning left from the current fuel item. */
    public int getFuelLeft() {
        
        return this.fuelLeft;
    }
    
    /** @return The total ticks of burning from the current fuel item. */
    public int getFuelEach() {
        
        return this.fuelEach;
    }
    
    /** @return The ticks the current item has been cooking for. */
    public int getCookSpent() {
        
        return this.cookSpent;
    }
    
    /** @return The total ticks for the current item to cook. */
    public int getCookEach() {
        
        return this.cookEach;
    }
    
    /** Sets the progress amounts to the given values. */
    public void setProgressBars(int fuelLeft, int fuelEach,
            int cookSpent, int cookEach) {

        this.fuelLeft = fuelLeft;
        this.fuelEach = fuelEach;
        this.cookSpent = cookSpent;
        this.cookEach = cookEach;
    }
    
    /** Sets the ItemStack to the input slot. */
    public void setInput(ItemStack stack) {
        
        if (!ItemStack.areItemsEqual(stack, this.input)) {
            
            this.cookEach = this.getCookTime(stack);
            this.cookSpent = 0;
            this.markDirty();
        }
        
        this.input = stack;
    }
    
    /** Sets the ItemStack to the fuel slot. */
    public void setFuel(ItemStack stack) {
        
        this.fuel = stack;
    }
    
    /** Sets the ItemStack to the output slot. */
    public void setOutput(ItemStack stack) {
        
        this.output = stack;
    }

    /** @return The ticks the current fuel item can burn for. */
    public int getFuelTime(ItemStack stack) {
        
        return this.recipes.getFuelTime(stack);
    }

    /** @return The ticks the current input item takes to cook. */
    public abstract int getCookTime(ItemStack stack);

    /** @return Whether this Furnace is currently heating. */
    public boolean isBurning() {

        return this.fuelLeft > 0;
    }
    
    /** @return Whether the ItemStack is a fuel for this Furnace. */
    public boolean isItemFuel(ItemStack stack) {

        return getFuelTime(stack) > 0;
    }

    @Override
    public void update() {

        if (this.world.isRemote) {

            return;
        }
        

        boolean isDirty = false;
        
        // Fuel progress
        if (this.fuelLeft > 0) {
            
            this.fuelLeft--;
            
        } else if (this.canCook()) {
            
            this.fuelEach = this.getFuelTime(this.fuel);
            this.fuelLeft = this.fuelEach;
            this.fuel.shrink(1);
            isDirty = true;
        }
        
        // Cook progress
        if (this.canCook() && this.fuelLeft > 0) {

            if (this.cookSpent < this.cookEach) {

                this.cookSpent++;
                
            } else if (this.cookSpent == this.cookEach) {

                this.cookSpent = 0;
                this.cookEach = this.getCookTime(this.input);
                this.cookItem();
                isDirty = true;
            }
        }

        // Cook progress reverses if no fuel
        if (!this.isBurning() && this.cookSpent > 0) {

            this.cookSpent = MathHelper.clamp(this.cookSpent - 2,
                    0, this.cookEach);
        }

        if (isDirty) {

            this.markDirty();
        }
        
        this.sendProgressPacket();
    }

    /** @return Whether the current input can cook into the current output. */
    protected boolean canCook() {

        if (this.input.isEmpty()) {

            return false;
        }

        ItemStack result = this.recipes.getCookingResult(this.input);

        if (result.isEmpty()) {

            return false;
        }

        if (this.output.isEmpty()) {

            return true;
        }

        boolean outputCorrect = this.output.isItemEqual(result);
        int output = this.output.getCount() + result.getCount();
        boolean hasRoom = output < this.output.getMaxStackSize();

        return outputCorrect && hasRoom;
    }

    /** Cooks one input item. */
    protected void cookItem() {

        ItemStack result = this.recipes.getCookingResult(this.input);

        if (this.output.isEmpty()) {

            this.output = result.copy();

        } else if (ItemStack.areItemsEqual(this.output, result)) {

            this.output.grow(result.getCount());
        }

        this.input.shrink(1);
    }

    /** Sends a packet to update the progress bars on the Client. */
    protected void sendProgressPacket() {
        
        if (this.world.isRemote) {
            
            return;
        }

        ModPackets.NETWORK.sendToAll(new FurnacePacketClient(this.fuelLeft,
                this.fuelEach, this.cookSpent, this.cookEach, this.pos));
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        
        this.input = new ItemStack(compound.getCompoundTag("input"));
        this.fuel = new ItemStack(compound.getCompoundTag("fuel"));
        this.output = new ItemStack(compound.getCompoundTag("output"));

        this.fuelLeft = compound.getInteger("fuelLeft");
        this.cookSpent = compound.getInteger("cookSpent");
        this.cookEach = compound.getInteger("cookEach");
        this.fuelEach = compound.getInteger("fuelEach");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        compound.setInteger("fuelLeft", this.fuelLeft);
        compound.setInteger("cookSpent", this.cookSpent);
        compound.setInteger("cookEach", this.cookEach);
        compound.setInteger("fuelEach", this.fuelEach);
        
        compound.setTag("input", this.input.writeToNBT(new NBTTagCompound()));
        compound.setTag("fuel", this.fuel.writeToNBT(new NBTTagCompound()));
        compound.setTag("output", this.output.writeToNBT(new NBTTagCompound()));

        return compound;
    }
}
