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

/** WorldGenerator for Emerald Lode. */
public class WorldGenEmerald extends WorldGenStone {

    public WorldGenEmerald(World world, Random rand) {

        super(world, rand, Blocks.EMERALD_ORE.getDefaultState(), 0, 30, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(39) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.1) {

            return true;
        }

        return false;
    }
}
