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

/** WorldGenerator for Lapis Lode. */
public class WorldGenLapis extends WorldGenStone {

    public WorldGenLapis(World world, Random rand) {

        super(world, rand, Blocks.LAPIS_ORE.getDefaultState(), 60, 70, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(19) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.3) {

            return true;
        }

        return false;
    }
}
