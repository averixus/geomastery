package com.jj.jjmod.init;

import com.jj.jjmod.main.Main;
import com.jj.jjmod.packets.FloorUpdateClient;
import com.jj.jjmod.packets.FoodUpdateClient;
import com.jj.jjmod.packets.InventoryUpdateClient;
import com.jj.jjmod.packets.InventoryUpdateServer;
import com.jj.jjmod.packets.TemperatureUpdateClient;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {

    public static final SimpleNetworkWrapper INSTANCE =
            NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    private static int id = 0;

    public static void preInit() {

        INSTANCE.registerMessage(InventoryUpdateClient.Handler.class,
                InventoryUpdateClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(InventoryUpdateServer.Handler.class,
                InventoryUpdateServer.class, id++, Side.SERVER);
        INSTANCE.registerMessage(TemperatureUpdateClient.Handler.class,
                TemperatureUpdateClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(FoodUpdateClient.Handler.class,
                FoodUpdateClient.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(FloorUpdateClient.Handler.class,
                FloorUpdateClient.class, id++, Side.CLIENT);
    }
}
