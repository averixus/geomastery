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

public class WorldGenTreeAcaciaSmall extends WorldGenTreeAbstract {

    public WorldGenTreeAcaciaSmall(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos stump) {
        
        ArrayList<BlockPos> trunks = Lists.newArrayList();
        
        int trunkCount = this.rand.nextInt(2);
        
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
            
            if (!(found instanceof BlockSapling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.ACACIA));
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.ACACIA));
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        
        leaves.add(stump.up(trunkCount + 1));
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(trunkCount + 1)
                    .offset(EnumFacing.HORIZONTALS[this.rand
                    .nextInt(EnumFacing.HORIZONTALS.length)]));
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.ACACIA));
            }
        }
        
        return true;
    }
}
