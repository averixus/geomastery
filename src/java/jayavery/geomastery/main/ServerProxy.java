/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
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
        
        Geomastery.LOG.error("Tried to get client player on server side!");
        throw new RuntimeException("Tried to get client player on server side!");
    }
    
    @Override
    public World getClientWorld() {
        
        Geomastery.LOG.error("Tried to get client world on server side!");
        throw new RuntimeException("Tried to get client world on server side!");
    }
    
    @Override
    public void addClientRunnable(Runnable task) {
        
        Geomastery.LOG.error("Tried to add client runnable on server side, runnable will not be executed!", new RuntimeException());
    }
}
