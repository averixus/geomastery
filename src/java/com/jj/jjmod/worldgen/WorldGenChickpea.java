package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for Chickpea crops. */
public class WorldGenChickpea extends WorldGenCrop {

    public WorldGenChickpea(World world, Random rand) {
        
        super(world, rand, ModBlocks.chickpea.getFullgrown(), 20, 4);
    }
}
