package com.jj.jjmod.capabilities;

import com.jj.jjmod.main.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapFoodstats {
    
    @CapabilityInject(ICapFoodstats.class)
    public static final Capability<ICapFoodstats> CAP_FOODSTATS = null;
    
    public static final ResourceLocation ID =
            new ResourceLocation(Main.MODID, "CapabilityFoodinfo");

}
