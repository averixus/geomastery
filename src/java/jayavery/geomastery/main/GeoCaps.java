/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import jayavery.geomastery.capabilities.DefaultCapDecay;
import jayavery.geomastery.capabilities.DefaultCapPlayer;
import jayavery.geomastery.capabilities.ICapDecay;
import jayavery.geomastery.capabilities.ICapPlayer;
import jayavery.geomastery.capabilities.ProviderCapPlayer;
import jayavery.geomastery.capabilities.StorageCapDecay;
import jayavery.geomastery.capabilities.StorageCapPlayer;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.utilities.FoodStatsWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/** Stores and registers capabilities and handles related events. */
public class GeoCaps {
    
    /** Player capability. */
    @CapabilityInject(ICapPlayer.class)
    public static final Capability<ICapPlayer> CAP_PLAYER = null;
    /** Player capability ID. */
    public static final ResourceLocation CAP_PLAYER_ID =
            new ResourceLocation(Geomastery.MODID, "CapabilityPlayer");
    
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
