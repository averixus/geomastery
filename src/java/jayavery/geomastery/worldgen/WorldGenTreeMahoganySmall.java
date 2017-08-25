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

public class WorldGenTreeMahoganySmall extends WorldGenTreeAbstract {

    public WorldGenTreeMahoganySmall(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {
                
    //    switch (this.rand.nextInt(3)) {
            
       //     case 0:
          //      return this.generateTreeA(pos);
        //    case 1:
                return this.generateTreeB(pos);
       //     case 2: default:
        //        return this.generateTreeC(pos);
     //   }
    }
    
    private boolean generateTreeA(BlockPos stump) {
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        int trunkCount = this.rand.nextInt(2);
        
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
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        }
        
        BlockPos leaf = stump.up(trunkCount + 1);
        
        if (this.world.getBlockState(leaf).getBlock()
                .isReplaceable(this.world, leaf)) {
        
            this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.MAHOGANY));
        }
        
        return true;
    }
    
    private boolean generateTreeB(BlockPos stump) {
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        int trunkCount = this.rand.nextInt(2);
        
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
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        leaves.add(stump.up(trunkCount + 1));
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (this.rand.nextInt(4) != 0) {
                
               leaves.add(stump.up(trunkCount + 1).offset(facing)); 
            }
        }
        
        leaves.add(stump.up(trunkCount + 2));
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.MAHOGANY));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeC(BlockPos stump) {
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        int trunkCount = 1;
        
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
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.MAHOGANY));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        leaves.add(stump.up(trunkCount + 1));
        leaves.add(stump.up(trunkCount + 2));
        leaves.add(stump.up(trunkCount + 3));
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                            
            leaves.add(stump.up(trunkCount + 1).offset(facing));
            
            if (this.rand.nextInt(3) != 0) {
                
                leaves.add(stump.up(trunkCount + 2).offset(facing));
            }
            
            if (this.rand.nextInt(3) == 0) {
                
                leaves.add(stump.up(trunkCount + 3).offset(facing));
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
}
