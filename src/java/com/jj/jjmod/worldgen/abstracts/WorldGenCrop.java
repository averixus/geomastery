package com.jj.jjmod.worldgen.abstracts;

import java.util.Random;
import com.jj.jjmod.utilities.IBiomeCheck;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public abstract class WorldGenCrop extends WorldGenAbstract {

    public IBlockState crop;
    public int spread;
    public int maxCluster;
    
    public WorldGenCrop(World world, Random rand,
            IBlockState crop, int spread, int maxCluster) {
        
        super(world, rand);
        this.crop = crop;
        this.spread = spread;
        this.maxCluster = maxCluster;
    }

    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        Biome biome = this.world.getBiome(new
                BlockPos(xFromChunk, 0, zFromChunk));

        if (this.rand.nextFloat() <= 0.001 &&
                ((IBiomeCheck) this.crop.getBlock()).isPermitted(biome)) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findValidGrass(x, z);
            
            int cluster = 0;
            int tries = 0;
            
            while (tries < this.maxCluster * 2 && cluster < this.maxCluster) {
                
                if (y != -1) {
                    
                    cluster = this.generateOne(new BlockPos(x, y, z)) ? cluster + 1 : cluster;
                }
                
                tries++;
                
                x = centreX + this.rand.nextInt(this.spread);
                z = centreZ + this.rand.nextInt(this.spread);
                y = this.findValidGrass(x, z);
            }
        }
    }
    
    public boolean generateOne(BlockPos pos) {
        
        this.world.setBlockState(pos, this.crop);
        return true;
    }
}
