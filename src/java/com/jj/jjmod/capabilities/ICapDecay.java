package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapDecay extends INBTSerializable<NBTTagCompound> {

    public boolean updateAndRot();
    public float getRenderFraction();
}
