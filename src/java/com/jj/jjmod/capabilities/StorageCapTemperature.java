package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageCapTemperature implements IStorage<ICapTemperature> {

    @Override
    public NBTBase writeNBT(Capability<ICapTemperature> capability,
            ICapTemperature instance, EnumFacing side) {

        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ICapTemperature> capability,
            ICapTemperature instance, EnumFacing side, NBTBase nbt) {

        instance.deserializeNBT((NBTTagCompound) nbt);
    }
    
}
