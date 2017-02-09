package com.jj.jjmod.init;

import com.jj.jjmod.main.Main;
import com.jj.jjmod.packets.ContainerPacketClient;
import com.jj.jjmod.packets.ContainerPacketServer;
import com.jj.jjmod.packets.FloorUpdateClient;
import com.jj.jjmod.packets.FoodPacketClient;
import com.jj.jjmod.packets.SpeedPacketClient;
import com.jj.jjmod.packets.TempPacketClient;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {

    public static final SimpleNetworkWrapper INSTANCE =
            NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    private static int id = 0;

    public static void preInit() {

        INSTANCE.registerMessage(ContainerPacketClient.Handler.class,
                ContainerPacketClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(ContainerPacketServer.Handler.class,
                ContainerPacketServer.class, id++, Side.SERVER);
        INSTANCE.registerMessage(TempPacketClient.Handler.class,
                TempPacketClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(FoodPacketClient.Handler.class,
                FoodPacketClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(SpeedPacketClient.Handler.class,
                SpeedPacketClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(FloorUpdateClient.Handler.class,
                FloorUpdateClient.class, id++, Side.CLIENT);
    }
}
