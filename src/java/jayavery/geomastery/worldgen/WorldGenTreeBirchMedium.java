/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
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

// TEST
public class WorldGenTreeBirchMedium extends WorldGenTreeAbstract {

    public WorldGenTreeBirchMedium(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {

      //  switch (this.rand.nextInt(2)) {
         //   case 0:
           //     return this.generateTreeA(pos);
       //     case 1: default:
                return this.generateTreeB(pos);
      //  }
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
        
        this.setBlock(stump, GeoBlocks.STUMP_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BIRCH));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BIRCH));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        
        int layer = trunkCount == 5 ? 1 : 2;
        
        if (trunkCount == 7) {
            
            this.layerCorners(stump.up(layer++), 1, leaves, ECornerAmount.OUTER_CHANCE);
        }
        
        this.layerPoints(stump.up(layer++), 2, leaves, 4);
        this.layerPoints(stump.up(layer++), 2, leaves, 4);
        this.layerOutsides(stump.up(layer++), 2, leaves, 4);
        this.layerCorners(stump.up(layer++), 1, leaves, ECornerAmount.OUTER_CHANCE);
        this.layerCorners(stump.up(layer++), 1, leaves, ECornerAmount.OUTER_REMOVED);
        this.layerPoints(stump.up(layer++), 1, leaves, 3);
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.BIRCH));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeB(BlockPos stump) {
        
        EnumFacing treeFacing = EnumFacing.HORIZONTALS[this.rand.nextInt(EnumFacing.HORIZONTALS.length)];
        EnumFacing sideFacing = treeFacing.rotateY();
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BIRCH));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.BIRCH));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        
        int layer = trunkCount == 5 ? 1 : 2;
        
        if (trunkCount == 7) {
            
            this.layerCorners(stump.up(layer++), 1, leaves, ECornerAmount.OUTER_REMOVED);
        }
        
        this.layerCorners(stump.up(layer++), 1, leaves, ECornerAmount.OUTER_REMOVED);
        
        for (int front = -1; front <= 2; front++) {
            
            for (int side = -1; side <= 2; side++) {
                
                int sum = Math.abs(front) + Math.abs(side);
                
                if (sum <= 2) {
                    
                    leaves.add(stump.up(layer).offset(treeFacing, front).offset(sideFacing, side));
                }
            }
        }
        
        layer++;
        
        leaves.add(stump.up(layer));
        leaves.add(stump.up(layer).offset(treeFacing));
        leaves.add(stump.up(layer).offset(sideFacing));
        leaves.add(stump.up(layer).offset(treeFacing).offset(sideFacing));
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(treeFacing, 2));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(treeFacing, -1));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(sideFacing, 2));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(sideFacing, -1));
        }
        
        layer++;
        
        leaves.add(stump.up(layer));
        leaves.add(stump.up(layer).offset(treeFacing));
        leaves.add(stump.up(layer).offset(sideFacing));
        leaves.add(stump.up(layer).offset(treeFacing).offset(sideFacing));
        
        layer++;
        
        leaves.add(stump.up(layer));
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(treeFacing));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(sideFacing));
        }
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(layer).offset(treeFacing).offset(sideFacing));
        }
        
        layer++;
        
        leaves.add(stump.up(layer));
        
        if (this.rand.nextInt(3) == 0) {
            
            leaves.add(stump.up(layer).offset(treeFacing));
        }
        
        if (this.rand.nextInt(3) == 0) {
            
            leaves.add(stump.up(layer).offset(sideFacing));
        }
        
        if (this.rand.nextInt(3) == 0) {
            
            leaves.add(stump.up(layer).offset(treeFacing).offset(sideFacing));
        }
        
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false).withProperty(BlockLeaves.TYPE, ETreeType.BIRCH));
            }
        }
        
        return true;
    }
}
