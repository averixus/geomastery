/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageCapDecay implements IStorage<ICapDecay> {

    @Override
    public NBTBase writeNBT(Capability<ICapDecay> capability,
            ICapDecay instance, EnumFacing side) {
        
        return instance.serializeNBT();
    }
    
    @Override
    public void readNBT(Capability<ICapDecay> capability,
            ICapDecay instance, EnumFacing side, NBTBase nbt) {
        
        instance.deserializeNBT((NBTTagCompound) nbt);
    }
}
