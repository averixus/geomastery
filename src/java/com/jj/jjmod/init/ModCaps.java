package com.jj.jjmod.init;

import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.DefaultCapPlayer;
import com.jj.jjmod.capabilities.ICapDecay;
import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.capabilities.StorageCapDecay;
import com.jj.jjmod.capabilities.StorageCapPlayer;
import com.jj.jjmod.main.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCaps {
    
    /** Player capability. */
    @CapabilityInject(ICapPlayer.class)
    public static final Capability<ICapPlayer> CAP_PLAYER = null;
    /** Player capability ID. */
    public static final ResourceLocation CAP_PLAYER_ID =
            new ResourceLocation(Main.MODID, "CapabilityPlayer");
    
    /** Decay capability. */
    @CapabilityInject(ICapDecay.class)
    public static final Capability<ICapDecay> CAP_DECAY = null;

    public static void preInit() {

        CapabilityManager.INSTANCE.register(ICapDecay.class,
                new StorageCapDecay(), DefaultCapDecay.class);
        CapabilityManager.INSTANCE.register(ICapPlayer.class,
                new StorageCapPlayer(), DefaultCapPlayer.class);
    }
}
