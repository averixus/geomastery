package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenSilver extends WorldGenStone {

    public WorldGenSilver(World world, Random rand) {

        super(world, rand, ModBlocks.oreSilver.getDefaultState(), 10, 50, 3);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(2) + 1;
        int rand2 = this.rand.nextInt(2) + 1;
        int rand3 = this.rand.nextInt(2) + 1;

        return rand1 + rand2 + rand3 - 2;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
