package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenTin extends WorldGenStone {

    public WorldGenTin(World world, Random rand) {

        super(world, rand, ModBlocks.oreTin.getDefaultState(), 30, 80, 5);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(2) + 1;
        int rand2 = this.rand.nextInt(2) + 1;

        return rand1 + rand2 - 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
