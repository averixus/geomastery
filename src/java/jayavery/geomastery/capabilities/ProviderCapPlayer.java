/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import jayavery.geomastery.main.GeoCaps;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/** ICapPlayer provider. */
public class ProviderCapPlayer
        implements ICapabilitySerializable<NBTTagCompound> {
    
    /** The capability instance. */
    private final ICapPlayer instance;
    
    public ProviderCapPlayer(ICapPlayer instance) {
        
        this.instance = instance;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side) {
        
        return capability == GeoCaps.CAP_PLAYER;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        
        if (capability == GeoCaps.CAP_PLAYER) {
            
            return (T) this.instance;
        }
        
        return null;
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        
        return this.instance.serializeNBT();
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        
        this.instance.deserializeNBT(nbt);
    }
}
