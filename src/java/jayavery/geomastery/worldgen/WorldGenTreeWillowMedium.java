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

public class WorldGenTreeWillowMedium extends WorldGenTreeAbstract {

    public WorldGenTreeWillowMedium(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        int layerCount = 4 + this.rand.nextInt(3);
        int trunkCount = layerCount + this.rand.nextInt(2);
        
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
        
        this.setBlock(stump, GeoBlocks.STUMP_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.WILLOW));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.WILLOW));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        
        BlockPos start = stump.up(trunkCount + 2);
        int layer = 1;
        
        leaves.add(start);
        
        for (; layer <= layerCount; layer++) {
            
            this.layerCorners(start.down(layer), 1, leaves, ECornerAmount.OUTER_CHANCE);
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (this.rand.nextInt(2) == 0) {
                
                for (int i = 0; i < layerCount; i++) {
                    
                    leaves.add(stump.offset(facing, 2).offset(facing.rotateYCCW()).up(i + 1));
                }
            }
            
            if (this.rand.nextInt(2) == 0) {
                
                for (int i = 0; i < layerCount; i++) {
                    
                    leaves.add(stump.offset(facing, 2).offset(facing.rotateY()).up(i + 1));
                }
            }
            
            if (this.rand.nextInt(2) == 0) {
                
                for (int i = 0; i < layerCount; i++) {
                    
                    leaves.add(stump.offset(facing, 3).up(i));
                    leaves.add(stump.offset(facing, 2).up(i + 1));
                }
            }
            
            if (this.rand.nextInt(2) == 0) {
                
                for (int i = 0; i < layerCount; i++) {
                    
                    leaves.add(stump.offset(facing, 2).up(i + 2));
                    leaves.add(stump.offset(facing).up(i + 3));
                }
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
}
