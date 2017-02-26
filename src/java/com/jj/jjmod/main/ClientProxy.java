package com.jj.jjmod.main;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModEntities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    /** Gui event handler instance. */
    public static final GuiEventHandler GUI =
            new GuiEventHandler();
    
    @Override
    public void preInit() {

        super.preInit();
        
        ModEntities.preInitClient();
        ModTileEntities.preInitClient();
        ModBlocks.preInitClient();
        ModItems.preInitClient();
      //  ModPackets.preInitClient();
        
        MinecraftForge.EVENT_BUS.register(GUI);
    }

    @Override
    public void init() {

        super.init();
    }

    @Override
    public void postInit() {

        super.postInit();
    }
    
    @Override
    public EntityPlayer getClientPlayer() {
        
        return Minecraft.getMinecraft().player;
    }
    
    @Override
    public World getClientWorld() {
        
        return Minecraft.getMinecraft().world;
    }
    
    @Override
    public void addMinecraftRunnable(Runnable task) {
        
        Minecraft.getMinecraft().addScheduledTask(task);
    }
}
