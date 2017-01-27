package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenFireopal extends WorldGenStone {

    public WorldGenFireopal(World world, Random rand) {

        super(world, rand, ModBlocks.lodeFireopal.getDefaultState(), 5, 15, 2, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(15) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.3) {

            return true;
        }

        return false;
    }
}
