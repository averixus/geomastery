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
    
    private static final int HEALTH_TIMER = 600;
    
    private int food = 20;
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
                
                this.food = Math.max(this.food - 1, 0);
                hasChanged = true;
            }
        }
        
        if (this.food <= 0) {
            
            this.damageTimer++;
            
            if (this.damageTimer >= HEALTH_TIMER) {
                
                this.player.attackEntityFrom(DamageSource.STARVE, 1.0F);
                this.damageTimer = 0;
            }
            
        } else {
            
            this.damageTimer = 0;
        }
        
        return hasChanged;
    }
    
    /** @return The total fullness for comparisons. */
    public float getFullness() {
        
        return this.food + this.saturation - this.exhaustion;
    }
    
    /** Attempts to heal the player using this food type. */
    public void heal() {
        
        if (this.food >= 10) {

            this.healTimer++;
            
            if (this.healTimer >= HEALTH_TIMER) {

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

        this.food = Math.min(hunger + this.food, 20);
        this.saturation = Math.min(this.saturation +
                (saturation * hunger * 2.0F), this.food);
    }
    
    @Override
    public void addStats(ItemFood item, ItemStack stack) {
        
        this.addStats(item.getHealAmount(stack),
                item.getSaturationModifier(stack));
    }
    
    @Override
    public int getFoodLevel() {

        return this.food;
    }
    
    @Override
    public boolean needFood() {

        return this.food < 20;
    }
    
    @Override
    public void addExhaustion(float exhaustion) {

        this.exhaustion = Math.min(this.exhaustion + exhaustion, 40.0F);
    }
    
    @Override
    public float getSaturationLevel() {

        return this.saturation;
    }
    
    @Override
    public void setFoodLevel(int hunger) {

        this.food = hunger;
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
            
            this.food = nbt.getInteger("hunger");
            this.damageTimer = nbt.getInteger("damageTimer");
            this.healTimer = nbt.getInteger("healTimer");
            this.saturation = nbt.getFloat("saturation");
            this.exhaustion = nbt.getFloat("exhaustion");
        }
    }
    
    @Override
    public void writeNBT(NBTTagCompound nbt) {

        nbt.setInteger("hunger", this.food);
        nbt.setInteger("damageTimer", this.damageTimer);
        nbt.setInteger("healTimer", this.healTimer);
        nbt.setFloat("saturation", this.saturation);
        nbt.setFloat("exhaustion", this.exhaustion);
    }
}
