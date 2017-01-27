package com.jj.jjmod.utilities;

import com.jj.jjmod.capabilities.CapFoodstats;
import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.DefaultCapFoodstats;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModItems.FoodType;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.packets.FoodUpdateClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FoodStatsWrapper extends FoodStats {
    
    protected EntityPlayer player;
    
    public FoodStatsWrapper(EntityPlayer player) {
        
        this.player = player;
    }
    
    @Override
    public void addStats(ItemFood item, ItemStack stack) {
        
        this.player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                .addStats(item, stack);
    }
    
    @Override
    public void onUpdate(EntityPlayer player) {
        
        player.getCapability(CapFoodstats.CAP_FOODSTATS, null).update();
    }
    
    @Override
    public void readNBT(NBTTagCompound nbt) {

        // Data is all stored in the capability
    }
    
    @Override
    public void writeNBT(NBTTagCompound nbt) {
        
        // Data is all stored in the capability
    }
    
    
    // Used to wear down hunger from actions
    @Override
    public void addExhaustion(float exhaustion) {
        
        this.player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                .addExhaustion(exhaustion);
    }
    
    // Only used to check whether player is allowed to sprint
    @Override
    public int getFoodLevel() {
        
        if (!this.player.getCapability(CapInventory.CAP_INVENTORY, null).canRun()) {
            
            return 0;
            
        } else {
        
        return this.player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                .getFoodLevel();
        }
    }
    
    // Only used to check whether player is allowed to eat
    @Override
    public boolean needFood() {
        
        return this.player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                .needFood();
    }
    
    // Only used for eating cake
    @Override
    public void addStats(int hunger, float saturation) {

        this.player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                .addStats(hunger, saturation);
    }
    
    // Only used to check whether updates need to be sent
    @Override
    public float getSaturationLevel() {
        
        return 5.0F;
    }
    
    // Only used to process updates
    @Override
    public void setFoodLevel(int hunger) {}
    
    // Only used to process updates
    @Override
    @SideOnly(Side.CLIENT)
    public void setFoodSaturationLevel(float saturation) {} 
}
