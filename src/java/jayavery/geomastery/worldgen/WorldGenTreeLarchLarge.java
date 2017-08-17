/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Random;
import com.google.common.collect.Lists;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeLarchLarge extends WorldGenTreeAbstract {

    public WorldGenTreeLarchLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int layerCount = this.rand.nextInt(4) + 11;
        int trunkCount = layerCount + (this.rand.nextInt(3) - 1);
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 1; i < trunkCount; i++) {
            
            trunks.add(stump.up(i));
        }
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.STUMP_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.LARCH));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.LARCH));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        BlockPos start = stump.up(trunkCount + 3);
        int layer = 1;
        
        leaves.add(start.down(layer++));
        leaves.add(start.down(layer++));
        this.layerCorners(start.down(layer++), 1, leaves, ECornerAmount.OUTER_REMOVED);
        
        for (; layer <= layerCount; layer++) {
            
            if (layer == 4 || layer == layerCount - 1) {
                
                this.layerOutsides(start.down(layer), 2, leaves, 1);
                
            } else if (layer == 5 || layer == layerCount) {
                
                this.layerCorners(start.down(layer), 1, leaves, ECornerAmount.OUTER_REMOVED);
                
            } else if (layer == 6 || layer == 8 || layer == 11) {
                
                this.layerCorners(start.down(layer), 2, leaves, ECornerAmount.INNER_CHANCE);
                
            } else if (layer == 7 || layer == 10) {
                
                this.layerPoints(start.down(layer), 2, leaves, 2);
                
            } else if (layer == 9 || layer == 12) {
                
                this.layerOutsides(start.down(layer), 4, leaves, 2);
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.LARCH));
            }
        }
        
        return true;
    }
}
