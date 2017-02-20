package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenSurface extends WorldGenAbstract {

    protected IBlockState block;
    protected int minHeight;
    protected int maxHeight;
    
    public WorldGenSurface(World world, Random rand, int minHeight, int maxHeight, IBlockState block) {
        
        super(world, rand);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.block = block;
    }
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        if (this.rand.nextFloat() <= 0.05) {
            
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
                    System.out.println("putting" + this.block + " at " + pos);
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
    
    protected abstract int getClusterSize();
}
