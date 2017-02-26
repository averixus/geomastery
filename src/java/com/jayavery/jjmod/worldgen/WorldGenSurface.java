package com.jayavery.jjmod.worldgen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Abstract superclass for generating blocks on the surface. */
public abstract class WorldGenSurface extends WorldGenAbstract {

    /** Chance of generating per chunk. */
    private static final float CHANCE = 0.05F;
    
    /** State of the block to generate. */
    protected IBlockState block;
    /** Minimum y co-ordinate to generate at. */
    protected int minHeight;
    /** Maximum y co-ordinate to generate at. */
    protected int maxHeight;
    
    public WorldGenSurface(World world, Random rand, int minHeight,
            int maxHeight, IBlockState block) {
        
        super(world, rand);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.block = block;
    }
    
    /** @return Size of cluster to generate. */
    protected abstract int getClusterSize();
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        if (this.rand.nextFloat() <= CHANCE) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findValidSurface(x, z) - 1;
            
            int cluster = this.rand.nextInt(this.getClusterSize()) + 1;
            int count = 0;
            int tries = 0;
            
            while (tries < cluster * 2 && count < cluster) {
                
                if (y >= this.minHeight && y <= this.maxHeight) {
                    
                    BlockPos pos = new BlockPos(x, y, z);
                    this.world.setBlockState(pos, this.block);
                    count++;
                }
                
                tries++;
                
                x = centreX + this.rand.nextInt(2);
                z = centreZ + this.rand.nextInt(2);
                y = this.findValidSurface(x, z) - 1;
            }
        }
    }
}
