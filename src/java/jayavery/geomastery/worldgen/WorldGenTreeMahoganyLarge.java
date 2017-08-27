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
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TEST
public class WorldGenTreeMahoganyLarge extends WorldGenTreeAbstract {

    public WorldGenTreeMahoganyLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }

    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 4 + this.rand.nextInt(3);
        
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
        
        this.setBlock(stump, GeoBlocks.BOLE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        }
        
        int leafStart = 3 + this.rand.nextInt(2);
        Set<BlockPos> leaves = Sets.newHashSet();
        
        this.layerCorners(stump.up(leafStart), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 1), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 2), 2, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerCorners(stump.up(leafStart + 3), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 4), 1, leaves, ECornerAmount.OUTER_CHANCE);
        
        Set<BlockPos> nodes = Sets.newHashSet();

        for (int i = 0; i <= 2; i++) {
            
            nodes.add(stump.add(i, leafStart + 1, 0));
            nodes.add(stump.add(0, leafStart + 1, i));
            nodes.add(stump.add(-i, leafStart + 1, 0));
            nodes.add(stump.add(0, leafStart + 1, -i));
        }
                
        List<BlockPos> possibles = Lists.newArrayList();
        
        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <=2; z++) {
                
                int sum = Math.abs(x) + Math.abs(z);
                
                if (sum < 4 && sum > 1) {
                    
                    possibles.add(stump.add(x, leafStart, z));
                }
            }
        }
        
        List<BlockPos> clumps = Lists.newArrayList();
        
        for (int amount = this.rand.nextInt(6) + 1; amount >= 0 ; amount--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size()))
                    .down(this.rand.nextInt(2)));
        }
        
        for (BlockPos clump : clumps) {
                        
            this.layerCorners(clump, 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerOutsides(clump.up(), 3, leaves, 2);
            this.layerCorners(clump.up(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            
            if (this.rand.nextInt(2) == 0) {
                
                leaves.add(clump.up(3));
            }
            
            
            int clumpY = clump.getY() + 1;
            int clumpX = clump.getX();
            int stumpX = stump.getX();
            int clumpZ = clump.getZ();
            int stumpZ = stump.getZ();
            boolean isZ = Math.abs(clumpZ - stumpZ) < Math.abs(clumpX - stumpX);
            
            if (isZ) {
                
                if (clumpX < stumpX) {
                    
                    for (int i = clumpX - 2; i <= stumpX; i++) {
                        
                        nodes.add(new BlockPos(i, clumpY, clumpZ));
                    }
                    
                } else {
                    
                    for (int i = stumpX; i <= clumpX + 2; i++) {
                        
                        nodes.add(new BlockPos(i, clumpY, clumpZ));
                    }
                }
                
            } else {
                
                if (clumpZ < stumpZ) {
                    
                    for (int i = clumpZ - 2; i <= stumpZ; i++) {
                        
                        nodes.add(new BlockPos(clumpX, clumpY, i));
                    }
                    
                } else {
                    
                    for (int i = stumpZ; i <= clumpZ + 2; i++) {
                        
                        nodes.add(new BlockPos(clumpX, clumpY, i));
                    }
                }
            }
            
        }
        
        for (BlockPos node : nodes) {
            
            if (this.world.getBlockState(node).getBlock()
                    .isReplaceable(this.world, node)) {
                
                this.setBlock(node, GeoBlocks.LEAVES_NODE.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.MAHOGANY));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.MAHOGANY));
            }
        }
        
        return true;
    }
}
