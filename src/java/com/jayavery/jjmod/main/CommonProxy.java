package com.jayavery.jjmod.main;

import com.jayavery.jjmod.init.ModBiomes;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.init.ModEntities;
import com.jayavery.jjmod.init.ModFluids;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.init.ModPackets;
import com.jayavery.jjmod.init.ModRecipes;
import com.jayavery.jjmod.init.ModTileEntities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    /** Block event handler instance. */
    public static final BlockEventHandler BLOCK =
            new BlockEventHandler();
    /** Entity event handler instance. */
    public static final EntityEventHandler ENTITY =
            new EntityEventHandler();
    /** Player event handler instance. */
    public static final PlayerEventHandler PLAYER =
            new PlayerEventHandler();
    /** Capability event handler instance. */
    public static final CapabilityEventHandler CAPABILITY =
            new CapabilityEventHandler();
    /** Worldgenerator instance. */
    public static final IWorldGenerator WORLDGEN = new WorldGenerator();

    public void preInit() {

        ModFluids.preInit();
        ModBlocks.preInit();
        ModItems.preInit();
        ModPackets.preInit();
        ModCaps.preInit();
        ModEntities.preInit();
        
        GameRegistry.registerWorldGenerator(WORLDGEN, 0);
        MinecraftForge.TERRAIN_GEN_BUS.register(WORLDGEN);
        MinecraftForge.ORE_GEN_BUS.register(WORLDGEN);
        MinecraftForge.EVENT_BUS.register(BLOCK);
        MinecraftForge.EVENT_BUS.register(ENTITY);
        MinecraftForge.EVENT_BUS.register(PLAYER);
        MinecraftForge.EVENT_BUS.register(CAPABILITY);
    }

    public void init() {

        ModBiomes.init();
        ModRecipes.init();
        ModTileEntities.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(Jjmod.instance,
                new GuiHandler());
    }

    public void postInit() {

    }
    
    public EntityPlayer getClientPlayer() {
        
        throw new RuntimeException("Tried to get Client player on Server side");
    }
    
    public World getClientWorld() {
        
        throw new RuntimeException("Tried to get Client world on Server side");
    }
    
    public void addMinecraftRunnable(Runnable task) {
        
        throw new RuntimeException("Tried to get Minecraft on Server side");
    }
}
