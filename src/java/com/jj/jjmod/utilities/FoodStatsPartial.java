package com.jj.jjmod.utilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** FoodStats class for single food type. */
public class FoodStatsPartial extends FoodStats {
    
    private int hunger = 20;
    private float saturation = 5.0F;
    private float exhaustion;
    private int damageTimer;
    private int healTimer;
    private EntityPlayer player;
    
    public FoodStatsPartial(EntityPlayer player) {
        
        this.player = player;
    }
    
    /** Ticks all values.
     * @return Whether the displayed food level needs to change. */
    public boolean tickHunger() {
        
        boolean hasChanged = false;
                
        if (this.exhaustion > 4.0F) {
            
            this.exhaustion -= 4.0F;

            if (this.saturation > 0.0F) {
                
                this.saturation = Math.max(this.saturation - 1.0F, 0.0F);
                
            } else {
                
                this.hunger = Math.max(this.hunger - 1, 0);
                hasChanged = true;
            }
        }
        
        if (this.hunger <= 0) {
            
            this.damageTimer++;
            
            if (this.damageTimer >= 240) {
                
                if (this.player.getHealth() > 1.0F) {
                    
                    this.player.attackEntityFrom(DamageSource.STARVE, 1.0F);
                }
                
                this.damageTimer = 0;
            }
            
        } else {
            
            this.damageTimer = 0;
        }
        
        return hasChanged;
    }
    
    /** Attempts to heal the player using this food type. */
    public void heal() {
        
        if (this.saturation > 0.0F && this.hunger >= 20) {
            
            this.healTimer++;
            
            if (this.healTimer >= 30) {
                
                float f = Math.min(this.saturation, 4.0F);
                this.player.heal(f / 4.0F);
                this.addExhaustion(f);
                this.healTimer = 0;
            }
            
        } else if (this.hunger >= 18) {
            
            this.healTimer++;
            
            if (this.healTimer >= 240) {

                this.player.heal(1.0F);
                this.addExhaustion(4.0F);
                this.healTimer = 0;
            }
            
        } else {
            
            this.healTimer = 0;
        }
    }
    
    @Override
    public void addStats(int hunger, float saturation) {

        this.hunger = Math.min(hunger + this.hunger, 20);
        this.saturation = Math.min(this.saturation +
                (saturation * hunger * 2.0F), this.hunger);
    }
    
    @Override
    public void addStats(ItemFood item, ItemStack stack) {
        
        this.addStats(item.getHealAmount(stack),
                item.getSaturationModifier(stack));
    }
    
    @Override
    public int getFoodLevel() {

        return this.hunger;
    }
    
    @Override
    public boolean needFood() {

        return this.hunger < 20;
    }
    
    @Override
    public void addExhaustion(float exhaustion) {

        this.exhaustion = Math.min(this.exhaustion + exhaustion, 40.0F);
    }
    
    @Override
    public float getSaturationLevel() {

        return this.saturation;
    }
    
    public float getExhaustion() {
        
        return this.exhaustion;
    }
    
    public void setExhaustion(float exhaustion) {
        
        this.exhaustion = exhaustion;
    }
    
    @Override
    public void setFoodLevel(int hunger) {

        this.hunger = hunger;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setFoodSaturationLevel(float saturation) {

        this.saturation = saturation;
    }
    
    /** Unused from super */
    @Override
    public void onUpdate(EntityPlayer player) {}
    
    @Override
    public void readNBT(NBTTagCompound nbt) {

        if (nbt.hasKey("hunger", 99)) {
            
            this.hunger = nbt.getInteger("hunger");
            this.damageTimer = nbt.getInteger("damageTimer");
            this.healTimer = nbt.getInteger("healTimer");
            this.saturation = nbt.getFloat("saturation");
            this.exhaustion = nbt.getFloat("exhaustion");
        }
    }
    
    @Override
    public void writeNBT(NBTTagCompound nbt) {

        nbt.setInteger("hunger", this.hunger);
        nbt.setInteger("damageTimer", this.damageTimer);
        nbt.setInteger("healTimer", this.healTimer);
        nbt.setFloat("saturation", this.saturation);
        nbt.setFloat("exhaustion", this.exhaustion);
    }
}
