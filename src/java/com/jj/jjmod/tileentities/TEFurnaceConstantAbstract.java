package com.jj.jjmod.tileentities;

import com.jj.jjmod.crafting.CookingManager;
import net.minecraft.util.math.MathHelper;

/** Abstract superclass TileEntity for constant cooking Furnace blocks. */
public abstract class TEFurnaceConstantAbstract extends TEFurnaceAbstract {

    public TEFurnaceConstantAbstract(CookingManager recipes) {
        
        super(recipes);
    }
    
    @Override
    public boolean isBurning() {
        
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
            
        } else if (!this.fuel.isEmpty()) {
            
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
}
