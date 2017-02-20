package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModLiquids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class WorldGenTar extends WorldGenSurface {

    public WorldGenTar(World world, Random rand) {
        
        super(world, rand, 30, 120, ModLiquids.tarBlock.getDefaultState());
    }
    
    @Override
    protected int getClusterSize() {
        
        int rand1 = this.rand.nextInt(4) + 1;
        int rand2 = this.rand.nextInt(4) + 1;
        int rand3 = this.rand.nextInt(4) + 1;
        
        return rand1 + rand2 + rand3;
    }

}
