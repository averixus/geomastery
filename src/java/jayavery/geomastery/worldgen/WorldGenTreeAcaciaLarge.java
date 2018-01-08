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
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeAcaciaLarge extends WorldGenTreeAbstract {

    public WorldGenTreeAcaciaLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 13 + this.rand.nextInt(4);
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        for (int i = 1; i <= trunkCount; i++) {
            
            trunks.add(stump.up(i));
        }
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.ACACIA));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.ACACIA));
        }
        
        EnumFacing facing = this.randHorizontal();
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        List<Clump> clumps = Lists.newArrayList();
                
        BlockPos centre = stump.up(trunkCount + 1).offset(facing.getOpposite());
        this.quarterLarge(centre, facing, leaves, nodes, clumps);
        this.quarterLarge(centre.offset(facing.getOpposite()), facing.rotateY(), leaves, nodes, clumps);
        this.quarterLarge(centre.offset(facing.getOpposite()).offset(facing.rotateYCCW()), facing.getOpposite(), leaves, nodes, clumps);
        this.quarterLarge(centre.offset(facing.rotateYCCW()), facing.rotateYCCW(), leaves, nodes, clumps);
        
        centre = centre.up();
        this.quarterSmall(centre, facing, leaves);
        this.quarterSmall(centre.offset(facing.getOpposite()), facing.rotateY(), leaves);
        this.quarterSmall(centre.offset(facing.getOpposite()).offset(facing.rotateYCCW()), facing.getOpposite(), leaves);
        this.quarterSmall(centre.offset(facing.rotateYCCW()), facing.rotateYCCW(), leaves);
        
        for (int amount = this.rand.nextInt(4) + 1; amount >= 0; amount--) {
            
            Clump clump = clumps.get(this.rand.nextInt(clumps.size()));
            clump.build(leaves, nodes);
            int clumpX = clump.centre.getX();
            int centreX = stump.getX();
            
            for (int nodeX = Math.min(clumpX, centreX); nodeX <= Math.max(clumpX, centreX); nodeX++) {
                
                nodes.add(new BlockPos(nodeX, clump.centre.getY() + 1, clump.centre.getZ()));
            }
        }
        
        for (int amount = this.rand.nextInt(2) + 1; amount >= 0; amount--) {
            
            this.new Clump(stump.up(trunkCount - 1), this.randHorizontal(), ClumpType.SINGLE).build(leaves, nodes);
        }
        
        int height = trunkCount - 4 - this.rand.nextInt(2);
        for (int amount = this.rand.nextInt(2) + 1; amount >= 0; amount--) {
            
            this.new Clump(stump.up(height), this.randHorizontal(), ClumpType.UPWARDS).build(leaves, nodes);
        }
                
        for (BlockPos node : nodes) {
            
            if (this.world.getBlockState(node).getBlock()
                    .isReplaceable(this.world, node)) {
                
                this.setBlock(node, GeoBlocks.LEAVES_NODE.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.ACACIA));
            }
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.ACACIA));
            }
        }
        
        return true;
    }
    
    private void quarterLarge(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<Clump> clumps) {
        
        for (int front = 0; front <= 4; front++) {
            
            for (int side = 0; side <= 4; side++) {
                
                BlockPos pos = centre.offset(facing, front).offset(facing.rotateY(), side);
                
                if ((front == 4 && side == 1) || (side == 4 && front == 1) || (front == 3 && side == 3)) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(pos);
                        
                        if (front == 0 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                    
                } else if (front + side <= 5) {
                    
                    leaves.add(pos);
                    
                    if (front == 0 || side == 0 || front == 4 || side == 4) {
                        
                        nodes.add(pos);
                    }
                }
                
                if (front == 3 && side == 3) {
                    
                    clumps.add(this.new Clump(pos.down(), facing, ClumpType.CORNER));
                    
                } else if (front == 0 && side == 4) {
                    
                    clumps.add(this.new Clump(pos.down(), facing, ClumpType.EDGE));
                }
            }
        }
    }
    
    private void quarterSmall(BlockPos centre, EnumFacing facing, Collection<BlockPos> leaves) {
        
        for (int front = 0; front <= 2; front++) {
            
            for (int side = 0; side <= 2; side++) {
                                
                if (front + side == 2) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                    }
                    
                } else if (front + side < 2) {
                    
                    leaves.add(centre.offset(facing, front).offset(facing.rotateY(), side));
                }
            }
        }
    }
    
    enum ClumpType {
        
        CORNER, EDGE, UPWARDS, SINGLE;
        
        Clump[] getExtraClumps(Clump clump) {
                        
            switch (this) {
                
                case CORNER:
                    return new Clump[] {clump.newClump(clump.centre.offset(clump.facing, 3).down(), clump.facing, SINGLE),
                            clump.newClump(clump.centre.offset(clump.facing).offset(clump.facing.rotateY(), 3).down(), clump.facing.rotateY(), SINGLE)};
                case EDGE:
                    return new Clump[] {clump.newClump(clump.centre.offset(clump.facing, 2).offset(clump.facing.rotateYCCW(), 2).down(), clump.facing, SINGLE),
                            clump.newClump(clump.centre.offset(clump.facing, 2).offset(clump.facing.rotateY(), 2).down(), clump.facing, SINGLE)};
                case UPWARDS:
                    return new Clump[] {clump.newClump(clump.centre.offset(clump.facing, 3).up(), clump.facing, SINGLE),
                            clump.newClump(clump.centre.offset(clump.facing, 2).offset(clump.facing.rotateY(), 2).up(), clump.facing, SINGLE),
                            clump.newClump(clump.centre.offset(clump.facing).offset(clump.facing.rotateY(), 3).up(), clump.facing.rotateY(), SINGLE)};
                default:
                    return new Clump[] {};
            }
        }
    }
    
    class Clump {
        
        final BlockPos centre;
        final EnumFacing facing;
        final ClumpType type;
        
        Clump(BlockPos centre, EnumFacing facing, ClumpType type) {
            
            this.centre = centre;
            this.facing = facing;
            this.type = type;
        }
        
        Clump newClump(BlockPos centre, EnumFacing facing, ClumpType type) {
            
            return new Clump(centre, facing, type);
        }
        
        void build(Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
            
            this.buildSingle(leaves, nodes);
            Clump[] extras = this.type.getExtraClumps(this);
            
            if (extras.length == 0) {
                
                return;
            }
            
            for (int amount = WorldGenTreeAcaciaLarge.this.rand.nextInt(3); amount >= 0; amount--) {
                
                extras[WorldGenTreeAcaciaLarge.this.rand.nextInt(extras.length)].buildSingle(leaves, nodes);
            }
        }
        
        private void buildSingle(Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
            
            for (int front = -1; front <= 2; front++) {
                
                for (int side = -1; side <= 2; side++) {
                    
                    BlockPos pos = this.centre.offset(this.facing, front).offset(this.facing.rotateY(), side);
                    
                    if ((front == -1 && (side == -1 || side == 2)) || (front == 2 && (side == -1 || side == 2))) {

                        if (WorldGenTreeAcaciaLarge.this.rand.nextInt(2) == 0) {
                            
                            leaves.add(pos);
                        }
                        
                    } else {
                        
                        leaves.add(pos);
                        
                        if (front == 0 || front == 1 || side == 1 || side == 0) {
                            
                            nodes.add(pos);
                        }
                    }
                }
            }
        }
    }
}
