/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockCrop;
import jayavery.geomastery.utilities.IBiomeCheck;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** Abstract superclass for WorldGenerators for crop blocks. */
public abstract class WorldGenCrop extends WorldGenAbstract {

    /** Chance of generating per chunk. */
    private static final float CHANCE = 0.002F;
    
    /** State of the crop block to generate. */
    protected IBlockState crop;
    /** Average spacing between generated blocks. */
    protected int spread;
    /** Maximum number of blocks generated per cluster. */
    protected int maxCluster;
    
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

        if (this.rand.nextFloat() <= CHANCE &&
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
                    
                    cluster = this.generateOne(new BlockPos(x, y, z)) ?
                            cluster + 1 : cluster;
                }
                
                tries++;
                
                x = centreX + this.rand.nextInt(this.spread);
                z = centreZ + this.rand.nextInt(this.spread);
                y = this.findValidGrass(x, z);
            }
        }
    }
    
    /** Attempt to generate a single block at the given position.
     * @return Whether it successfully generates. */
    protected boolean generateOne(BlockPos pos) {
        
        if (((BlockCrop) this.crop.getBlock())
                .canStay(this.world, pos)) {
        
            this.world.setBlockState(pos, this.crop);
            return true;
        
        } else {
            
            return false;
        }
    }
}
