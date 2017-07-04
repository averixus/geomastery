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

/** WorldGenerator for banana trees. */
public class WorldGenTreeBanana extends WorldGenTree {

    public WorldGenTreeBanana(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 20, 6, GeoBlocks.SEEDLING_BANANA);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {
        
        ArrayList<BlockPos> trunks = new ArrayList<BlockPos>();

        trunks.add(pos);
        trunks.add(pos.up());
        trunks.add(pos.up(2));
        trunks.add(pos.up(3));
        trunks.add(pos.up(4));
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!(found instanceof BlockSeedling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.WOOD_BANANA.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = new ArrayList<BlockPos>();
        
        BlockPos top = pos.up(5);
        
        leaves.add(top);
        leaves.add(top.north());
        leaves.add(top.north(2));
        leaves.add(top.north(3).down());
        leaves.add(top.south());
        leaves.add(top.south(2));
        leaves.add(top.south(3).down());
        leaves.add(top.east());
        leaves.add(top.east(2));
        leaves.add(top.east(3).down());
        leaves.add(top.west());
        leaves.add(top.west(2));
        leaves.add(top.west(3).down());
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF_BANANA.getDefaultState());
            }
        }
        
        return true;
    }
}
