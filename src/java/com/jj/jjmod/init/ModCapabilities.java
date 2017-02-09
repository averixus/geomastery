package com.jj.jjmod.init;

import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.DefaultCapPlayer;
import com.jj.jjmod.capabilities.ICapDecay;
import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.capabilities.StorageCapDecay;
import com.jj.jjmod.capabilities.StorageCapPlayer;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {

    public static void preInit() {

     //   CapabilityManager.INSTANCE.register(ICapInventory.class,
     //           new StorageCapInventory(), DefaultCapInventory.class);
     //   CapabilityManager.INSTANCE.register(ICapTemperature.class,
     //           new StorageCapTemperature(), DefaultCapTemperature.class);
        CapabilityManager.INSTANCE.register(ICapDecay.class,
                new StorageCapDecay(), DefaultCapDecay.class);
     //   CapabilityManager.INSTANCE.register(ICapFoodstats.class,
     //           new StorageCapFoodinfo(), DefaultCapFoodstats.class);
        CapabilityManager.INSTANCE.register(ICapPlayer.class,
                new StorageCapPlayer(), DefaultCapPlayer.class);
    }
}
