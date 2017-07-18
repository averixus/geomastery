/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import java.util.Random;
import jayavery.geomastery.worldgen.WorldGenTreeAbstract;
import net.minecraft.world.World;

/** Functional interface for creating a WorldGenTreeAbstract. */
@FunctionalInterface
public interface ITreeGenFactory {
    
    /** @return A new WorldGenTreeAbstract. */
    public WorldGenTreeAbstract makeTreeGen(World world, Random rand,
            boolean isSapling);
}
