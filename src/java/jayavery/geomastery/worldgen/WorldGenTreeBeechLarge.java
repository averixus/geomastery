/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import jayavery.geomastery.worldgen.WorldGenTreeAbstract.ECornerAmount;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeBeechLarge extends WorldGenTreeAbstract {

    public WorldGenTreeBeechLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {
        
        return this.generateTreeB(pos);
    }
    
    private boolean generateTreeA(BlockPos stump) {
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BEECH));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BEECH));
        }
        
        int leafStart = 3 + this.rand.nextInt(2);
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        
        this.layerCorners(stump.up(leafStart), 1, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerCorners(stump.up(leafStart + 1), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 2), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 3), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 4), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 5), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(leafStart + 6), 1, leaves, ECornerAmount.OUTER_REMOVED);

        for (int i = leafStart; i <= leafStart + 6; i++) {
            
            nodes.add(stump.up(i));
        }
        
        List<BlockPos> possibles = Lists.newArrayList();
        
        for (int i = leafStart + 1; i < leafStart + 6; i++) {
            
            possibles.add(stump.add(1, i, 1));
            possibles.add(stump.add(1, i, -1));
            possibles.add(stump.add(-1, i, 1));
            possibles.add(stump.add(-1, i, -1));
        }
        
        List<BlockPos> clumps = Lists.newArrayList();
        
        for (int amount = this.rand.nextInt(5) + 2; amount >= 0; amount--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }
        
        
        for (BlockPos clump : clumps) {
            
            
            this.layerCorners(clump.down(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerCorners(clump.down(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump, 2, leaves, ECornerAmount.OUTER_REMOVED);
            this.layerCorners(clump.up(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            
            
            int clumpY = clump.getY();
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
                        .withProperty(BlockLeaves.NODE, true).withProperty(BlockLeaves.TYPE, ETreeType.BEECH));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.BEECH));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeB(BlockPos stump) {
        
        int trunkCount = 6 + this.rand.nextInt(3);
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BEECH));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BEECH));
        }
        
        int leafStart = 3 + this.rand.nextInt(3);
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        
        this.layerCorners(stump.up(leafStart), 1, leaves, ECornerAmount.ALL_PRESENT);
        this.layerCorners(stump.up(leafStart + 1), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 2), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 3), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 4), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 5), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 6), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 7), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 8), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 9), 2, leaves, ECornerAmount.INNER_CHANCE);
        this.layerCorners(stump.up(leafStart + 10), 1, leaves, ECornerAmount.ALL_PRESENT);

        for (int i = leafStart; i <= leafStart + 10; i++) {
            
            nodes.add(stump.up(i));
        }
        
        List<BlockPos> possibles = Lists.newArrayList();
        
        for (int i = leafStart + 2; i <= leafStart + 8; i++) {
            
            possibles.add(stump.add(2, i, 1));
            possibles.add(stump.add(2, i, -1));
            possibles.add(stump.add(-2, i, 1));
            possibles.add(stump.add(-2, i, -1));
            possibles.add(stump.add(1, i, -2));
            possibles.add(stump.add(1, i, 2));
            possibles.add(stump.add(-1, i, -2));
            possibles.add(stump.add(-1, i, 2));
        }
        
        List<BlockPos> clumps = Lists.newArrayList();
        
        for (int amount = this.rand.nextInt(4) + 3; amount >= 0; amount--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }
        
        for (BlockPos clump : clumps) {
            
            
            this.layerCorners(clump.down(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerCorners(clump.down(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump, 2, leaves, ECornerAmount.OUTER_REMOVED);
            this.layerCorners(clump.up(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            
            
            int clumpY = clump.getY();
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
                        .withProperty(BlockLeaves.NODE, true).withProperty(BlockLeaves.TYPE, ETreeType.BEECH));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.BEECH));
            }
        }
        
        
        
        return true;
    }
}
