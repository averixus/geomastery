package com.jayavery.jjmod.init;

import com.jayavery.jjmod.capabilities.DefaultCapDecay;
import com.jayavery.jjmod.capabilities.DefaultCapPlayer;
import com.jayavery.jjmod.capabilities.ICapDecay;
import com.jayavery.jjmod.capabilities.ICapPlayer;
import com.jayavery.jjmod.capabilities.StorageCapDecay;
import com.jayavery.jjmod.capabilities.StorageCapPlayer;
import com.jayavery.jjmod.main.Main;
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
