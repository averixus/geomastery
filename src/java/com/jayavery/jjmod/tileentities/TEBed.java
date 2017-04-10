package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockBed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/** TileEntity for custom bed blocks. */
public class TEBed extends TileEntity implements ITickable {
    
    public static final int MAX_USES = 20;

    /** The remaining number of times the player can sleep in
     * this bed before its durability runs out. */
    private int usesLeft = MAX_USES;

    @Override
    public void update() {

        if (this.world.getWorldTime() % 24000L == 0) {
            
            ((BlockBed) this.world.getBlockState(this.pos).getBlock())
                    .onMorning(this.world, this.pos);
        }
    }
    
    /** @return The current durability of this bed. */
    public int getUsesLeft() {

        return this.usesLeft;
    }

    /** Increments the current durability of this bed. */
    public void addUse() {

        this.usesLeft--;
    }

    /** Sets the current durability of this bed to the given damage. */
    public void setUsesLeft(int usesLeft) {

        this.usesLeft = usesLeft;
    }

    /** Checks whether this bed has reached its maximum durability.
     * @return Whether or not the bed has run out of uses. */
    public boolean isBroken() {

        return this.usesLeft <= 0;
    }
    
    /** Checks whether this bed is at its original durability.
     * @return Whether or not the bed is fully durable. */
    public boolean isUndamaged() {
        
        return this.usesLeft >= MAX_USES;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        
        super.writeToNBT(nbt);
        nbt.setInteger("usesLeft", this.usesLeft);
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        
        super.readFromNBT(nbt);
        this.usesLeft = nbt.getInteger("usesLeft");
    }
}
