package com.jj.jjmod.init;

import com.jj.jjmod.main.Main;
import com.jj.jjmod.packets.ContainerPacketClient;
import com.jj.jjmod.packets.ContainerPacketServer;
import com.jj.jjmod.packets.DryingPacketClient;
import com.jj.jjmod.packets.FloorUpdateClient;
import com.jj.jjmod.packets.FoodPacketClient;
import com.jj.jjmod.packets.FurnacePacketClient;
import com.jj.jjmod.packets.TempPacketClient;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {

    /** Network channel for sending packets. */
    public static final SimpleNetworkWrapper NETWORK =
            NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    
    /** ID counter. */
    private static int id = 0;

    public static void preInit() {

        NETWORK.registerMessage(ContainerPacketServer.Handler.class,
                ContainerPacketServer.class, id++, Side.SERVER);
        NETWORK.registerMessage(ContainerPacketClient.Handler.class,
                ContainerPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(TempPacketClient.Handler.class,
                TempPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(FoodPacketClient.Handler.class,
                FoodPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(FloorUpdateClient.Handler.class,
                FloorUpdateClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(DryingPacketClient.Handler.class,
                DryingPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(FurnacePacketClient.Handler.class,
                FurnacePacketClient.class, id++, Side.CLIENT);
    }
}
