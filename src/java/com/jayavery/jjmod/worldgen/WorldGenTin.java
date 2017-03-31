package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for tin ore. */
public class WorldGenTin extends WorldGenStone {

    public WorldGenTin(World world, Random rand) {

        super(world, rand, ModBlocks.oreTin.getDefaultState(), 30, 120, 10, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(3);
        int rand2 = this.rand.nextInt(3);

        return rand1 + rand2 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
