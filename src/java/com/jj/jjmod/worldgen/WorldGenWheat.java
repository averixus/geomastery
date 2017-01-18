package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;
import net.minecraft.block.BlockCrops;
import net.minecraft.world.World;

public class WorldGenWheat extends WorldGenCrop {

    public WorldGenWheat(World world, Random rand) {
        
        super(world, rand, ModBlocks.wheat.getFullgrown(), 10, 7);
    }
}
