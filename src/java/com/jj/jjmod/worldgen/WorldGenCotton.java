package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenCotton extends WorldGenCrop {

    public WorldGenCotton(World world, Random rand) {
        
        super(world, rand, ModBlocks.cotton.getFullgrown(), 30, 5);
    }
}
