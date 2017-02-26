package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for tomato crops. */
public class WorldGenTomato extends WorldGenCrop {

    public WorldGenTomato(World world, Random rand) {
        
        super(world, rand, ModBlocks.tomato.getFullgrown(), 20, 7);
    }
}
