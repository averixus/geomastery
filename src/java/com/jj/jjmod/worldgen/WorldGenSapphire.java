package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for sapphire lode. */
public class WorldGenSapphire extends WorldGenStone {

    public WorldGenSapphire(World world, Random rand) {

        super(world, rand, ModBlocks.lodeSapphire.getDefaultState(),
                80, 120, 4, 1);
    }

    @Override
    protected int getVeinSize() {

        return 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
