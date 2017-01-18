package com.jj.jjmod.tileentities;

import net.minecraft.tileentity.TileEntity;

public class TEBed extends TileEntity {

    protected int maxUses;
    protected int uses = 0;

    public TEBed(int maxUses) {

        this.maxUses = maxUses;
    }

    public int getDropDamage() {

        return this.uses;
    }

    public void addUse() {

        this.uses++;
    }

    public void setDropDamage(int damage) {

        this.uses = damage;
    }

    public boolean isBroken() {

        return this.uses >= this.maxUses;
    }
}
