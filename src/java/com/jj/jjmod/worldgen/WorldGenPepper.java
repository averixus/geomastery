package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for Pepper crops. */
public class WorldGenPepper extends WorldGenCrop {

    public WorldGenPepper(World world, Random rand) {
        
        super(world, rand, ModBlocks.pepper.getFullgrown(), 30, 3);
    }
}
