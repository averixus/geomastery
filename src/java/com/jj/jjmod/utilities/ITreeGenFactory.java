package com.jj.jjmod.utilities;

import java.util.Random;
import com.jj.jjmod.worldgen.WorldGenTree;
import net.minecraft.world.World;

/** Functional interface for creating a WorldGenTree. */
@FunctionalInterface
public interface ITreeGenFactory {
    
    /** @return A new WorldGenTree. */
    public WorldGenTree makeTreeGen(World world, Random rand,
            boolean isSapling);
}
