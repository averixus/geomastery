package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockSeedling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** Abstract superclass for tree WorldGenerators. */
public abstract class WorldGenTree extends WorldGenAbstract {
    
    /** Chance to generate per chunk. */
    private static final float CHANCE = 0.002F;
    
    /** Whether to send block updates for this generation. */
    protected boolean notify;
    /** Average spacing between trees. */
    protected int spread;
    /** Maximum number of trees generated per cluster. */
    protected int maxCluster;
    /** Type of seedling to generate. */
    protected BlockSeedling seedlingType;
    
    public WorldGenTree(World world, Random rand, boolean isSapling,
            int spread, int maxCluster, BlockSeedling seedlingType) {
        
        super(world, rand);
        this.notify = isSapling;
        this.spread = spread;
        this.maxCluster = maxCluster;
        this.seedlingType = seedlingType;
    }
    
    /** Generates a single tree. */
    public abstract boolean generateTree(BlockPos pos); 
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        Biome biome = this.world.getBiome(new
                BlockPos(xFromChunk, 0, zFromChunk));
        
        if (this.rand.nextFloat() <= CHANCE &&
                this.seedlingType.isPermitted(biome)) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findValidDirt(x, z);
            
            int cluster = 0;
            int tries = 0;
            
            while (tries < this.maxCluster * 2 && cluster < this.maxCluster) {
                
                if (y != -1) {
                    
                    cluster = this.generateTree(new BlockPos(x, y, z)) ?
                            cluster + 1 : cluster;
                }
                
                tries++;
                
                x = centreX + this.rand.nextInt(this.spread);
                z = centreZ + this.rand.nextInt(this.spread);
                y = this.findValidDirt(x, z);
            }
        }
    }   
    
    /** Sets a blockstate with flags according to the phase of generation. */
    protected void setBlock(BlockPos pos, IBlockState state) {
        
        if (this.notify) {
            
            this.world.setBlockState(pos, state);
            
        } else {
            
            this.world.setBlockState(pos, state, 2);
        }
    }
}
