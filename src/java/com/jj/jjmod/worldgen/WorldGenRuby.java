package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenRuby extends WorldGenStone {

    public WorldGenRuby(World world, Random rand) {

        super(world, rand, ModBlocks.lodeRuby.getDefaultState(), 0, 256, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(29) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.2) {

            return true;
        }

        return false;
    }
}
