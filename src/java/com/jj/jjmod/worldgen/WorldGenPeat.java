package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for peat blocks. */
public class WorldGenPeat extends WorldGenSurface {

    public WorldGenPeat(World world, Random rand) {
        
        super(world, rand, 75, 90, ModBlocks.peat.getDefaultState());
    }

    @Override
    protected int getClusterSize() {

        return this.rand.nextInt(100) + 1;
    }
}
