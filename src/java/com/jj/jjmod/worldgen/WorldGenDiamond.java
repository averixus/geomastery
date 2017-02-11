package com.jj.jjmod.worldgen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for Diamond Lode. */
public class WorldGenDiamond extends WorldGenStone {

    public WorldGenDiamond(World world, Random rand) {

        super(world, rand, Blocks.DIAMOND_ORE.getDefaultState(), 0, 15, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(79) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.05) {

            return true;
        }

        return false;
    }
}
