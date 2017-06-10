package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Abstract superclass for generating blocks on the surface. */
public class WorldGenPeat extends WorldGenAbstract {

    /** Chance of generating per chunk. */
    private static final float CHANCE = 1F;
    
    /** State of the block to generate. */
    protected IBlockState block;
    /** Minimum y co-ordinate to generate at. */
    protected int minHeight;
    /** Maximum y co-ordinate to generate at. */
    protected int maxHeight;
    
    public WorldGenPeat(World world, Random rand) {
        
        super(world, rand);
        this.minHeight = 75;
        this.maxHeight = 90;
        this.block = GeoBlocks.PEAT.getDefaultState();
    }
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        if (this.rand.nextFloat() <= CHANCE) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findValidSurface(x, z) - 1;
            
            int cluster = this.rand.nextInt(100) + 1;
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
