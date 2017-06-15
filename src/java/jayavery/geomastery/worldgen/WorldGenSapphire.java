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

/** WorldGenerator for sapphire lode. */
public class WorldGenSapphire extends WorldGenStone {

    public WorldGenSapphire(World world, Random rand) {

        super(world, rand, GeoBlocks.LODE_SAPPHIRE.getDefaultState(),
                80, 120, 30, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(2) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
