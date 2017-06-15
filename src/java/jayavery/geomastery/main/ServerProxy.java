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
        
        throw new RuntimeException("Tried to get Client player on Server!");
    }
    
    @Override
    public World getClientWorld() {
        
        throw new RuntimeException("Tried to get Client world on Server!");
    }
    
    @Override
    public void addClientRunnable(Runnable task) {
        
        throw new RuntimeException("Tried to add Client Runnable on Server!");
    }
}
