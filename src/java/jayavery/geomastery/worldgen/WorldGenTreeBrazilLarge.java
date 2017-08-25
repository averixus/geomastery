/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import jayavery.geomastery.worldgen.WorldGenTreeAbstract.ECornerAmount;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeBrazilLarge extends WorldGenTreeAbstract {

    public WorldGenTreeBrazilLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 12 + this.rand.nextInt(5);
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 0; i < trunkCount; i++) {
            
            trunks.add(stump.up(i + 1));
        }
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!(found instanceof BlockSapling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        List<BlockPos> possibles = Lists.newArrayList();
        
        this.layerSmall(stump.up(trunkCount), leaves, possibles);
        this.layerLarge(stump.up(trunkCount + 1), leaves, possibles);
        this.layerSmall(stump.up(trunkCount + 2), leaves, possibles);
        
        Set<BlockPos> clumps = Sets.newHashSet();
        
        for (int amount = this.rand.nextInt(4) + 2; amount >= 0; amount--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }
        
        for (BlockPos clump : clumps) {
            
            this.layerPoints(clump.down(), 1, leaves, 2);
            this.layerCorners(clump, 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerPoints(clump.up(), 1, leaves, 2);
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
            }
        }
        
        return true;
    }
    
    private void layerSmall(BlockPos centre, Collection<BlockPos> leaves, Collection<BlockPos> possibles) {
        
        for (int x = -1; x <= 1; x++) {
            
            for (int z = -1; z <= 1; z++) {
                
                BlockPos pos = centre.add(x, 0, z);
                
                if (Math.abs(x) == 1 || Math.abs(z) == 1) {
                    
                    possibles.add(pos);
                }
                
                if (Math.abs(x) + Math.abs(z) == 2) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                    }
                    
                } else {
                    
                    leaves.add(pos);
                }
            }
        }
    }
    
    private void layerLarge(BlockPos centre, Collection<BlockPos> leaves, Collection<BlockPos> possibles) {
        
        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                BlockPos pos = centre.add(x, 0, z);
                
                if (Math.abs(x) + Math.abs(z) < 4) {
                    
                    if (Math.abs(x) + Math.abs(z) == 3) {
                        
                        if (this.rand.nextInt(2) == 0) {
                            
                            leaves.add(pos);
                        }
                        
                    } else {
                        
                        leaves.add(pos);
                    }
                    
                    if (Math.abs(x) == 2 || Math.abs(z) == 2) {
                        
                        possibles.add(pos);
                    }
                }
            }
        }
    }
}
