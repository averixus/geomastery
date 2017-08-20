/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import com.google.common.collect.Lists;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreePineMedium extends WorldGenTreeAbstract {

    public WorldGenTreePineMedium(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = this.rand.nextInt(6) + 6;
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
        
        this.setBlock(stump, GeoBlocks.STUMP_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.PINE));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.PINE));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        BlockPos start = stump.up(trunkCount + 2);
        int layer = 1;
        
        leaves.add(start.down(layer++));
        this.layerCorners(start.down(layer++), 1, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerCorners(start.down(layer++), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerPoints(start.down(layer++), 2, leaves, 2);
        this.layerFive(start.down(layer++), leaves);
        
        for (; layer < trunkCount; layer++) {
            
            if (layer < 9) {
                
                this.cross(start.down(layer), 2, leaves);
                
            } else {
                
                this.cross(start.down(layer), 1, leaves);
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.PINE));
            }
        }
        
        
        return true;
    }
    
    private void layerFive(BlockPos centre, Collection<BlockPos> leaves) {
        
        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                if (Math.abs(x) + Math.abs(z) == 3) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                    
                } else if (Math.abs(x) + Math.abs(z) < 3) {
                    
                    leaves.add(centre.add(x, 0, z));
                }
            }
        }
    }
    
    private void cross(BlockPos centre, int radius, Collection<BlockPos> leaves) {
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (this.rand.nextInt(4) == 0) {
                
                for (int i = 0; i <= radius; i++) {
                    
                    leaves.add(centre.offset(facing, i));
                }
            }
        }
    }
}
