package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenTomato extends WorldGenCrop {

    public WorldGenTomato(World world, Random rand) {
        
        super(world, rand, ModBlocks.tomato.getFullgrown(), 20, 7);
    }
}
