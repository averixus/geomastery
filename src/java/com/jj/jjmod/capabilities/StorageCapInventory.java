package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageCapInventory implements IStorage<ICapInventory> {

    @Override
    public NBTBase writeNBT(Capability<ICapInventory> capability,
            ICapInventory instance, EnumFacing side) {

        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ICapInventory> capability,
            ICapInventory instance, EnumFacing side, NBTBase nbt) {

        instance.deserializeNBT((NBTTagCompound) nbt);
    }
}
