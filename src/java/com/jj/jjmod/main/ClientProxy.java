package com.jj.jjmod.main;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModEntities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModTileEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();
        
        ModEntities.preInitClient();
        ModTileEntities.preInitClient();
        ModBlocks.preInitClient();
        ModItems.preInitClient();
    }

    @Override
    public void init() {

        super.init();
    }

    @Override
    public void postInit() {

        super.postInit();
    }
}
