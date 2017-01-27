package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenCoal extends WorldGenStone {

    public WorldGenCoal(World world, Random rand) {

        super(world, rand, Blocks.COAL_ORE.getDefaultState(), 5, 60, 20, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(19) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
