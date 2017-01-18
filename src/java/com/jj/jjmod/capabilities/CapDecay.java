package com.jj.jjmod.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapDecay {
    
    @CapabilityInject(ICapDecay.class)
    public static final Capability<ICapDecay> CAP_DECAY = null;
}
