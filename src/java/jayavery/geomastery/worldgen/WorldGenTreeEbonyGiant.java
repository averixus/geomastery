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

public class WorldGenTreeEbonyGiant extends WorldGenTreeAbstract {

    public WorldGenTreeEbonyGiant(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        ArrayList<BlockPos> stumps = Lists.newArrayList();
        
        stumps.add(stump);
        stumps.add(stump.north());
        stumps.add(stump.east());
        stumps.add(stump.east().north());
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 0; i < 3; i++) {
            
            trunks.add(stump.up(i + 1));
            trunks.add(stump.up(i + 1).north());
            trunks.add(stump.up(i + 1).east());
            trunks.add(stump.up(i + 1).north().east());
        }
        
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
            
            this.setBlock(aStump, GeoBlocks.BOLE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.EBONY));
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.EBONY));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        List<BlockPos> possibles = Lists.newArrayList();
        
        BlockPos start = stump.up();
        int layer = 1;
        
        for (; layer <= 6; layer++) {
            
            if (layer == 1 || layer == 6) {
                
                this.quarterOneSix(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterOneSix(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterOneSix(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterOneSix(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
            
            } else if (layer == 2) {
                
                this.quarterTwo(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterTwo(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterTwo(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterTwo(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
            
            } else if (layer == 3 || layer == 4) {
                
                this.quarterThreeFour(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterThreeFour(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterThreeFour(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterThreeFour(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
            
            } else if (layer == 5) {
                
                this.quarterFive(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterFive(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterFive(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterFive(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
            }
        }
        
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
    
    private void quarterOneSix(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 3; front++) {
            
            for (int side = 0; side <= 3; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 4) {
                    
                } else if (front == 3 || side == 3 || side + front == 4) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (front == 0 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                }
            }
        }
    }
    
    private void quarterFive(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 4; front++) {
            
            for (int side = 0; side <= 4; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 5) {
                    
                } else if (front == 4 || side == 4 || front + side == 5 || side + front < 2) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (front == 0 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else {
                    
                    leaves.add(pos);
                    
                    if (front == 0 || side == 0) {
                        
                        nodes.add(pos);
                    }
                }
            }
        }
    }
    
    private void quarterTwo(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 4; front++) {
            
            for (int side = 0; side <= 4; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 5) {
                    
                } else if (front == 4 || side == 4 || front + side == 5) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (front == 0 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else {
                    
                    leaves.add(pos);
                    
                    if (front == 0 || side == 0) {
                        
                        nodes.add(pos);
                    }
                }
            }
        }
    }
    
    private void quarterThreeFour(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 5; front++) {
            
            for (int side = 0; side <= 5; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 7) {
                    
                } else if (front == 5 || side == 5 || front + side == 7) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (front == 0 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else {
                    
                    leaves.add(pos);
                    
                    if (front == 0 || side == 0) {
                        
                        nodes.add(pos);
                    }
                }
            }
        }
    }
}
