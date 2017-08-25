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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeSpruceMedium extends WorldGenTreeAbstract {

    public WorldGenTreeSpruceMedium(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int layerCount = this.rand.nextInt(5) + 6;
        int trunkCount = layerCount - 2 - this.rand.nextInt(2);
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
        
        this.setBlock(stump, GeoBlocks.BOLE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        BlockPos start = stump.up(trunkCount + 4);
        int layer = 1;
        
        leaves.add(start.down(layer++));
        leaves.add(start.down(layer++));
        
        for (; layer <= layerCount; layer++) {
         
            if (layer == layerCount - 2) {
                
                this.layerOutsides(start.down(layer), 2, leaves, 1);
                
            } else if (layer == layerCount - 1) {
                
                this.innerRing(start.down(layer), leaves);
                
            } else if (layer == layerCount) {
                
                this.outerRing(start.down(layer), leaves);
                
            } else if (layer == 3 || layer == 4) {
                
                this.layerPoints(start.down(layer), 1, leaves, 2);
                
            } else if (layer == 6) {
                
                this.layerOutsides(start.down(layer), 2, leaves, 2);
 
            } else if (layer == 5 || layer == 7) {
                
                this.layerPoints(start.down(layer), 1, leaves, 1);
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.SPRUCE));
            }
        }
        
        
        return true;
    }
    
    private void innerRing(BlockPos centre, Collection<BlockPos> leaves) {
        
        for (int x = -3; x <= 3; x++) {
            
            for (int z = -3; z <= 3; z++) {
                
                if (Math.abs(x) <= 1 && Math.abs(z) <= 1) {
                    
                } else if ((Math.abs(x) <= 1 && Math.abs(z) == 2) || (Math.abs(z) <= 1 && Math.abs(x) == 2)) {
                    
                    leaves.add(centre.add(x, 0, z));
                    
                } else if ((Math.abs(x) + Math.abs(z) <= 4)) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                }
            }
        }
    }
    
    private void outerRing(BlockPos centre, Collection<BlockPos> leaves) {
        
        for (int x = -3; x <= 3; x++) {
            
            for (int z = -3; z <= 3; z++) {
                
                if ((Math.abs(x) == 3 && z == 0) || (Math.abs(z) == 3 && x == 0) || (Math.abs(x) + Math.abs(z) == 4)) {
                    
                    if (this.rand.nextInt(2) == 0 && leaves.contains(centre.add(x, 1, z))) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                }
            }
        }
    }
}
