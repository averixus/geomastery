package com.jj.jjmod.worldgen;

import java.util.ArrayList;
import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for banana trees. */
public class WorldGenTreeBanana extends WorldGenTree {

    public WorldGenTreeBanana(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 20, 6, ModBlocks.seedlingBanana);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {
        
        ArrayList<BlockPos> trunks = new ArrayList<BlockPos>();

        trunks.add(pos);
        trunks.add(pos.up());
        trunks.add(pos.up(2));
        trunks.add(pos.up(3));
        trunks.add(pos.up(4));
        
        for (BlockPos trunk : trunks) {
            
            if (!this.world.getBlockState(trunk).getBlock()
                    .isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, ModBlocks.woodBanana.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = new ArrayList<BlockPos>();
        
        BlockPos top = pos.up(5);
        
        leaves.add(top);
        leaves.add(top.north());
        leaves.add(top.north(2));
        leaves.add(top.north(3).down());
        leaves.add(top.south());
        leaves.add(top.south(2));
        leaves.add(top.south(3).down());
        leaves.add(top.east());
        leaves.add(top.east(2));
        leaves.add(top.east(3).down());
        leaves.add(top.west());
        leaves.add(top.west(2));
        leaves.add(top.west(3).down());
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, ModBlocks.leafBanana.getDefaultState());
            }
        }
        
        return true;
    }
}
