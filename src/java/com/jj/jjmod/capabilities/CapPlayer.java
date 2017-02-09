package com.jj.jjmod.capabilities;

import com.jj.jjmod.main.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapPlayer {
    
    @CapabilityInject(ICapPlayer.class)
    public static final Capability<ICapPlayer> CAP_PLAYER = null;

    public static final ResourceLocation ID =
            new ResourceLocation(Main.MODID, "CapabilityPlayer");
}