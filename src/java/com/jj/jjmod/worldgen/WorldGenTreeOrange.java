package com.jj.jjmod.worldgen;

import java.util.ArrayList;
import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for orange trees. */
public class WorldGenTreeOrange extends WorldGenTree {
    
    public WorldGenTreeOrange(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 30, 4, ModBlocks.seedlingOrange);
    }

    @Override
    public boolean generateTree(BlockPos pos) {
        
        ArrayList<BlockPos> trunks = new ArrayList<BlockPos>();

        trunks.add(pos);
        trunks.add(pos.up());
        trunks.add(pos.up(2));
        trunks.add(pos.up(3));
        
        for (BlockPos trunk : trunks) {
            
            if (!this.world.getBlockState(trunk).getBlock()
                    .isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, ModBlocks.woodOrange.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = new ArrayList<BlockPos>();
        
        BlockPos top = pos.up(3);
        
        leaves.add(top.north());
        leaves.add(top.north().up());
        leaves.add(top.north().down());
        leaves.add(top.north().east());
        leaves.add(top.north().west());
        leaves.add(top.north().west().up());
        leaves.add(top.north().east().up());
        leaves.add(top.north().west().down());
        leaves.add(top.north().east().down());
        leaves.add(top.south());
        leaves.add(top.south().up());
        leaves.add(top.south().down());
        leaves.add(top.south().east());
        leaves.add(top.south().west());
        leaves.add(top.south().west().up());
        leaves.add(top.south().east().up());
        leaves.add(top.south().west().down());
        leaves.add(top.south().east().down());
        leaves.add(top.down().west());
        leaves.add(top.down().west(2));
        leaves.add(top.west());
        leaves.add(top.west(2));
        leaves.add(top.up().west());
        leaves.add(top.up().west(2));
        leaves.add(top.up(2).west());
        leaves.add(top.down().east());
        leaves.add(top.down().east(2));
        leaves.add(top.east());
        leaves.add(top.east(2));
        leaves.add(top.up().east());
        leaves.add(top.up().east(2));
        leaves.add(top.up(2).east());
        leaves.add(top.up());
        leaves.add(top.up(2));
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, ModBlocks.leafOrange.getDefaultState());
            }
        }
        
        return true;
    }
}
