package com.jj.jjmod.tileentities;

import com.jj.jjmod.crafting.CookingManager;
import net.minecraft.util.math.MathHelper;

public abstract class TEFurnaceConstantAbstract extends TEFurnaceAbstract {

    public TEFurnaceConstantAbstract(CookingManager recipes) {
        
        super(recipes);
    }
    
    @Override
    public boolean isHeating() {
        
        return this.fuelLeft > 0;
    }
    
    @Override
    public void update() {
        
        boolean isDirty = false;
        
        // Reduce cook time
        if (this.fuelLeft > 0) {

            this.fuelLeft--;
        }

        // Do nothing if wrong side
        if (this.worldObj.isRemote) {

            return;
        }
        
        // If ready to cook
        if (this.stacks.get(1) != null) {

            // If already cooking
            if (this.isBurning()) {

                if (this.canSmelt() && this.stacks.get(0) != null) {
                    
                    this.cookSpent++;
    
                    // If finished cooking
                    if (this.cookSpent == this.cookEach) {
    
                        this.cookSpent = 0;
                        this.cookEach = getCookTime(this.stacks.get(0));
                        this.smeltItem();
                        isDirty = true;
                    }
                }
            
            // If not already cooking
            } else {

                // Start new cook
                this.fuelEach = getFuelTime(this.stacks.get(1));
                this.fuelLeft = this.fuelEach;
                this.stacks.get(1).func_190918_g(1);

                // If used last item
                if (this.stacks.get(1) != null && this.stacks.get(1).func_190916_E() == 0) {

                    this.stacks.set(1, this.stacks.get(1).getItem()
                            .getContainerItem(this.stacks.get(1)));
                }

                isDirty = true;
            }
        }
        
        // If cooking from last fuel?
        if (!this.isBurning() && this.cookSpent > 0) {

            this.cookSpent =
                    MathHelper.clamp_int(this.cookSpent - 2, 0, this.cookEach);
        }
        
        // If dirty
        if (isDirty) {

            this.markDirty();
        }        
    }
}
