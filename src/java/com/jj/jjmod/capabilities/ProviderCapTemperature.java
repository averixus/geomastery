package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderCapTemperature implements ICapabilitySerializable {

    private final DefaultCapTemperature instance;
    
    public ProviderCapTemperature(DefaultCapTemperature instance) {
        
        this.instance = instance;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        return capability == CapTemperature.CAP_TEMPERATURE;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        
        if (capability == CapTemperature.CAP_TEMPERATURE) {
            
            return (T) this.instance;
        }
        
        return null;
    }
    
    @Override
    public NBTBase serializeNBT() {

        return this.instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {

        this.instance.deserializeNBT((NBTTagCompound) nbt);
    }
}
