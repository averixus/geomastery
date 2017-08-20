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

public class WorldGenTreeBeechGiant extends WorldGenTreeAbstract {

    public WorldGenTreeBeechGiant(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 7 + this.rand.nextInt(3);
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
            
            this.setBlock(aStump, GeoBlocks.STUMP_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BEECH));
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BEECH));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        int leafStart = 3 + this.rand.nextInt(3);
        
        this.quarterCorners(stump.up(leafStart), EnumFacing.SOUTH, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        this.quarterCorners(stump.up(leafStart).north(), EnumFacing.WEST, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        this.quarterCorners(stump.up(leafStart).east().north(), EnumFacing.NORTH, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        this.quarterCorners(stump.up(leafStart).east(), EnumFacing.EAST, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        
        for (int i = 1; i <= 11; i++) {
            
            this.quarterCorners(stump.up(leafStart + i), EnumFacing.SOUTH, 2, ECornerAmount.INNER_CHANCE, leaves, nodes);
            this.quarterCorners(stump.up(leafStart + i).north(), EnumFacing.WEST, 2, ECornerAmount.INNER_CHANCE, leaves, nodes);
            this.quarterCorners(stump.up(leafStart + i).north().east(), EnumFacing.NORTH, 2, ECornerAmount.INNER_CHANCE, leaves, nodes);
            this.quarterCorners(stump.up(leafStart + i).east(), EnumFacing.EAST, 2, ECornerAmount.INNER_CHANCE, leaves, nodes);
        }
        
        this.quarterCorners(stump.up(leafStart + 12), EnumFacing.SOUTH, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        this.quarterCorners(stump.up(leafStart + 12).north(), EnumFacing.WEST, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        this.quarterCorners(stump.up(leafStart + 12).east().north(), EnumFacing.NORTH, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        this.quarterCorners(stump.up(leafStart + 12).east(), EnumFacing.EAST, 1, ECornerAmount.ALL_PRESENT, leaves, nodes);
        
        List<BlockPos> possibles = Lists.newArrayList();
        
        for (int i = leafStart; i <= leafStart + 12; i++) {
            
            possibles.add(stump.add(2, i, 1));
            possibles.add(stump.add(2, i, -1));
            possibles.add(stump.add(-2, i, 1));
            possibles.add(stump.add(-2, i, -1));
            possibles.add(stump.add(1, i, 2));
            possibles.add(stump.add(1, i, -2));
            possibles.add(stump.add(-1, i, 2));
            possibles.add(stump.add(-1, i, -2));
        }
        
        List<BlockPos> clumps = Lists.newArrayList();
        
        for (int node = this.rand.nextInt(6) + 5; node >= 0; node--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }

        for (BlockPos clump : clumps) {

            EnumFacing facing = EnumFacing.HORIZONTALS[this.rand.nextInt(EnumFacing.HORIZONTALS.length)];
            
            this.quarterCorners(clump.down(2), facing.getOpposite(), 1, ECornerAmount.ALL_PRESENT, leaves, null);
            this.quarterCorners(clump.down(2).offset(facing), facing.rotateYCCW(), 1, ECornerAmount.ALL_PRESENT, leaves, null);
            this.quarterCorners(clump.down(2).offset(facing).offset(facing.rotateY()), facing, 1, ECornerAmount.ALL_PRESENT, leaves, null);
            this.quarterCorners(clump.down(2).offset(facing.rotateY()), facing.rotateY(), 1, ECornerAmount.ALL_PRESENT, leaves, null);
            
            for (int i = -1; i <= 2; i++) {
                
                this.quarterCorners(clump.add(0, i, 0), facing.getOpposite(), 2, ECornerAmount.OUTER_REMOVED, leaves, null);
                this.quarterCorners(clump.add(0, i, 0).offset(facing), facing.rotateYCCW(), 2, ECornerAmount.OUTER_REMOVED, leaves, null);
                this.quarterCorners(clump.add(0, i, 0).offset(facing).offset(facing.rotateY()), facing, 2, ECornerAmount.OUTER_REMOVED, leaves, null);
                this.quarterCorners(clump.add(0, i, 0).offset(facing.rotateY()), facing.rotateY(), 2, ECornerAmount.OUTER_REMOVED, leaves, null);
            }
            
            this.quarterCorners(clump.up(3), facing.getOpposite(), 1, ECornerAmount.ALL_PRESENT, leaves, null);
            this.quarterCorners(clump.up(3).offset(facing), facing.rotateYCCW(), 1, ECornerAmount.ALL_PRESENT, leaves, null);
            this.quarterCorners(clump.up(3).offset(facing).offset(facing.rotateY()), facing, 1, ECornerAmount.ALL_PRESENT, leaves, null);
            this.quarterCorners(clump.up(3).offset(facing.rotateY()), facing.rotateY(), 1, ECornerAmount.ALL_PRESENT, leaves, null);
        
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
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState()
                        .withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.BEECH));
            }
        }
        
        
        return true;
    }
    
    private void quarterCorners(BlockPos centre, EnumFacing facing, int radius, ECornerAmount corners, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        for (int front = 0; front <= radius; front++) {
            
            for (int side = 0; side <= radius; side++) {
                
                int sum = front + side;
                boolean place = false;
                
                if (sum == 2 * radius) {
                    
                    if (corners.placeOuter(this.rand)) {
                        
                        leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                        place = true;
                    }
                    
                } else if (sum == (2 * radius) - 1) {
                    
                    if (corners.placeInner(this.rand)) {
                        
                        leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                        place = true;
                    }
                    
                } else {
                    
                    leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                    place = true;
                }
                
                if (place && front == 0 && nodes != null) {
                    
                    nodes.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                }
            }
        }
    }
}
