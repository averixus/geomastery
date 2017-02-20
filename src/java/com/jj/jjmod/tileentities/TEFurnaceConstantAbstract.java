package com.jj.jjmod.tileentities;

import com.jj.jjmod.crafting.CookingManager;
import net.minecraft.util.math.MathHelper;

/** Abstract superclass TileEntity for constant cooking Furnace blocks. */
public abstract class TEFurnaceConstantAbstract extends TEFurnaceAbstract {

    public TEFurnaceConstantAbstract(CookingManager recipes) {
        
        super(recipes, 1);
    }
    
    @Override
    public boolean isHeating() {
        
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
            
        } else if (!this.fuels.get(0).isEmpty()) {
            
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
}
