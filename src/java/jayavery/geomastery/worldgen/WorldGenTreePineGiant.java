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
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.particle.ParticleFirework.Starter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreePineGiant extends WorldGenTreeAbstract {

    public WorldGenTreePineGiant(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        EnumFacing facing = EnumFacing.HORIZONTALS[this.rand.nextInt(EnumFacing.HORIZONTALS.length)];
        int trunkCount = 17 + this.rand.nextInt(6);
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 1; i < trunkCount; i++) {
            
            trunks.add(stump.up(i));
            trunks.add(stump.up(i).offset(facing));
            trunks.add(stump.up(i).offset(facing.rotateY()));
            trunks.add(stump.up(i).offset(facing).offset(facing.rotateY()));
        }
        
        ArrayList<BlockPos> stumps = Lists.newArrayList();
        
        stumps.add(stump);
        stumps.add(stump.offset(facing.rotateY()));
        stumps.add(stump.offset(facing));
        stumps.add(stump.offset(facing.rotateY()).offset(facing));
        
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
            
            this.setBlock(aStump, GeoBlocks.STUMP_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.PINE));
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.PINE));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        BlockPos start = stump.up(trunkCount + 4);
        BlockPos startFacing = start.offset(facing);
        BlockPos startSide = start.offset(facing.rotateY());
        BlockPos startCorner = start.offset(facing).offset(facing.rotateY());
        
        int layer = 1;
        
        leaves.add(start.down(layer++));
        
        leaves.add(start.down(layer));
        leaves.add(startFacing.down(layer));
        leaves.add(startSide.down(layer));
        leaves.add(startCorner.down(layer));
        layer++;
        
        this.quarter(start.down(layer), facing.getOpposite(), 1, leaves);
        this.quarter(startFacing.down(layer), facing.rotateYCCW(), 1, leaves);
        this.quarter(startSide.down(layer), facing.rotateY(), 1, leaves);
        this.quarter(startCorner.down(layer), facing, 1, leaves);
        layer++;
        
        this.quarter(start.down(layer), facing.getOpposite(), 2, leaves);
        this.quarter(startFacing.down(layer), facing.rotateYCCW(), 2, leaves);
        this.quarter(startSide.down(layer), facing.rotateY(), 2, leaves);
        this.quarter(startCorner.down(layer), facing, 2, leaves);
        layer++;
        
        this.quarter(start.down(layer), facing.getOpposite(), 3, leaves);
        this.quarter(startFacing.down(layer), facing.rotateYCCW(), 3, leaves);
        this.quarter(startSide.down(layer), facing.rotateY(), 3, leaves);
        this.quarter(startCorner.down(layer), facing, 3, leaves);
        layer++;
        
        this.quarterRing(start.down(layer), facing.getOpposite(), leaves);
        this.quarterRing(startFacing.down(layer), facing.rotateYCCW(), leaves);
        this.quarterRing(startSide.down(layer), facing.rotateY(), leaves);
        this.quarterRing(startCorner.down(layer), facing, leaves);
        layer++;
        
        for (; layer <= 10; layer++) {
        
            this.quarterLargeClumps(start.down(layer), facing.getOpposite(), leaves, nodes);
            this.quarterLargeClumps(startFacing.down(layer), facing.rotateYCCW(), leaves, nodes);
            this.quarterLargeClumps(startSide.down(layer), facing.rotateY(), leaves, nodes);
            this.quarterLargeClumps(startCorner.down(layer), facing, leaves, nodes);
        }
        
        for (; layer <= 12; layer++) {
            
            this.quarterMediumClumps(start.down(layer), facing.getOpposite(), leaves, nodes);
            this.quarterMediumClumps(startFacing.down(layer), facing.rotateYCCW(), leaves, nodes);
            this.quarterMediumClumps(startSide.down(layer), facing.rotateY(), leaves, nodes);
            this.quarterMediumClumps(startCorner.down(layer), facing, leaves, nodes);
        }
        
        for (; layer <= 14; layer++) {
            
            this.quarterSmallClumps(start.down(layer), facing.getOpposite(), leaves);
            this.quarterSmallClumps(startFacing.down(layer), facing.rotateYCCW(), leaves);
            this.quarterSmallClumps(startSide.down(layer), facing.rotateY(), leaves);
            this.quarterSmallClumps(startCorner.down(layer), facing, leaves);
        }
        
        for (BlockPos node : nodes) {
            
            if (this.world.getBlockState(node).getBlock()
                    .isReplaceable(this.world, node)) {
                
                this.setBlock(node, GeoBlocks.LEAF.getDefaultState()
                        .withProperty(BlockLeaves.NODE, true).withProperty(BlockLeaves.TYPE, ETreeType.PINE));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState()
                        .withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.PINE));
            }
        }
        
        return true;
    }
    
    private void quarter(BlockPos centre, EnumFacing facing, int radius, Collection<BlockPos> leaves) {
        
        for (int front = 0; front <= radius; front++) {
            
            for (int side = 0 ; side <= radius; side++) {
                
                if (front + side == radius) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                    }
                        
                } else if (front + side < radius) {
                    
                    leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                }
            }
        }
    }
    
    private void quarterRing(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves) {
        
        for (int front = 0; front <= 3; front++) {
            
            for (int side = 0; side <= 3; side++) {
                
                int sum = front + side;
                
                if (sum > 3) {
                    
                } else if (sum == 3) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                    }
                    
                } else if (sum > 1) {
                    
                    leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                }
            }
        }
    }
    
    private void quarterLargeClumps(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        if (this.rand.nextInt(10) == 0) {
            
            nodes.add(centre.offset(facing));
            leaves.add(centre.offset(facing.rotateY()));
            nodes.add(centre.offset(facing).offset(facing.rotateY()));
            leaves.add(centre.offset(facing, 2).offset(facing.rotateY()));
            nodes.add(centre.offset(facing.rotateY(), 2).offset(facing));
            nodes.add(centre.offset(facing.rotateY(), 2).offset(facing, 2));
            nodes.add(centre.offset(facing.rotateY(), 2).offset(facing, 3));
            leaves.add(centre.offset(facing.rotateY(), 3).offset(facing, 2));
            leaves.add(centre.offset(facing.rotateY(), 3).offset(facing, 3));
        }
        
        if (this.rand.nextInt(10) == 0) {
            
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing));
            nodes.add(centre.offset(facing));
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing, 2));
            nodes.add(centre.offset(facing, 2));
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing, 3));
            nodes.add(centre.offset(facing, 3));
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing, 4));
            nodes.add(centre.offset(facing, 4));
        }
    }
    
    private void quarterMediumClumps(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        if (this.rand.nextInt(10) == 0) {
            
            nodes.add(centre.offset(facing));
            leaves.add(centre.offset(facing.rotateY()));
            nodes.add(centre.offset(facing.rotateY()).offset(facing));
            leaves.add(centre.offset(facing.rotateY()).offset(facing, 2));
            nodes.add(centre.offset(facing.rotateY(), 2).offset(facing));
            leaves.add(centre.offset(facing.rotateY(), 2).offset(facing, 2));
        }
        
        if (this.rand.nextInt(10) == 0) {
            
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing));
            nodes.add(centre.offset(facing));
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing, 2));
            nodes.add(centre.offset(facing, 2));
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing, 3));
            nodes.add(centre.offset(facing, 3));
        }
    }
    
    private void quarterSmallClumps(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves) {
        
        if (this.rand.nextInt(10) == 0) {
            
            leaves.add(centre.offset(facing));
            leaves.add(centre.offset(facing.rotateY()));
            leaves.add(centre.offset(facing).offset(facing.rotateY()));
        }
        
        if (this.rand.nextInt(10) == 0) {
            
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing));
            leaves.add(centre.offset(facing));
            leaves.add(centre.offset(facing.rotateYCCW()).offset(facing, 2));
            leaves.add(centre.offset(facing, 2));
        }
    }
}
