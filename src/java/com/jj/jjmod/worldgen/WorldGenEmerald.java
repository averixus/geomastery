package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenEmerald extends WorldGenStone {

    public WorldGenEmerald(World world, Random rand) {

        super(world, rand, Blocks.EMERALD_ORE.getDefaultState(), 0, 30, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(59) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.1) {

            return true;
        }

        return false;
    }
}
