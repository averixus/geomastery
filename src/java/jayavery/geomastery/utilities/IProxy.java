/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IProxy {

    /** Called on {@code ModelRegistryEvent}. */
    public void registerModels();
    /** Called on {@code FMLPreInitializationEvent}. */
    public void preInit();
    /** Called on {@code FMLInitializationEvent}. */
    public void init();
    /** Called on {@code FMLPostInitializationEvent}. */
    public void postInit();
    /** @return The client player. Throws exception on the server. */
    public EntityPlayer getClientPlayer();
    /** @return The client world. Throws exception on the server. */
    public World getClientWorld();
    /** Adds a client runnable. Throws exception on the server. */
    public void addClientRunnable(Runnable task);
}
