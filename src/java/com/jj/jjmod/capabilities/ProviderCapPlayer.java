package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderCapPlayer implements ICapabilitySerializable {
    
    private final ICapPlayer instance;
    
    public ProviderCapPlayer(ICapPlayer instance) {
        
        this.instance = instance;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side) {
        
        return capability == ModCapabilities.CAP_PLAYER;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        
        if (capability == ModCapabilities.CAP_PLAYER) {
            
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
