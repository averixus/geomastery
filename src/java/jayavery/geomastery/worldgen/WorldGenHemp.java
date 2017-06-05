package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for Hemp crops. */
public class WorldGenHemp extends WorldGenCrop {

    public WorldGenHemp(World world, Random rand) {
        
        super(world, rand, GeoBlocks.hemp.getFullgrown(), 30, 3);
    }
}
