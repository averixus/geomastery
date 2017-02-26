package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for Hemp crops. */
public class WorldGenHemp extends WorldGenCrop {

    public WorldGenHemp(World world, Random rand) {
        
        super(world, rand, ModBlocks.hemp.getFullgrown(), 30, 3);
    }
}
