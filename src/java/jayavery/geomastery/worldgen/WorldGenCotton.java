/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for cotton crops. */
public class WorldGenCotton extends WorldGenCrop {

    public WorldGenCotton(World world, Random rand) {
        
        super(world, rand, GeoBlocks.COTTON.getFullgrown(), 30, 5);
    }
}
