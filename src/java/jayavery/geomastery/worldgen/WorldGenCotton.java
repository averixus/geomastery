package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for cotton crops. */
public class WorldGenCotton extends WorldGenCrop {

    public WorldGenCotton(World world, Random rand) {
        
        super(world, rand, GeoBlocks.COTTON.getFullgrown(), 30, 5);
    }
}
