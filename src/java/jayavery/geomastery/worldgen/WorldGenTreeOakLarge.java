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
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TEST
public class WorldGenTreeOakLarge extends WorldGenTreeAbstract {

    public WorldGenTreeOakLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }

    @Override
    public boolean generateTree(BlockPos pos) {

  //      switch (this.rand.nextInt(2)) {
    //        case 0:
      //          return this.generateTreeA(pos);
//            case 1: default:
                return this.generateTreeB(pos);
   //     }
    }

    private boolean generateTreeA(BlockPos stump) {
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_LARGE.getDefaultState());
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState());
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
                        
            this.layerCorners(clump, 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerCorners(clump.up(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(2), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(3), 1, leaves, ECornerAmount.OUTER_CHANCE);
            
            
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
                
                this.setBlock(node, GeoBlocks.LEAF.getDefaultState()
                        .withProperty(BlockLeaves.NODE, true));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeB(BlockPos stump) {
        
        int trunkCount = 5 + this.rand.nextInt(3);
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_LARGE.getDefaultState());
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState());
        }
        
        int leafStart = 3 + this.rand.nextInt(2);
        Set<BlockPos> leaves = Sets.newHashSet();
        
        this.layerCorners(stump.up(leafStart), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 1), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 2), 3, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 3), 3, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerCorners(stump.up(leafStart + 4), 3, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerCorners(stump.up(leafStart + 5), 3, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerCorners(stump.up(leafStart + 6), 3, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 7), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 8), 1, leaves, ECornerAmount.OUTER_CHANCE);

        Set<BlockPos> nodes = Sets.newHashSet();

        for (int i = 0; i <= 3; i++) {
            
            nodes.add(stump.add(i, leafStart + 2, 0));
            nodes.add(stump.add(0, leafStart + 2, i));
            nodes.add(stump.add(-i, leafStart + 2, 0));
            nodes.add(stump.add(0, leafStart + 2, -i));
        }
        
        List<BlockPos> possiblesOuter = Lists.newArrayList();
        List<BlockPos> possiblesInner = Lists.newArrayList();
        
        for (int x = -3; x <= 3; x++) {
            
            for (int z = -3; z <= 3; z++) {
                                
                if (Math.abs(x) + Math.abs(z) == 6) {
                    
                } else if (Math.abs(x) == 3 || Math.abs(z) == 3 ||
                        (Math.abs(x) == 2 && Math.abs(z) == 2)) {
                    
                    possiblesOuter.add(stump.add(x, leafStart, z));
                    
                } else if (Math.abs(x) + Math.abs(z) > 1) {
                    
                    possiblesInner.add(stump.add(x, leafStart, z));
                }
            }
        }
        
        List<BlockPos> clumps = Lists.newArrayList();
        
        for (int clump = 0; clump <= this.rand.nextInt(7) + 3; clump++) {
            
            clumps.add(possiblesOuter.get(this.rand.nextInt(possiblesOuter.size())).down(this.rand.nextInt(2)));
        }
        
        for (int clump = 0; clump <= this.rand.nextInt(7) + 3; clump++) {
            
            clumps.add(possiblesInner.get(this.rand.nextInt(possiblesInner.size())).up(this.rand.nextInt(3) + 2));
        }
        
        for (BlockPos clump : clumps) {
            
            
            this.layerCorners(clump.up(), 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerCorners(clump.up(2), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(3), 2, leaves, ECornerAmount.OUTER_REMOVED);
            this.layerCorners(clump.up(4), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(5), 1, leaves, ECornerAmount.OUTER_CHANCE);
            
            
            int clumpY = clump.getY() + 2;
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
                
                this.setBlock(node, GeoBlocks.LEAF.getDefaultState()
                        .withProperty(BlockLeaves.NODE, true));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false));
            }
        }
        
        return true;
    }
}
