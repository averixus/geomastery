package jayavery.geomastery.utilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IProxy {

    public void registerModels();
    public void preInit();
    public void init();
    public void postInit();
    public EntityPlayer getClientPlayer();
    public World getClientWorld();
    public void addMinecraftRunnable(Runnable task);
}
