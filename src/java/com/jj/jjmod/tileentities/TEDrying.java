package com.jj.jjmod.tileentities;

import com.jj.jjmod.crafting.CookingManager;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.packets.DryingPacketClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

/** TileEntity for Drying Rack block. */
public class TEDrying extends TileEntity implements ITickable {

    public final CookingManager recipes = ModRecipes.DRYING;
    private ItemStack input = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    private int drySpent;
    private int dryEach;

    /** @return The ItemStack in the input slot. */
    public ItemStack getInput() {
        
        return this.input;
    }
    
    /** @return The ItemStack in the output slot. */
    public ItemStack getOutput() {
        
        return this.output;
    }

    /** Sets the given stack to the input slot. */
    public void setInput(ItemStack stack) {
        
        if (ItemStack.areItemsEqual(stack, this.input)) {
            
            this.dryEach = this.getDryTime(stack);
            this.drySpent = 0;
            this.markDirty();
        }
        
        this.input = stack;
    }
    
    /** Sets the given stack to the output slot. */
    public void setOutput(ItemStack stack) {
        
        this.output = stack;
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
    
    /** @return The ticks needed to dry the given item. */
    public int getDryTime(ItemStack stack) {

        return 200;
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
                this.dryEach = getDryTime(this.input);
                this.dryItem();
                this.markDirty();
            }
                
        } else if (this.drySpent > 0) {

            this.drySpent = 0;
        }
        
        this.sendProgressPacket();
    }

    /** @return Whether the current input
     * can be dried to the current output. */
    private boolean canDry() {

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

    /** Turns an input into an output. */
    private void dryItem() {

        ItemStack result = this.recipes.getCookingResult(this.input);

        if (this.output.isEmpty()) {

            this.output = result.copy();

        } else if (ItemStack.areItemsEqual(this.input, result)) {

            this.output.grow(result.getCount());
        }

        this.input.shrink(1);
    }
    
    /** Sends an packet to update the progress bars on the Client. */
    private void sendProgressPacket() {
        
        if (this.world.isRemote) {
            
            return;
        }
        
        ModPackets.NETWORK.sendToAll(new DryingPacketClient(this.drySpent,
                this.dryEach, this.pos));
        
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);

        this.input = new ItemStack(compound.getCompoundTag("input"));
        this.output = new ItemStack(compound.getCompoundTag("output"));
        this.drySpent = compound.getInteger("drySpent");
        this.dryEach = compound.getInteger("dryEach");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);

        compound.setInteger("drySpent", this.drySpent);
        compound.setInteger("dryEach", this.dryEach);
        compound.setTag("input", this.input.writeToNBT(new NBTTagCompound()));
        compound.setTag("output", this.output.writeToNBT(new NBTTagCompound()));

        return compound;
    }
}
