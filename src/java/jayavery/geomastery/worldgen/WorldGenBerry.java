package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for berry crops. */
public class WorldGenBerry extends WorldGenCrop {

    public WorldGenBerry(World world, Random rand) {
        
        super(world, rand, GeoBlocks.BERRY.getFullgrown(), 10, 8);
    }
}
