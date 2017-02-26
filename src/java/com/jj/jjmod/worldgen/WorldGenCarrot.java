package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for carrot crops. */
public class WorldGenCarrot extends WorldGenCrop {

    public WorldGenCarrot(World world, Random rand) {
        
        super(world, rand, ModBlocks.carrot.getFullgrown(), 20, 4);
    }
}
