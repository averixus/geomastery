package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenChalk extends WorldGenStone {

    public WorldGenChalk(World world, Random rand) {
        
        super(world, rand, ModBlocks.chalk.getDefaultState(), 30, 256, 1, 0.5);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(12) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
