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
import com.google.common.collect.Lists;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenTreeWillowLarge extends WorldGenTreeAbstract {

    public WorldGenTreeWillowLarge(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int layerCount = 7 + this.rand.nextInt(3);
        int trunkCount = layerCount - 1 - this.rand.nextInt(2);
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.WILLOW));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_LARGE.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.WILLOW));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        ArrayList<BlockPos> nodes = Lists.newArrayList();
        BlockPos start = stump.up(trunkCount + 4);
        int layer = 0;
        
        this.layerCorners(start.down(layer), 1, leaves, ECornerAmount.OUTER_CHANCE);
        layer = layer + 1;
        
        for (; layer <= layerCount; layer++) {
            
            this.layer(start.down(layer), leaves, nodes);
        }
        
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (this.rand.nextInt(2) == 0) {
            
                this.sideClump(start, facing, layerCount, leaves, nodes);
            }
            
            if (this.rand.nextInt(2) == 0) {
            
                this.cornerClump(start, facing, layerCount, leaves, nodes);
            }
        }
        
        
        for (BlockPos node : nodes) {
            
            if (this.world.getBlockState(node).getBlock()
                    .isReplaceable(this.world, node)) {
            
                this.setBlock(node, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, true).withProperty(BlockLeaves.TYPE, ETreeType.WILLOW));
            }
        }   
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.WILLOW));
            }
        }
        
        
        
        return true;
    }
    
    private void layer(BlockPos start, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                int sum = Math.abs(x) + Math.abs(z);
                
                if (sum <= 3) {
                    
                    leaves.add(start.add(x, 0, z));
                    
                    if (x == 0 || z == 0) {
                        
                        nodes.add(start.add(x, 0, z));
                    }
                }
            }
        }
    }
    
    private void sideClump(BlockPos start, EnumFacing facing, int layerCount, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        start = start.down().offset(facing, 3);
        int layer = 1;
        
        for (; layer <= layerCount; layer++) {
            
            if (layer == 1) {
                
                nodes.add(start.down(layer));
                leaves.add(start.down(layer).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing.rotateYCCW()));
                
            } else if (layer == 2) {
                
                nodes.add(start.down(layer));
                leaves.add(start.down(layer).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateYCCW()));
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateY(), 2));
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateYCCW(), 2));
                
            } else if (layer == 3 || layer == 4 || layer == layerCount - 2) {
                
                nodes.add(start.down(layer));
                leaves.add(start.down(layer).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateYCCW()));
                leaves.add(start.down(layer).offset(facing.rotateY(), 2));
                leaves.add(start.down(layer).offset(facing.rotateYCCW(), 2));
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateY(), 2));
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateYCCW(), 2));
                
            } else if (layer == layerCount - 1) {
                
                nodes.add(start.down(layer));
                leaves.add(start.down(layer).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateYCCW()));
                leaves.add(start.down(layer).offset(facing.rotateY(), 2));
                leaves.add(start.down(layer).offset(facing.rotateYCCW(), 2));
                
            } else if (layer == layerCount) {
                
                nodes.add(start.down(layer).offset(facing));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateYCCW()));
            
            } else {
                
                nodes.add(start.down(layer));
                leaves.add(start.down(layer).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateY()));
                leaves.add(start.down(layer).offset(facing).offset(facing.rotateYCCW()));
                leaves.add(start.down(layer).offset(facing.rotateY(), 2));
                leaves.add(start.down(layer).offset(facing.rotateYCCW(), 2));
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateY(), 2));
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateYCCW(), 2));
            }
        }
            
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing, 2).offset(facing.rotateY()));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing, 2).offset(facing.rotateYCCW()));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing, 2));
            }
        }
        
        
    }
    
    private void cornerClump(BlockPos start, EnumFacing facing, int layerCount, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        start = start.down().offset(facing, 3).offset(facing.rotateY(), 3);
        int layer = 1;
        
        for (; layer <= layerCount; layer++) {
            
            if (layer == 1) {
                
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateYCCW()));
                
            } else if (layer == 2) {
                
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing.rotateYCCW(), 3));
                nodes.add(start.down(layer).offset(facing.rotateYCCW(), 2));
                nodes.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing.getOpposite(), 3));
                nodes.add(start.down(layer).offset(facing.getOpposite(), 2));
                nodes.add(start.down(layer).offset(facing.getOpposite()));
                
            } else if (layer == layerCount - 1) {
                
                nodes.add(start.down(layer));
                nodes.add(start.down(layer).offset(facing.rotateYCCW(), 3));
                nodes.add(start.down(layer).offset(facing.rotateYCCW(), 2));
                nodes.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing.getOpposite(), 3));
                nodes.add(start.down(layer).offset(facing.getOpposite(), 2));
                nodes.add(start.down(layer).offset(facing.getOpposite()));
                
            } else if (layer == layerCount) {
                
                leaves.add(start.down(layer));
                
            } else {
                
                leaves.add(start.down(layer).offset(facing.getOpposite()).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer));
                nodes.add(start.down(layer).offset(facing.rotateYCCW(), 3));
                nodes.add(start.down(layer).offset(facing.rotateYCCW(), 2));
                nodes.add(start.down(layer).offset(facing.rotateYCCW()));
                nodes.add(start.down(layer).offset(facing.getOpposite(), 3));
                nodes.add(start.down(layer).offset(facing.getOpposite(), 2));
                nodes.add(start.down(layer).offset(facing.getOpposite()));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing).offset(facing.rotateYCCW()));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing).offset(facing.rotateYCCW(), 2));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing.rotateY()));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing.rotateY()).offset(facing.getOpposite()));
            }
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            for (int i = 0; i <= layerCount - 2; i++) {
                
                leaves.add(start.down(i + 3).offset(facing.rotateY()).offset(facing.getOpposite(), 2));
            }
        }
    }
}
