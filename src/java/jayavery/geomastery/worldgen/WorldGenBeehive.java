package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockBeehive;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for beehive blocks. */
public class WorldGenBeehive extends WorldGenAbstract {
    
    /** Chance of generating per chunk. */
    private static final float CHANCE = 0.01F;

    public WorldGenBeehive(World world, Random rand) {
        
        super(world, rand);
    }

    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {

        if (this.rand.nextFloat() <= CHANCE) {
            
            int x = 0;
            int z = 0;
            int y = -1;
            int tries = 0;
            
            while (y == -1 && tries < 100) {
                
                tries++;
                x = this.rand.nextInt(16) + xFromChunk;
                z = this.rand.nextInt(16) + zFromChunk;
                y = this.findValidLog(x, z);
            }
            
            if (y != -1) {
                
                BlockPos hive = new BlockPos(x, y, z);
                IBlockState state = GeoBlocks.BEEHIVE.getDefaultState();
                
                for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                        
                    state = state.withProperty(BlockBeehive.FACING, facing);
                    
                    if (GeoBlocks.BEEHIVE.canStay(this.world, hive, state)) {

                        this.world.setBlockState(hive, state);
                        return;
                    }
                }
            }
        }
    }
}
