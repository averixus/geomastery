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

public class WorldGenTreeAcaciaMedium extends WorldGenTreeAbstract {

    public WorldGenTreeAcaciaMedium(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int trunkCount = 6 + this.rand.nextInt(3);
        
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
        
        this.setBlock(stump, GeoBlocks.BOLE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.ACACIA));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.ACACIA));
        }
        
        Set<BlockPos> leaves = Sets.newHashSet();
        Set<BlockPos> nodes = Sets.newHashSet();
        List<BlockPos> possibles = Lists.newArrayList();
        
        EnumFacing facing = this.randHorizontal();
        
        this.layerLarge(stump.up(trunkCount + 1), facing, leaves, nodes, possibles);
        this.layerSmall(stump.up(trunkCount + 2), facing, leaves, nodes);
        
        for (int i = this.rand.nextInt(3) + 1; i >= 0; i--) {
            
            this.layerSmall(possibles.get(this.rand.nextInt(possibles.size())).down(), this.randHorizontal(), leaves, nodes);
        }
        
        for (int i = this.rand.nextInt(3); i >= 0; i--) {
            
            this.layerSmall(stump.up(trunkCount - 1), this.randHorizontal(), leaves, nodes);
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
    
    private void layerLarge(BlockPos trunk, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes, Collection<BlockPos> possibles) {
                
        for (int front = -2; front <= 3; front++) {
            
            for (int side = -2; side <= 3; side++) {
                
                BlockPos pos = trunk.offset(facing, front).offset(facing.rotateY(), side);
                
                if ((front == -2 && (side == -2 || side == 3)) || (front == 3 && (side == -2 || side == 3))) {
                    
                } else if (front == -2 || front == 3 || side == -2 || side == 3) {
                    
                    possibles.add(pos);
                    leaves.add(pos);
                    
                    if (front == 0 || side == 0) {
                        
                        nodes.add(pos);
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
    
    private void layerSmall(BlockPos trunk, EnumFacing facing, Collection<BlockPos> leaves, Collection<BlockPos> nodes) {
        
        for (int front = -1; front <= 2; front++) {
            
            for (int side = -1; side <= 2; side++) {
                
                BlockPos pos = trunk.offset(facing, front).offset(facing.rotateY(), side);
                
                if ((front == -1 && (side == -1 || side == 2)) || (front == 2 && (side == -1 || side == 2))) {
                                        
                    if (this.rand.nextInt(2) == 0) {
                        
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
