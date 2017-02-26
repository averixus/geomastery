package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for berry crops. */
public class WorldGenBerry extends WorldGenCrop {

    public WorldGenBerry(World world, Random rand) {
        
        super(world, rand, ModBlocks.berry.getFullgrown(), 10, 8);
    }
}
