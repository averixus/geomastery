package com.jj.jjmod.worldgen;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for Gold Ore. */
public class WorldGenGold extends WorldGenStone {

    public WorldGenGold(World world, Random rand) {

        super(world, rand, Blocks.GOLD_ORE.getDefaultState(), 0, 40, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(2);
        int rand2 = this.rand.nextInt(2);
        int rand3 = this.rand.nextInt(2);

        return rand1 + rand2 + rand3;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
