package com.jayavery.jjmod.main;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;

@Mod(modid = Jjmod.MODID, version = Jjmod.VERSION, name = Jjmod.NAME)
public class Jjmod {

    public static final String MODID = "jjmod";
    public static final String VERSION = "1.2.1.a";
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
