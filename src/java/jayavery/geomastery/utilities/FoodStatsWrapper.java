/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import jayavery.geomastery.main.GeoCaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Wrapper for the player FoodStats object, to link to ICapPlayer. */
public class FoodStatsWrapper extends FoodStats {
    
    /** The player owning this FoodStats. */
    private EntityPlayer player;
    
    public FoodStatsWrapper(EntityPlayer player) {
        
        this.player = player;
    }
    
    /** Used to wear down hunger from actions */
    @Override
    public void addExhaustion(float exhaustion) {
        
        this.player.getCapability(GeoCaps.CAP_PLAYER, null)
                .addExhaustion(exhaustion);
    }
    
    /** Only used to check whether player is allowed to sprint */
    @Override
    public int getFoodLevel() {
        
        if (!this.player.getCapability(GeoCaps.CAP_PLAYER, null)
                .canSprint()) {
            
            return 0;
            
        } else {
        
        return this.player.getCapability(GeoCaps.CAP_PLAYER, null)
                .getFoodLevel();
        }
    }
    
    /** Only used to check whether player is allowed to eat vanilla food */
    @Override
    public boolean needFood() {
        
        return false;
    }
    
    /** Only used to check whether vanilla packets need to be sent */
    @Override
    public float getSaturationLevel() {
        
        return 5.0F;
    }
    
    /** Used for most vanilla eating. */
    @Override
    public void addStats(ItemFood item, ItemStack stack) {}
    
    /** Only used for eating vanilla cake. */
    @Override
    public void addStats(int hunger, float saturation) {}
    
    /** Used for vanilla update. */
    @Override
    public void onUpdate(EntityPlayer player) {}
    
    /** Used to store vanilla data. */
    @Override
    public void readNBT(NBTTagCompound nbt) {}
    
    /** Used to store vanilla data. */
    @Override
    public void writeNBT(NBTTagCompound nbt) {}
    
    /** Only used to process vanilla packets. */
    @Override
    public void setFoodLevel(int hunger) {}
    
    /** Only used to process vanilla packets. */
    @Override
    @SideOnly(Side.CLIENT)
    public void setFoodSaturationLevel(float saturation) {} 
}
