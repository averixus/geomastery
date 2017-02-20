package com.jj.jjmod.init;

import com.jj.jjmod.main.Main;
import com.jj.jjmod.packets.ContainerPacketClient;
import com.jj.jjmod.packets.ContainerPacketServer;
import com.jj.jjmod.packets.DecayPacketClient;
import com.jj.jjmod.packets.DecayPacketServerAsk;
import com.jj.jjmod.packets.DryingPacketClient;
import com.jj.jjmod.packets.FloorUpdateClient;
import com.jj.jjmod.packets.FoodPacketClient;
import com.jj.jjmod.packets.FurnacePacketClient;
import com.jj.jjmod.packets.SpeedPacketClient;
import com.jj.jjmod.packets.TempPacketClient;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {

    public static final SimpleNetworkWrapper NETWORK =
            NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    private static int id = 0;

    public static void preInit() {

        NETWORK.registerMessage(ContainerPacketClient.Handler.class,
                ContainerPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(ContainerPacketServer.Handler.class,
                ContainerPacketServer.class, id++, Side.SERVER);
        NETWORK.registerMessage(TempPacketClient.Handler.class,
                TempPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(FoodPacketClient.Handler.class,
                FoodPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(SpeedPacketClient.Handler.class,
                SpeedPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(FloorUpdateClient.Handler.class,
                FloorUpdateClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(DryingPacketClient.Handler.class,
                DryingPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(FurnacePacketClient.Handler.class,
                FurnacePacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(DecayPacketClient.Handler.class,
                DecayPacketClient.class, id++, Side.CLIENT);
        NETWORK.registerMessage(DecayPacketServerAsk.Handler.class,
                DecayPacketServerAsk.class, id++, Side.SERVER);
    }
}
