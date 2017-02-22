package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for potato crops. */
public class WorldGenPotato extends WorldGenCrop {

    public WorldGenPotato(World world, Random rand) {
        
        super(world, rand, ModBlocks.potato.getFullgrown(), 20, 5);
    }
}
