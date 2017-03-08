package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for tar. */
public class WorldGenTar extends WorldGenAbstract {
    
    /** Chance of generating per chunk. */
    private static final float CHANCE = 0.05F;
    
    /** State of the block to generate. */
    protected IBlockState block;
    /** Minimum y co-ordinate to generate at. */
    protected int minHeight;
    /** Maximum y co-ordinate to generate at. */
    protected int maxHeight;

    public WorldGenTar(World world, Random rand) {
        
        super(world, rand);
        this.minHeight = 30;
        this.maxHeight = 120;
        this.block = ModBlocks.tar.getDefaultState();
    }
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        if (this.rand.nextFloat() <= CHANCE) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findSurroundedSurface(x, z);
                        
            int rand1 = this.rand.nextInt(4);
            int rand2 = this.rand.nextInt(4);
            int rand3 = this.rand.nextInt(4);
            int cluster = rand1 + rand2 + rand3 + 3;
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
                y = this.findSurroundedSurface(x, z);
            }
        }
    }
}
