package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderCapInventory implements ICapabilitySerializable {

    private final DefaultCapInventory instance;

    public ProviderCapInventory(DefaultCapInventory instance) {

        this.instance = instance;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        return capability == CapInventory.CAP_INVENTORY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        if (capability == CapInventory.CAP_INVENTORY) {

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
