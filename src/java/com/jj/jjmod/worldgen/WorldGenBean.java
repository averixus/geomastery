package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;

import net.minecraft.world.World;

public class WorldGenBean extends WorldGenCrop {

    public WorldGenBean(World world, Random rand) {
        
        super(world, rand, ModBlocks.bean.getFullgrown(), 30, 5);
    }
}
