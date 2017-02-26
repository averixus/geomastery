package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for tar. */
public class WorldGenTar extends WorldGenSurface {

    public WorldGenTar(World world, Random rand) {
        
        super(world, rand, 30, 120, ModBlocks.tar.getDefaultState());
    }
    
    @Override
    protected int getClusterSize() {
        
        int rand1 = this.rand.nextInt(4);
        int rand2 = this.rand.nextInt(4);
        int rand3 = this.rand.nextInt(4);
        
        return rand1 + rand2 + rand3 + 3;
    }

}
