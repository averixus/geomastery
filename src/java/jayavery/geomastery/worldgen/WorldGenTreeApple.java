/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Random;
import jayavery.geomastery.blocks.BlockSeedling;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for apple trees. */
public class WorldGenTreeApple extends WorldGenTree {

    public WorldGenTreeApple(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 30, 5, GeoBlocks.SEEDLING_APPLE);
    }

    @Override
    public boolean generateTree(BlockPos pos) {

        ArrayList<BlockPos> trunks = new ArrayList<BlockPos>();

        trunks.add(pos);
        trunks.add(pos.up());
        trunks.add(pos.up(2));
        trunks.add(pos.up(3));
        
        for (BlockPos trunk : trunks) {
            
            Block check = this.world.getBlockState(trunk).getBlock();
            
            if (!check.isReplaceable(this.world, trunk) &&
                    !(check instanceof BlockSeedling)) {

                return false;
            }
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.WOOD_APPLE.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = new ArrayList<BlockPos>();
        
        BlockPos top = pos.up(3);
        
        leaves.add(top.up());
        leaves.add(top.up(2));
        leaves.add(top.north());
        leaves.add(top.north(2));
        leaves.add(top.north().up());
        leaves.add(top.north().down());
        leaves.add(top.north().west());
        leaves.add(top.north().east());
        leaves.add(top.south());
        leaves.add(top.south(2));
        leaves.add(top.south().up());
        leaves.add(top.south().down());
        leaves.add(top.south().west());
        leaves.add(top.south().east());
        leaves.add(top.west());
        leaves.add(top.west(2));
        leaves.add(top.west().up());
        leaves.add(top.west().down());
        leaves.add(top.east());
        leaves.add(top.east(2));
        leaves.add(top.east().up());
        leaves.add(top.east().down());

        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
                
                this.setBlock(leaf, GeoBlocks.LEAF_APPLE.getDefaultState());
            }
        }
        
        return true;
    }
}
