/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraft.world.biome.Biome;

/** Implemented when a block is restricted to certain biomes. */
@FunctionalInterface
public interface IBiomeCheck {
    
    /** @return Whether this block is allowed in the given Biome. */
    public boolean isPermitted(Biome biome);
}
