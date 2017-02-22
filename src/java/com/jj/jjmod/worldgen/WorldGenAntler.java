package com.jj.jjmod.worldgen;

import java.util.Random;

import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for Antler blocks. */
public class WorldGenAntler extends WorldGenAbstract {

    /** Chance of generating per chunk. */
    private static final float CHANCE = 0.001F;
    
    public WorldGenAntler(World world, Random rand) {
        
        super(world, rand);
    }
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        if (this.rand.nextFloat() <= CHANCE) {
            
            int x = 0;
            int z = 0;
            int y = -1;
            int tries = 0;
            
            while (y == -1 && tries < 10) {
                
                tries++;
                x = this.rand.nextInt(16) + xFromChunk;
                z = this.rand.nextInt(16) + zFromChunk;
                y = this.findValidSurface(x, z);
            }
            
            if (y != -1) {
                
                this.world.setBlockState(new BlockPos(x, y, z),
                        ModBlocks.antler.getDefaultState());
            }
        } 
    }
}
