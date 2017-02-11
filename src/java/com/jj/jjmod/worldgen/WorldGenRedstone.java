package com.jj.jjmod.worldgen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for Redstone Lode. */
public class WorldGenRedstone extends WorldGenStone {

    public WorldGenRedstone(World world, Random rand) {

        super(world, rand, Blocks.REDSTONE_ORE.getDefaultState(), 5, 45, 2, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(39) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.5) {

            return true;
        }

        return false;
    }
}
