package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenSalt extends WorldGenStone {

    public WorldGenSalt(World world, Random rand) {
        
        super(world, rand, ModBlocks.salt.getDefaultState(), 20, 60, 1, 0.1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(100) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
