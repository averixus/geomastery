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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeBrazilGiant extends WorldGenTreeAbstract {

    public WorldGenTreeBrazilGiant(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 23 + this.rand.nextInt(3);
        
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
            
            this.setBlock(aStump, GeoBlocks.BOLE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_GIANT.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        List<BlockPos> possibles = Lists.newArrayList();
        
        BlockPos start = stump.up(trunkCount - 3);
        
        for (int layer = 1; layer <= 7; layer++) {
            
            if (layer == 1) {
                
                this.quarterOne(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterOne(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterOne(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterOne(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
                
            } else if (layer == 2) {
                
                this.quarterTwo(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterTwo(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterTwo(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterTwo(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
                
            } else if (layer == 3 || layer == 5) {
                
                this.quarterThreeFive(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterThreeFive(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterThreeFive(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterThreeFive(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
                
            } else if (layer == 4) {
                
                this.quarterFour(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterFour(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterFour(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterFour(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
                
            } else if (layer == 6) {
                
                this.quarterSix(start.up(layer), EnumFacing.SOUTH, leaves, nodes, possibles);
                this.quarterSix(start.up(layer).north(), EnumFacing.WEST, leaves, nodes, possibles);
                this.quarterSix(start.up(layer).north().east(), EnumFacing.NORTH, leaves, nodes, possibles);
                this.quarterSix(start.up(layer).east(), EnumFacing.EAST, leaves, nodes, possibles);
                
            } else if (layer == 7) {
                
                for (BlockPos pos : new BlockPos[] {start.up(layer), start.up(layer).north(), start.up(layer).north().east(), start.up(layer).east()}) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                    }
                }
            }
        }
        
        Set<BlockPos> clumps = Sets.newHashSet();
        
        for (int amount = 5 + this.rand.nextInt(6); amount >= 0; amount--) {
            
            clumps.add(possibles.get(this.rand.nextInt(possibles.size())));
        }
        
        for (BlockPos clump : clumps) {
            
            this.layerPoints(clump.down(), 1, leaves, 2);
            this.layerCorners(clump, 1, leaves, ECornerAmount.OUTER_CHANCE);
            this.layerPoints(clump.up(), 1, leaves, 2);
            
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
                
                this.setBlock(node, GeoBlocks.LEAVES_NODE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BRAZIL));
            }
        }
        
        return true;
    }
    
    private void quarterOne(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        nodes.add(centre);
        
        if (this.rand.nextInt(2) == 0) {
        
            nodes.add(centre.offset(facing));
            possibles.add(centre.offset(facing));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            nodes.add(centre.offset(facing.rotateY()));
            possibles.add(centre.offset(facing.rotateY()));
        }
    }
    
    private void quarterTwo(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 2; front++) {
            
            for (int side = 0; side <= 2; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side == 2) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        possibles.add(pos);
                        
                        if (front == 0 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else if (front + side < 2) {
                    
                    leaves.add(pos);
                    
                    if (front == 0 || side == 0) {
                        
                        nodes.add(pos);
                    }
                }
            }
        }
    }
    
    private void quarterThreeFive(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 4; front ++) {
            
            for (int side = 0; side <= 4; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 6) {
                    
                } else if (front + side == 6 || front == 4 || side == 4) {
                    
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
        
    private void quarterFour(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 6; front ++) {
            
            for (int side = 0; side <= 6; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 8) {
                    
                } else if (front + side == 8 || front == 6 || side == 6) {
                    
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
    
    private void quarterSix(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
        
        for (int front = 0; front <= 3; front++) {
            
            for (int side = 0; side <= 3; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if (front + side > 4) {
                    
                } else if (front + side == 4 || front == 3 || side == 3) {
                    
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
