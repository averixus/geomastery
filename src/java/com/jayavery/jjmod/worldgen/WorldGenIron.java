package com.jayavery.jjmod.worldgen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for Iron Ore. */
public class WorldGenIron extends WorldGenStone {

    public WorldGenIron(World world, Random rand) {

        super(world, rand, Blocks.IRON_ORE.getDefaultState(), 20, 45, 10, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(3);
        int rand2 = this.rand.nextInt(3);
        int rand3 = this.rand.nextInt(3);

        return rand1 + rand2 + rand3 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
