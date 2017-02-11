package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for Copper Ore. */
public class WorldGenCopper extends WorldGenStone {

    public WorldGenCopper(World world, Random rand) {

        super(world, rand, ModBlocks.oreCopper.getDefaultState(),
                40, 120, 20, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(5) + 1;
        int rand2 = this.rand.nextInt(5) + 1;

        return rand1 + rand2 - 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
