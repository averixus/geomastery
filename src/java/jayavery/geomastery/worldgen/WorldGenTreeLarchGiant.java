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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeLarchGiant extends WorldGenTreeAbstract {

    public WorldGenTreeLarchGiant(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        EnumFacing facingFront = EnumFacing.HORIZONTALS[this.rand.nextInt(EnumFacing.HORIZONTALS.length)];
        EnumFacing facingSide = facingFront.rotateY();
        
        int layerCount = 15 + this.rand.nextInt(7);
        int trunkCount = layerCount - 1 - this.rand.nextInt(2);
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 1; i < trunkCount; i++) {
            
            trunks.add(stump.up(i));
            trunks.add(stump.up(i).offset(facingFront));
            trunks.add(stump.up(i).offset(facingSide));
            trunks.add(stump.up(i).offset(facingFront).offset(facingSide));
        }
        
        ArrayList<BlockPos> stumps = Lists.newArrayList();
        
        stumps.add(stump);
        stumps.add(stump.offset(facingSide));
        stumps.add(stump.offset(facingFront));
        stumps.add(stump.offset(facingSide).offset(facingFront));
        
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
            
            this.setBlock(aStump, GeoBlocks.BOLE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.LARCH));
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.LARCH));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        BlockPos start = stump.up(trunkCount + 5);
        
        for (int layer = 1; layer <= layerCount; layer++) {
            
            if (layer == 1) {
                
                leaves.add(start.down(layer));
                
            } else if (layer == 2) {
                
                leaves.add(start.down(layer));
                leaves.add(start.offset(facingFront).down(layer));
                
            } else if (layer == 3) {
                
                leaves.add(start.down(layer));
                leaves.add(start.offset(facingFront).down(layer));
                leaves.add(start.offset(facingSide).down(layer));
                
            } else if (layer == 4 || layer == 5) {
                
                leaves.add(start.down(layer));
                leaves.add(start.offset(facingFront).down(layer));
                leaves.add(start.offset(facingSide).down(layer));
                leaves.add(start.offset(facingFront).offset(facingSide).down(layer));
                
            } else if (layer == 6 || layer == 9 || layer == 12 || layer == layerCount) {
                
                this.layerSix(start.down(layer), facingFront, leaves);
                
            } else if (layer == 7 || layer == 10 || layer == 13 || layer == 16) {
                
                this.layerSeven(start.down(layer), facingFront, leaves);
                
            } else if (layer == 8 || layer == 11 || layer == 14 || layer == 17 || layer == layerCount - 1) {
                
                this.quarterEight(start.down(layer), facingSide.getOpposite(), leaves);
                this.quarterEight(start.down(layer).offset(facingFront), facingFront, leaves);
                this.quarterEight(start.down(layer).offset(facingSide).offset(facingFront), facingSide, leaves);
                this.quarterEight(start.down(layer).offset(facingSide), facingFront.getOpposite(), leaves);
                
            } else if (layer == 15 || layer == 18) {
                
                this.layerFifteen(start.down(layer), facingFront, leaves, nodes);
                
            } else if (layer == 19) {
                
                this.quarterNineteen(start.down(layer), facingSide.getOpposite(), leaves, nodes);
                this.quarterNineteen(start.down(layer).offset(facingFront), facingFront, leaves, nodes);
                this.quarterNineteen(start.down(layer).offset(facingSide).offset(facingFront), facingSide, leaves, nodes);
                this.quarterNineteen(start.down(layer).offset(facingSide), facingFront.getOpposite(), leaves, nodes);
            }
        }
        
        
        for (BlockPos node : nodes) {
            
            if (this.world.getBlockState(node).getBlock()
                    .isReplaceable(this.world, node)) {
                
                this.setBlock(node, GeoBlocks.LEAVES_NODE.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.LARCH));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.LARCH));
            }
        }
        
        return true;
    }
    
    private void layerSix(BlockPos start, EnumFacing facing, Collection<BlockPos> leaves) {
                
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(start.offset(facing, 2));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(start.offset(facing.rotateY()).offset(facing, 2));
        }
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing).offset(facing.rotateY(), 2));
        }
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing.rotateY(), 2));
        }
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing.rotateY()).offset(facing.getOpposite()));
        }
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing.getOpposite()));
        }
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing.rotateYCCW()));
        }
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing).offset(facing.rotateYCCW()));
        }
    }
    
    private void layerSeven(BlockPos start, EnumFacing facing, Collection<BlockPos> leaves) {
        
        leaves.add(start.offset(facing, 2));
        leaves.add(start.offset(facing.rotateY()).offset(facing, 2));
        leaves.add(start.offset(facing).offset(facing.rotateY(), 2));
        leaves.add(start.offset(facing.rotateY(), 2));
        leaves.add(start.offset(facing.rotateY()).offset(facing.getOpposite()));
        leaves.add(start.offset(facing.getOpposite()));
        leaves.add(start.offset(facing.rotateYCCW()));
        leaves.add(start.offset(facing).offset(facing.rotateYCCW()));
    }
    
    private void quarterEight(BlockPos start, EnumFacing facing, Collection<BlockPos> leaves) {
        
        leaves.add(start.offset(facing));
        leaves.add(start.offset(facing, 2));
        leaves.add(start.offset(facing).offset(facing.rotateY()));
        leaves.add(start.offset(facing.rotateY()));
        leaves.add(start.offset(facing.rotateY(), 2));
        
        if (this.rand.nextInt(2) == 0) {

            leaves.add(start.offset(facing, 2).offset(facing.rotateY()));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(start.offset(facing).offset(facing.rotateY(), 2));
        }
    }
    
    private void layerFifteen(BlockPos start, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        for (int front = -3; front <= 4; front++) {
            
            for (int side = -3; side <= 4; side++) {
                
                if (front == -3 ? side == -3 || side == 4 : front == 4 ? side == -3 || side == 4 : false) {
                    
                } else if (front == -3 || front == 4 || side == -3 || side == 4) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(start.offset(facing, front).offset(facing.rotateY(), side));
                    }
                    
                } else {
                    
                    leaves.add(start.offset(facing, front).offset(facing.rotateY(), side));
                }
                
                if (front == 0 || side == 0) {
                    
                    nodes.add(start.offset(facing, front).offset(facing.rotateY(), side));
                }
            }
        }
    }
    
    private void quarterNineteen(BlockPos start, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        for (int front = 0; front <= 4; front++) {
            
            for (int side = 0; side <= 4; side++) {
                
                if (Math.abs(front) + Math.abs(side) > 5) {
                    
                } else if (front == 4 || side == 4) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(start.offset(facing, front).offset(facing.rotateY(), side));
                    }
                    
                } else {
                    
                    leaves.add(start.offset(facing, front).offset(facing.rotateY(), side));
                }
                
                if (front == 0) {
                    
                    nodes.add(start.offset(facing, front).offset(facing.rotateY(), side));
                }
            }
        }
    }
}
