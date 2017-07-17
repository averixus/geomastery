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
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketDrying;
import jayavery.geomastery.utilities.IItemStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

/** TileEntity for drying rack block. */
public class TEDrying extends TileEntity implements ITickable, IItemStorage {

    /** Recipes for this drying rack. */
    public static final CookingManager RECIPES = GeoRecipes.DRYING;
    
    /** Comparator to move all empty stacks to the end of a list. */
    private static final Comparator<ItemStack> SORTER =
            (s1, s2) -> s1.isEmpty() ? 1 : s2.isEmpty() ? -1 : 0;
    
    /** This drying rack's input stacks. */
    private NonNullList<ItemStack> inputs = NonNullList
            .withSize(2, ItemStack.EMPTY);
    /** This drying rack's output stacks. */
    private NonNullList<ItemStack> outputs = NonNullList
            .withSize(2, ItemStack.EMPTY);
    /** Ticks spent drying the current item. */
    private int drySpent = 0;
    /** Total ticks needed to dry the current item. */
    private int dryEach = -1;

    /** Sorts the list of input stacks. */
    public void sort() {

        Collections.sort(this.inputs, SORTER);
        int newDryEach = RECIPES.getCookingTime(this.inputs.get(0));
        
        if (this.dryEach != newDryEach) {
            
            this.dryEach = newDryEach;
            this.drySpent = 0;
            this.markDirty();
        }
    }
    
    @Override
    public List<ItemStack> getDrops() {
        
        List<ItemStack> result = Lists.newArrayList();
        result.addAll(this.inputs);
        result.addAll(this.outputs);
        return result;
    }

    /** @return The ItemStack in the input slot. */
    public ItemStack getInput(int index) {
        
        return this.inputs.get(index);
    }
    
    /** @return The ItemStack in the output slot. */
    public ItemStack getOutput(int index) {
        
        return this.outputs.get(index);
    }

    /** Sets the given stack to the input slot. */
    public void setInput(ItemStack stack, int index) {

        this.inputs.set(index, stack);
    }
    
    /** Sets the given stack to the output slot. */
    public void setOutput(ItemStack stack, int index) {
        
        this.outputs.set(index, stack);
    }
    
    /** @return The ticks spent drying the current item. */
    public int getDrySpent() {
        
        return this.drySpent;
    }
    
    /** @return The ticks needed to dry the current item. */
    public int getDryEach() {
        
        return this.dryEach;
    }
    
    /** Sets the given progress values. */
    public void setProgressBars(int drySpent, int dryEach) {
        
        this.drySpent = drySpent;
        this.dryEach = dryEach;
    }

    @Override
    public void update() {
        
        if (this.world.isRemote) {

            return;
        }
        
        if (this.canDry()) {

            if (this.drySpent < this.dryEach) {

                this.drySpent++;
                
            } else if (this.drySpent == this.dryEach) {

                this.drySpent = 0;
                this.dryEach = RECIPES.getCookingTime(this.inputs.get(0));
                this.dryItem();
                this.markDirty();
            }
                
        } else if (this.drySpent > 0) {

            this.drySpent = 0;
        }
        
        this.sendProgressPacket();
    }

    /** @return Whether the current input can be dried to the current output. */
    private boolean canDry() {

        if (this.inputs.get(0).isEmpty()) {

            return false;
        }

        ItemStack result = RECIPES.getCookingResult(this.inputs.get(0),
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
            boolean hasRoom = outputCount <= output.getMaxStackSize();
            
            if (outputCorrect && hasRoom) {
                
                return true;
            }
        }
        
        return false;
    }

    /** Turns an input into an output. */
    private void dryItem() {

        ItemStack result = RECIPES.getCookingResult(this.inputs.get(0),
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
    
    /** Sends a packet to update the progress bars on the client. */
    private void sendProgressPacket() {
        
        if (!this.world.isRemote) {

            Geomastery.NETWORK.sendToAll(new CPacketDrying(this.drySpent,
                    this.dryEach, this.pos));
        }
    }
    
    // Required to update GUI on the client. 
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    // Required to update GUI on the client.
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    // Required to update GUI on the client.
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
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

        this.drySpent = compound.getInteger("drySpent");
        this.dryEach = compound.getInteger("dryEach");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        compound.setInteger("drySpent", this.drySpent);
        compound.setInteger("dryEach", this.dryEach);
        
        for (int i = 0; i < this.inputs.size(); i++) {

            compound.setTag("input" + i, this.inputs.get(i)
                    .writeToNBT(new NBTTagCompound()));
        }
        
        for (int i = 0; i < this.outputs.size(); i++) {
            
            compound.setTag("output" + i, this.outputs.get(i)
                    .writeToNBT(new NBTTagCompound()));
        }
        
        return compound;
    }
}
