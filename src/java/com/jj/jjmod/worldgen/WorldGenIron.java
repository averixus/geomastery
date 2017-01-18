package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenIron extends WorldGenStone {

    public WorldGenIron(World world, Random rand) {

        super(world, rand, Blocks.IRON_ORE.getDefaultState(), 20, 60, 10);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(3) + 1;
        int rand2 = this.rand.nextInt(3) + 1;
        int rand3 = this.rand.nextInt(3) + 1;

        return rand1 + rand2 + rand3 - 2;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
