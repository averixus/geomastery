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

public class FoodStatsPartial extends FoodStats {
    
    protected int hunger = 20;
    protected float saturation = 5.0F;
    protected float exhaustion;
    protected int timer;
    protected int prevHunger = 20;
    
    @Override
    public void addStats(int hunger, float saturation) {

        this.hunger = Math.min(hunger + this.hunger, 20);
        this.saturation = Math.min(this.saturation + (saturation * hunger * 2.0F), this.hunger);
    }
    
    @Override
    public void addStats(ItemFood item, ItemStack stack) {
        
        this.addStats(item.getHealAmount(stack), item.getSaturationModifier(stack));
    }
    
    @Override
    public void onUpdate(EntityPlayer player) {}
    
    public boolean update(EntityPlayer player) {
        
        if (player.worldObj.isRemote) {
            
            return false;
        }
        
        boolean hasChanged = false;
        EnumDifficulty difficulty = player.worldObj.getDifficulty();
        this.prevHunger = this.hunger;
        
        if (this.exhaustion > 4.0F) {
            
            this.exhaustion -= 4.0F;

            if (this.saturation > 0.0F) {
                
                this.saturation = Math.max(this.saturation - 1.0F, 0.0F);
                
            } else if (difficulty != EnumDifficulty.PEACEFUL) {
                
                this.hunger = Math.max(this.hunger - 1, 0);
                hasChanged = true;
            }
        }
        
        boolean regen = player.worldObj.getGameRules().getBoolean("naturalRegeneration");
        
        if (regen && this.saturation > 0.0F && player.shouldHeal() && this.hunger >= 20) {
            
            this.timer++;
            
            if (this.timer >= 30) {
                
                float f = Math.min(this.saturation, 4.0F);
                player.heal(f / 4.0F);
                this.addExhaustion(f);
                this.timer = 0;
            }
            
        } else if (regen && this.hunger >= 18 && player.shouldHeal()) {
            
            this.timer++;
            
            if (this.timer >= 240) {

                player.heal(1.0F);
                this.addExhaustion(4.0F);
                this.timer = 0;
            }
            
        } else if (this.hunger <= 0) {
            
            this.timer++;
            
            if (this.timer >= 240) {
                
                if (player.getHealth() > 10.0F ||
                        difficulty == EnumDifficulty.HARD ||
                        (player.getHealth() > 1.0F &&
                        difficulty == EnumDifficulty.NORMAL)) {
                    
                    player.attackEntityFrom(DamageSource.starve, 1.0F);
                }
                
                this.timer = 0;
            }
            
        } else {
            
            this.timer = 0;
        }
        
        return hasChanged;
    }
    
    @Override
    public void readNBT(NBTTagCompound nbt) {

        if (nbt.hasKey("hunger", 99)) {
            
            this.hunger = nbt.getInteger("hunger");
            this.timer = nbt.getInteger("timer");
            this.saturation = nbt.getFloat("saturation");
            this.exhaustion = nbt.getFloat("exhaustion");
        }
    }
    
    @Override
    public void writeNBT(NBTTagCompound nbt) {

        nbt.setInteger("hunger", this.hunger);
        nbt.setInteger("timer", this.timer);
        nbt.setFloat("saturation", this.saturation);
        nbt.setFloat("exhaustion", this.exhaustion);
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
}
