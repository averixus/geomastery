package com.jj.jjmod.main;

import com.jj.jjmod.init.ModBiomes;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModEntities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModLiquids;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.init.ModTileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static final EventHandler EVENTS = new EventHandler();
    public static final IWorldGenerator WORLDGEN = new WorldGenerator();

    public void preInit(FMLPreInitializationEvent e) {

        ModBlocks.preInit();
        ModLiquids.preInit();
        ModItems.preInit();
        ModPackets.preInit();
        ModCapabilities.preInit();
        ModEntities.preInit();
        
        GameRegistry.registerWorldGenerator(WORLDGEN, 0);
        MinecraftForge.TERRAIN_GEN_BUS.register(WORLDGEN);
        MinecraftForge.EVENT_BUS.register(EVENTS);
    }

    public void init(FMLInitializationEvent e) {

        ModBiomes.init();
        ModRecipes.init();
        ModTileEntities.init();
        ModEntities.preInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance,
                new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
