package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for beetroot crops. */
public class WorldGenBeetroot extends WorldGenCrop {

    public WorldGenBeetroot(World world, Random rand) {
        
        super(world, rand, GeoBlocks.beetroot.getFullgrown(), 20, 3);
    }
}
