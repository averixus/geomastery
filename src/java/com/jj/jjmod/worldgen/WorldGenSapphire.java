package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.world.World;

public class WorldGenSapphire extends WorldGenStone {

    public WorldGenSapphire(World world, Random rand) {

        super(world, rand, ModBlocks.lodeSapphire.getDefaultState(),
                80, 120, 4);
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
