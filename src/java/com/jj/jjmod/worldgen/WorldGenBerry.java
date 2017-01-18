package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenBerry extends WorldGenCrop {

    public WorldGenBerry(World world, Random rand) {
        
        super(world, rand, ModBlocks.berry.getFullgrown(), 10, 8);
    }
}
