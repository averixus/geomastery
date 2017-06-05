package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for wheat crops. */
public class WorldGenWheat extends WorldGenCrop {

    public WorldGenWheat(World world, Random rand) {
        
        super(world, rand, GeoBlocks.wheat.getFullgrown(), 10, 7);
    }
}
