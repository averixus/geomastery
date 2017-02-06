package com.jj.jjmod.capabilities;

import com.jj.jjmod.items.ItemEdible.FoodType;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapFoodstats extends INBTSerializable<NBTTagCompound> {

    public void update();
    
    // Only used to check whether player is allowed to sprint
    public int getFoodLevel();
    
    // Used to wear down hunger from actions
    public void addExhaustion(float exhaustion);
    
    // Used to eat food items
    public void addStats(ItemFood item, ItemStack stack);

    // Only used for eating cake
    public void addStats(int hunger, float saturation);
    
    public boolean canEat(FoodType type);
    
    public void sendMessage();
    public void sendMessage(FoodType type, int hunger);
    public void processMessage(FoodType type, int hunger);
}
