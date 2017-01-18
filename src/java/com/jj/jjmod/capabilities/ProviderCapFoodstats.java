package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderCapFoodstats implements ICapabilitySerializable {

    private final DefaultCapFoodstats instance;
    
    public ProviderCapFoodstats(DefaultCapFoodstats instance) {
        
        this.instance = instance;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        
        return capability == CapFoodstats.CAP_FOODSTATS;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        
        if (capability == CapFoodstats.CAP_FOODSTATS) {
            
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
