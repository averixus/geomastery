/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.crafting.CookingManager;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketFurnace;
import jayavery.geomastery.utilities.IItemStorage;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

/** Abstract superclass TileEntity for furnace blocks. */
public abstract class TEFurnaceAbstract<E extends Enum<E> & IMultipart>
        extends TEMultiAbstract<E> implements ITickable, IItemStorage {

    /** Comparator to move all empty stacks to the end of a list. */
    private static final Comparator<ItemStack> SORTER =
            (s1, s2) -> s1.isEmpty() ? 1 : s2.isEmpty() ? -1 : 0;

    /** Recipes for this furnace. */
    public final CookingManager recipes;
    /** Size of inputs, outputs, and fuels for this furnace. */
    protected final int size;
    /** This furnace's input stacks. */
    protected NonNullList<ItemStack> inputs;
    /** This funace's fuel stacks. */
    protected NonNullList<ItemStack> fuels;
    /** This furnace's output stacks. */
    protected NonNullList<ItemStack> outputs;
    /** Ticks of burning left for the current fuel. */
    protected int fuelLeft = 0;
    /** Total ticks of burning for each item of current fuel. */
    protected int fuelEach = -1;
    /** Ticks spent cooking the current input. */
    protected int cookSpent = 0;
    /** Total ticks to cook each item of the current input. */
    protected int cookEach = -1;
    
    public TEFurnaceAbstract(CookingManager recipes, int size) {

        this.recipes = recipes;
        this.inputs = NonNullList.withSize(size, ItemStack.EMPTY);
        this.fuels = NonNullList.withSize(size, ItemStack.EMPTY);
        this.outputs = NonNullList.withSize(size, ItemStack.EMPTY);
        this.size = size;
    }
    
    /** @return The number of input, fuel, and output slots of this furnace. */
    public int size() {
        
        return this.size;
    }
    
    @Override
    public List<ItemStack> getDrops() {

        List<ItemStack> result = Lists.newArrayList();
        result.addAll(this.inputs);
        result.addAll(this.outputs);
        result.addAll(this.fuels);
        return result;
    }
    
    /** Sorts the input and fuel stacks. */
    public void sort() {
        
        Collections.sort(this.inputs, SORTER);
        Collections.sort(this.fuels, SORTER);
        
        int newCookEach = this.recipes.getCookingTime(this.inputs.get(0));
        
        if (this.cookEach != newCookEach) {
            
            this.cookEach = newCookEach;
            this.cookSpent = 0;
            this.markDirty();
        }
        
        int newFuelEach = this.recipes.getFuelTime(this.fuels.get(0));
        
        if (this.fuelEach != newFuelEach) {
            
            this.fuelEach = newFuelEach;
          //  this.fuelLeft = this.fuelEach;
            this.markDirty();
        }
    }
    
    /** @return The ItemStack in the input slot. */
    public ItemStack getInput(int index) {
        
        return this.inputs.get(index);
    }
    
    /** @return The ItemStack in the fuel slot. */
    public ItemStack getFuel(int index) {
        
        return this.fuels.get(index);
    }
    
    /** @return The ItemStack in the output slot. */
    public ItemStack getOutput(int index) {
        
        return this.outputs.get(index);
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
    public void setInput(ItemStack stack, int index) {
        
        this.inputs.set(index, stack);
        this.sort();
    }
    
    /** Sets the ItemStack to the fuel slot. */
    public void setFuel(ItemStack stack, int index) {
        
        this.fuels.set(index, stack);
        this.sort();
    }
    
    /** Sets the ItemStack to the output slot. */
    public void setOutput(ItemStack stack, int index) {
        
        this.outputs.set(index, stack);
    }

    /** @return Whether this furnace is currently producing heat. */
    public boolean isHeating() {

        return true;
    }
    
    /** @return Whether this furnace is currently using fuel. */
    public boolean isUsingFuel() {
        
        return this.fuelLeft > 0;
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
            
            this.fuelEach = this.recipes.getFuelTime(this.fuels.get(0));
            this.fuelLeft = this.fuelEach;
            this.fuels.get(0).shrink(1);
            isDirty = true;
        }
        
        // Cook progress
        if (this.canCook() && this.fuelLeft > 0) {

            if (this.cookSpent < this.cookEach) {

                this.cookSpent++;
                
            } else if (this.cookSpent == this.cookEach) {

                this.cookSpent = 0;
                this.cookEach = this.recipes.getCookingTime(this.inputs.get(0));
                this.cookItem();
                isDirty = true;
            }
        }

        // Cook progress reverses if no fuel
        if (!this.isUsingFuel() && this.cookSpent > 0) {

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

        if (this.inputs.get(0).isEmpty()) {

            return false;
        }

        ItemStack result = this.recipes.getCookingResult(this.inputs.get(0),
                this.world);

        if (result.isEmpty()) {

            return false;
        }
        
        for (ItemStack output : this.outputs) {

            if (output.isEmpty()) {
    
                return true;
            }
    
            boolean outputCorrect = output.isItemEqual(result);
            int outputCount = output.getCount() + result.getCount();
            boolean hasRoom = outputCount < output.getMaxStackSize();
    
            if (outputCorrect && hasRoom) {
                
                return true;
            }
        }
        
        return false;
    }

    /** Cooks one input item. */
    protected void cookItem() {

        ItemStack result = this.recipes.getCookingResult(this.inputs.get(0),
                this.world);

        for (int i = 0; i < this.outputs.size(); i++) {
            
            ItemStack output = this.outputs.get(i);
            
            if (ItemStack.areItemsEqual(result, output) &&
                    (output.getCount() + result.getCount())
                    <= output.getMaxStackSize()) {
                
                output.grow(result.getCount());
                this.inputs.get(0).shrink(1);
                this.sort();
                return;
            }
        }
        
        for (int i = 0; i < this.outputs.size(); i++) {
            
            ItemStack output = this.outputs.get(i);
            
            if (output.isEmpty()) {
                
                result = result.copy();
                
                if (result.hasCapability(GeoCaps.CAP_DECAY, null)) {
                    
                    result.getCapability(GeoCaps.CAP_DECAY, null)
                            .setBirthTime(this.world.getTotalWorldTime());
                }
                
                this.outputs.set(i, result);
                this.inputs.get(0).shrink(1);
                this.sort();
                return;
            }
        }
    }

    /** Sends a packet to update the progress bars on the Client. */
    protected void sendProgressPacket() {
        
        if (!this.world.isRemote) {
            
            Geomastery.NETWORK.sendToAll(new CPacketFurnace(this.fuelLeft,
                    this.fuelEach, this.cookSpent, this.cookEach, this.pos));
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        
        for (int i = 0; i < this.inputs.size(); i++) {
            
            this.setInput(new ItemStack(compound
                    .getCompoundTag("input" + i)), i);
        }
        
        for (int i = 0; i < this.outputs.size(); i++) {
            
            this.setOutput(new ItemStack(compound
                    .getCompoundTag("output" + i)), i);
        }
        
        for (int i = 0; i < this.fuels.size(); i++) {
            
            this.setFuel(new ItemStack(compound
                    .getCompoundTag("fuel" + i)), i);
        }

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
        
        for (int i = 0; i < this.inputs.size(); i++) {
            
            compound.setTag("input" + i, this.getInput(i)
                    .writeToNBT(new NBTTagCompound()));
        }
        
        for (int i = 0; i < this.outputs.size(); i++) {
            
            compound.setTag("output" + i, this.getOutput(i)
                    .writeToNBT(new NBTTagCompound()));
        }
        
        for (int i = 0; i < this.fuels.size(); i++) {
            
            compound.setTag("fuel" + i, this.getFuel(i)
                    .writeToNBT(new NBTTagCompound()));
        }

        return compound;
    }
}
