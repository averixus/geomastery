package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for carrot crops. */
public class WorldGenCarrot extends WorldGenCrop {

    public WorldGenCarrot(World world, Random rand) {
        
        super(world, rand, GeoBlocks.CARROT.getFullgrown(), 20, 4);
    }
}
