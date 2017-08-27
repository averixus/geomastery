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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeMahoganyGiant extends WorldGenTreeAbstract {

    public WorldGenTreeMahoganyGiant(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 6 + this.rand.nextInt(4);
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 0; i < trunkCount; i++) {
            
            trunks.add(stump.up(i + 1));
            trunks.add(stump.up(i + 1).north());
            trunks.add(stump.up(i + 1).east());
            trunks.add(stump.up(i + 1).north().east());
        }
        
        ArrayList<BlockPos> stumps = Lists.newArrayList();
        
        stumps.add(stump);
        stumps.add(stump.north());
        stumps.add(stump.east());
        stumps.add(stump.east().north());
        
        for (BlockPos aStump : stumps) {
        
            Block stumpFound = this.world.getBlockState(aStump).getBlock();
            
            if (!(stumpFound instanceof BlockSapling) &&
                    !stumpFound.isReplaceable(this.world, aStump)) {
                
                return false;
            }
        }
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!(found instanceof BlockSapling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
                
        for (BlockPos aStump : stumps) {
            
            this.setBlock(aStump, GeoBlocks.BOLE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        List<BlockPos> possibles = Lists.newArrayList();
        
        BlockPos pos = stump.up(3 + this.rand.nextInt(2));
                
        this.quarter(pos, EnumFacing.SOUTH, leaves, nodes, possibles);
        this.quarter(pos.north(), EnumFacing.WEST, leaves, nodes, possibles);
        this.quarter(pos.north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
        this.quarter(pos.east(), EnumFacing.EAST, leaves, nodes, possibles);
        
        List<BlockPos> clumps = Lists.newArrayList();
        
        for (int node = this.rand.nextInt(9) + 8; node >= 0; node--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }
        
        for (BlockPos clump : clumps) {
            
            
            if (this.rand.nextInt(2) == 0) {
                
                leaves.add(clump.down(3));
            }
            
            this.layerCorners(clump.down(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerCorners(clump.down(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerOutsides(clump, 3, leaves, 2);
            this.layerCorners(clump.up(), 2, leaves, ECornerAmount.INNER_CHANCE);
            this.layerCorners(clump.up(2), 1, leaves, ECornerAmount.OUTER_CHANCE);
            
            if (this.rand.nextInt(2) == 0) {
                
                leaves.add(clump.up(3));
            }
            
            
            int clumpY = clump.getY() - 1;
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
    
    private void quarter(BlockPos pos, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int a = 0; a <= 3; a++) {
            
            for (int b = 0; b <= 3; b++) {
                
                BlockPos toAdd = pos.offset(facing, a).offset(facing.rotateY(), b);
                
                if (a + b == 3) {
                    
                    leaves.add(toAdd);
                    possibles.add(toAdd);
                    
                } else if (a + b < 3) {
                    
                    leaves.add(toAdd);
                }
            }
        }
        
        pos = pos.up();
        
        this.layerFiveEight(pos, facing, leaves, nodes, possibles);
        
        pos = pos.up();
        
        this.layerSixSeven(pos, facing, leaves, possibles);
        
        pos = pos.up();
        
        this.layerSixSeven(pos, facing, leaves, possibles);
        
        pos = pos.up();
        
        this.layerFiveEight(pos, facing, leaves, nodes, possibles);
        
        pos = pos.up();
        
        this.layerFour(pos, facing, leaves, possibles);
        
        pos = pos.up();
        
        this.layerThree(pos, facing, leaves, possibles);
        
        pos = pos.up();
        
        for (int a = 0; a <= 2; a++) {
            
            for (int b = 0; b <= 2; b++) {
                
                BlockPos toAdd = pos.offset(facing, a).offset(facing.rotateY(), b);
                
                if (a + b == 3) {
                    
                    leaves.add(toAdd);
                    possibles.add(toAdd);
                    
                } else if (a + b < 3) {
                    
                    leaves.add(toAdd);
                }
            }
        }
        
        pos = pos.up();
        
        leaves.add(pos);
        leaves.add(pos.offset(facing));
        possibles.add(pos.offset(facing));
        leaves.add(pos.offset(facing.rotateY()));
        possibles.add(pos.offset(facing.rotateY()));
    }
    
    private void layerFiveEight(BlockPos pos, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int a = 0; a <= 5; a++) {
            
            nodes.add(pos.offset(facing, a));
        }
        
        possibles.add(pos.offset(facing, 5));
        
        for (int a = 0; a <= 4; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY()));
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 2));
        }
        
        possibles.add(pos.offset(facing, 4).offset(facing.rotateY()));
        possibles.add(pos.offset(facing, 4).offset(facing.rotateY(), 2));
        
        for (int a = 0; a <= 3; a++) {
            
            nodes.add(pos.offset(facing, a).offset(facing.rotateY(), 3));
        }
        
        possibles.add(pos.offset(facing, 3).offset(facing.rotateY(), 3));
        
        for (int a = 0; a <= 2; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 4));
        }
        
        possibles.add(pos.offset(facing).offset(facing.rotateY(), 4));
        possibles.add(pos.offset(facing, 2).offset(facing.rotateY(), 4));
        
        leaves.add(pos.offset(facing.rotateY(), 5));
        possibles.add(pos.offset(facing.rotateY(), 5));
    }
    
    private void layerSixSeven(BlockPos pos, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> possibles) {
        
        for (int a = 0; a <= 5; a++) {
            
            leaves.add(pos.offset(facing, a));
            leaves.add(pos.offset(facing, a).offset(facing.rotateY()));
        }
        
        possibles.add(pos.offset(facing, 5));
        possibles.add(pos.offset(facing, 5).offset(facing.rotateY()));
        
        for (int a = 0; a <= 4; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 2));
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 3));
        }
        
        possibles.add(pos.offset(facing, 4).offset(facing.rotateY(), 2));
        possibles.add(pos.offset(facing, 4).offset(facing.rotateY(), 3));
        
        for (int a = 0; a <= 3; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 4));
        }
        
        possibles.add(pos.offset(facing, 3).offset(facing.rotateY(), 4));
        possibles.add(pos.offset(facing, 2).offset(facing.rotateY(), 4));
        
        leaves.add(pos.offset(facing).offset(facing.rotateY(), 5));
        leaves.add(pos.offset(facing.rotateY(), 5));
        possibles.add(pos.offset(facing).offset(facing.rotateY(), 5));
        possibles.add(pos.offset(facing.rotateY(), 5));
    }
    
    private void layerFour(BlockPos pos, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> possibles) {
        
        for (int a = 0; a <= 4; a++) {
            
            leaves.add(pos.offset(facing, a));
            leaves.add(pos.offset(facing, a).offset(facing.rotateY()));
        }
        
        possibles.add(pos.offset(facing, 4));
        possibles.add(pos.offset(facing, 4).offset(facing.rotateY()));
        
        for (int a = 0; a <= 3; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 2));
        }
        
        possibles.add(pos.offset(facing, 3).offset(facing.rotateY(), 2));
        
        for (int a = 0; a <= 2; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 3));
        }
        
        possibles.add(pos.offset(facing, 2).offset(facing.rotateY(), 3));
        
        leaves.add(pos.offset(facing).offset(facing.rotateY(), 4));
        leaves.add(pos.offset(facing.rotateY(), 4));
        possibles.add(pos.offset(facing).offset(facing.rotateY(), 4));
        possibles.add(pos.offset(facing.rotateY(), 4));
    }
    
    private void layerThree(BlockPos pos, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> possibles) {
        
        for (int a = 0; a <= 3; a++) {
            
            leaves.add(pos.offset(facing, a));
            leaves.add(pos.offset(facing, a).offset(facing.rotateY()));
        }
        
        possibles.add(pos.offset(facing, 3));
        possibles.add(pos.offset(facing, 3).offset(facing.rotateY()));
        
        for (int a = 0; a <= 2; a++) {
            
            leaves.add(pos.offset(facing, a).offset(facing.rotateY(), 2));
        }
        
        possibles.add(pos.offset(facing, 2).offset(facing.rotateY(), 2));
        
        leaves.add(pos.offset(facing).offset(facing.rotateY(), 3));
        leaves.add(pos.offset(facing.rotateY(), 3));
        possibles.add(pos.offset(facing).offset(facing.rotateY(), 3));
        possibles.add(pos.offset(facing.rotateY(), 3));
    }
}
