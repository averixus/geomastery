package com.jj.jjmod.tileentities;

import net.minecraft.tileentity.TileEntity;

/** TileEntity for custom Bed blocks. */
public class TEBed extends TileEntity {

    /** The maximum number of times the player can sleep in
     * this Bed before its durability runs out. */
    private int maxUses;
    private int uses = 0;

    public TEBed(int maxUses) {

        this.maxUses = maxUses;
    }

    /** @return The current durability of this Bed. */
    public int getDropDamage() {

        return this.uses;
    }

    /** Increments the current durability of this Bed. */
    public void addUse() {

        this.uses++;
    }

    /** Sets the current durability of this Bed to the given damage. */
    public void setDropDamage(int damage) {

        this.uses = damage;
    }

    /** Checks whether this Bed has reached its maximum durability.
     * @return Whether or not the Bed has run out of uses. */
    public boolean isBroken() {

        return this.uses >= this.maxUses;
    }
}
