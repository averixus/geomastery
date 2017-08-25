/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeEbonyLarge extends WorldGenTreeAbstract{

    public WorldGenTreeEbonyLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        BlockPos[] trunks = {stump.up(), stump.up(2)};
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!(found instanceof BlockSapling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.EBONY));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.EBONY));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        List<BlockPos> possibles = Lists.newArrayList();
        
        this.layerSmall(stump.up(2), leaves, nodes, possibles);
        this.layerLarge(stump.up(3), leaves, nodes, possibles);
        this.layerSmall(stump.up(4), leaves, nodes, possibles);
        
        Set<BlockPos> clumps = Sets.newHashSet();
        
        for (int amount = this.rand.nextInt(4) + 3; amount >= 0; amount--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }
        
        for (BlockPos clump : clumps) {
            
            for (int x = -2; x <= 2; x++) {
                
                for (int y = -2; y <= 2; y++) {
                    
                    for (int z = -2; z <= 2; z++) {
                        
                        if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= 2) {
                            
                            BlockPos pos = clump.add(x, y, z);
                            leaves.add(pos);
                            
                            if (x == 0 || z == 0) {
                                
                                nodes.add(pos);
                            }
                        }
                    }
                }
            }
        }
        
        for (BlockPos node : nodes) {
            
            if (this.world.getBlockState(node).getBlock()
                    .isReplaceable(this.world, node)) {
                
                this.setBlock(node, GeoBlocks.LEAVES_NODE.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.EBONY));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.EBONY));
            }
        }
        
        return true;
    }
    
    private void layerSmall(BlockPos centre, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                int sum = Math.abs(x) + Math.abs(z);
                BlockPos pos = centre.add(x, 0, z);
                
                if (sum == 4) {
                    
                } else if ((sum % 2) != 0) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (x == 0 || z == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else {
                    
                    leaves.add(pos);
                    
                    if (x == 0 || z == 0) {
                        
                        nodes.add(pos);
                    }
                }
            }
        }
    }
    
    private void layerLarge(BlockPos centre, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int x = -3; x <= 3; x++) {
            
            for (int z = -3; z <= 3; z++) {
                
                BlockPos pos = centre.add(x, 0, z);
                
                if ((Math.abs(x) == 3 && (Math.abs(z) == 2 || z == 0)) ||
                        (Math.abs(z) == 3 && (Math.abs(x) == 2 || x == 0))) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (x == 0 || z == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else if (Math.abs(x) + Math.abs(z) < 6) {
                    
                    leaves.add(pos);
                    
                    if (x == 0 || z == 0) {
                        
                        nodes.add(pos);
                    }
                }
            }
        }
    }
}
