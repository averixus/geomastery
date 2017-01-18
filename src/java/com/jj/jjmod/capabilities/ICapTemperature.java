package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapTemperature extends INBTSerializable<NBTTagCompound> {

    public void update();
    public ResourceLocation getIcon();
    public void processMessage(int icon);
    public void sendMessage();
    
}
