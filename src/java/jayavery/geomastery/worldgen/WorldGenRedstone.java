/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for redstone Lode. */
public class WorldGenRedstone extends WorldGenStone {

    public WorldGenRedstone(World world, Random rand) {

        super(world, rand, Blocks.REDSTONE_ORE.getDefaultState(), 5, 45, 2, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(39) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.2) {

            return true;
        }

        return false;
    }
}
