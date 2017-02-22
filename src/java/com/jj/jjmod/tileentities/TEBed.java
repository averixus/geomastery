package com.jj.jjmod.tileentities;

import net.minecraft.tileentity.TileEntity;

/** TileEntity for custom bed blocks. */
public class TEBed extends TileEntity {

    /** The remaining number of times the player can sleep in
     * this bed before its durability runs out. */
    private int usesLeft;

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
}
