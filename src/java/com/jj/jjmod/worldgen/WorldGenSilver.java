package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for silver ore. */
public class WorldGenSilver extends WorldGenStone {

    public WorldGenSilver(World world, Random rand) {

        super(world, rand, ModBlocks.oreSilver.getDefaultState(), 10, 50, 3, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(2);
        int rand2 = this.rand.nextInt(2);
        int rand3 = this.rand.nextInt(2);

        return rand1 + rand2 + rand3 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
