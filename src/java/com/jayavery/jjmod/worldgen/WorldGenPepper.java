package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for pepper crops. */
public class WorldGenPepper extends WorldGenCrop {

    public WorldGenPepper(World world, Random rand) {
        
        super(world, rand, ModBlocks.pepper.getFullgrown(), 30, 3);
    }
}
