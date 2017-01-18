package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenLapis extends WorldGenStone {

    public WorldGenLapis(World world, Random rand) {

        super(world, rand, Blocks.LAPIS_ORE.getDefaultState(), 60, 70, 2);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(19) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.6) {

            return true;
        }

        return false;
    }
}
