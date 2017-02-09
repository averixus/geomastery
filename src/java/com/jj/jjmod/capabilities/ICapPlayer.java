package com.jj.jjmod.capabilities;

import com.jj.jjmod.utilities.TempStage;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapPlayer extends INBTSerializable<NBTTagCompound> {

    public void tick();
    
    public int getInventoryRows();
    public int getInventorySize();
    public boolean canSprint();
    public ItemStack getBackpack();
    public ItemStack getYoke();
    public ItemStack removeBackpack();
    public ItemStack removeYoke();
    public void putBackpack(ItemStack stack);
    public void putYoke(ItemStack stack);
    
    public ResourceLocation getTempIcon();
    
    public int foodLevel(FoodType type);
    public boolean canEat(FoodType type);
    public int getFoodLevel();
    public void addExhaustion(float exhaustion);
    public void addStats(ItemFood item, ItemStack stack);

    public void sendTempPacket(TempStage stage);
    public void sendFoodPacket(FoodType foodType, int hunger);
    public void sendSpeedPacket(float speed);
    public void syncAll();
    public void processTempMessage(TempStage stage);
    public void processFoodMessage(FoodType foodType, int hunger);

}
