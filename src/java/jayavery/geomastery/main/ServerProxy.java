package jayavery.geomastery.main;

import jayavery.geomastery.utilities.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/** Server-side proxy. */
public class ServerProxy implements IProxy {

    @Override
    public void registerModels() {}
    
    @Override
    public void preInit() {}

    @Override
    public void init() {}

    @Override
    public void postInit() {}
    
    @Override
    public EntityPlayer getClientPlayer() {
        
        throw new RuntimeException("Tried to get Client player on Server side");
    }
    
    @Override
    public World getClientWorld() {
        
        throw new RuntimeException("Tried to get Client world on Server side");
    }
    
    @Override
    public void addMinecraftRunnable(Runnable task) {
        
        throw new RuntimeException("Tried to add Minecraft Runnable on Server side");
    }
}
