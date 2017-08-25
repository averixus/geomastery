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

// copy of larch
public class WorldGenTreeSpruceSmall extends WorldGenTreeAbstract {

    public WorldGenTreeSpruceSmall(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {
        
        return this.generateTreeC(pos);
    }
    
    private boolean generateTreeA(BlockPos stump) {
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();
        leaves.add(stump.up());
        
        if (this.rand.nextInt(2) == 0) {
            
            leaves.add(stump.up(2));
            leaves.add(stump.up(3));
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.SPRUCE));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeB(BlockPos stump) {
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
                
        int layer = 1;
        
        if (this.rand.nextInt(2) == 0) {
            
            Block trunkFound = this.world.getBlockState(stump.up(layer)).getBlock();
            
            if (!trunkFound.isReplaceable(this.world, stump)) {
                
                return false;
            }
            
            this.setBlock(stump.up(layer), GeoBlocks.TREE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
            layer++;            
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
        
        for (int i = layer; i <= layer + 1; i++) {
            
            BlockPos leaf = stump.up(i);
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, ETreeType.SPRUCE));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeC(BlockPos stump) {
        
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        Block trunkFound = this.world.getBlockState(stump.up()).getBlock();
        
        if (!trunkFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        this.setBlock(stump, GeoBlocks.BOLE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
        this.setBlock(stump.up(), GeoBlocks.TREE_SMALL.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));

        ArrayList<BlockPos> leaves = Lists.newArrayList();
        leaves.add(stump.up(2));
        leaves.add(stump.up(3));
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            leaves.add(stump.up().offset(facing));
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAVES.getDefaultState().withProperty(BlockTree.TYPE, ETreeType.SPRUCE));
            }
        }
        
        return true;
    }
}
