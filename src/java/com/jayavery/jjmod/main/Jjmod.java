package com.jayavery.jjmod.main;

import com.jayavery.jjmod.utilities.TreeFallUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Jjmod.MODID, version = Jjmod.VERSION, name = Jjmod.NAME)
public class Jjmod {

    public static final String MODID = "jjmod";
    public static final String VERSION = "0.2";
    public static final String NAME = "JJ Mod";

    @SidedProxy(clientSide = "com.jayavery.jjmod.main.ClientProxy",
            serverSide = "com.jayavery.jjmod.main.CommonProxy")
    public static CommonProxy proxy;

    @Instance
    public static Jjmod instance = new Jjmod();

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        // Read config, create + register blocks/items/etc
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {

        // Data structures, crafting recipes, register handler
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {

        // Communicate with other mods
        proxy.postInit();
    }
}
