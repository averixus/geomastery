package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenHemp extends WorldGenCrop {

    public WorldGenHemp(World world, Random rand) {
        
        super(world, rand, ModBlocks.hemp.getFullgrown(), 30, 3);
    }
}
