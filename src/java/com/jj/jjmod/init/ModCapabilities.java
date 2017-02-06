package com.jj.jjmod.init;

import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.DefaultCapFoodstats;
import com.jj.jjmod.capabilities.DefaultCapInventory;
import com.jj.jjmod.capabilities.DefaultCapTemperature;
import com.jj.jjmod.capabilities.ICapDecay;
import com.jj.jjmod.capabilities.ICapFoodstats;
import com.jj.jjmod.capabilities.ICapInventory;
import com.jj.jjmod.capabilities.ICapTemperature;
import com.jj.jjmod.capabilities.StorageCapDecay;
import com.jj.jjmod.capabilities.StorageCapFoodinfo;
import com.jj.jjmod.capabilities.StorageCapInventory;
import com.jj.jjmod.capabilities.StorageCapTemperature;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {

    public static void preInit() {

     //   CapabilityManager.INSTANCE.register(ICapInventory.class,
     //           new StorageCapInventory(), DefaultCapInventory.class);
        CapabilityManager.INSTANCE.register(ICapTemperature.class,
                new StorageCapTemperature(), DefaultCapTemperature.class);
        CapabilityManager.INSTANCE.register(ICapDecay.class,
                new StorageCapDecay(), DefaultCapDecay.class);
        CapabilityManager.INSTANCE.register(ICapFoodstats.class,
                new StorageCapFoodinfo(), DefaultCapFoodstats.class);
    }
}
