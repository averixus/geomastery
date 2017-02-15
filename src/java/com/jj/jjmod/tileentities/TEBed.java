package com.jj.jjmod.tileentities;

import net.minecraft.tileentity.TileEntity;

/** TileEntity for custom Bed blocks. */
public class TEBed extends TileEntity {

    /** The remaining number of times the player can sleep in
     * this Bed before its durability runs out. */
    private int usesLeft;

    /** @return The current durability of this Bed. */
    public int getUsesLeft() {

        return this.usesLeft;
    }

    /** Increments the current durability of this Bed. */
    public void addUse() {

        this.usesLeft--;
    }

    /** Sets the current durability of this Bed to the given damage. */
    public void setUsesLeft(int usesLeft) {

        this.usesLeft = usesLeft;
    }

    /** Checks whether this Bed has reached its maximum durability.
     * @return Whether or not the Bed has run out of uses. */
    public boolean isBroken() {

        return this.usesLeft <= 0;
    }
}
