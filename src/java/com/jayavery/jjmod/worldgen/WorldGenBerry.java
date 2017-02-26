package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for berry crops. */
public class WorldGenBerry extends WorldGenCrop {

    public WorldGenBerry(World world, Random rand) {
        
        super(world, rand, ModBlocks.berry.getFullgrown(), 10, 8);
    }
}
