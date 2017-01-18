package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.worldgen.abstracts.WorldGenTree;
import net.minecraft.world.World;

@FunctionalInterface
public interface ITreeGenRef {
    
    public WorldGenTree makeTreeGen(World world, Random rand, boolean isSapling);
}
