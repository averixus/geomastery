package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for chickpea crops. */
public class WorldGenChickpea extends WorldGenCrop {

    public WorldGenChickpea(World world, Random rand) {
        
        super(world, rand, GeoBlocks.chickpea.getFullgrown(), 20, 4);
    }
}
