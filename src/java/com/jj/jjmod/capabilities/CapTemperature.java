package com.jj.jjmod.capabilities;

import com.jj.jjmod.main.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapTemperature {

    @CapabilityInject(ICapTemperature.class)
    public static final Capability<ICapTemperature> CAP_TEMPERATURE = null;

    public static final ResourceLocation ID =
            new ResourceLocation(Main.MODID, "CapabilityTemperature");
}
