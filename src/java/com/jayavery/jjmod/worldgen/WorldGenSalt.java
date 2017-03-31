package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for salt blocks. */
public class WorldGenSalt extends WorldGenStone {

    public WorldGenSalt(World world, Random rand) {
        
        super(world, rand, ModBlocks.salt.getDefaultState(), 20, 60, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(50) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
