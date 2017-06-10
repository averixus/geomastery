package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for pepper crops. */
public class WorldGenPepper extends WorldGenCrop {

    public WorldGenPepper(World world, Random rand) {
        
        super(world, rand, GeoBlocks.PEPPER.getFullgrown(), 30, 3);
    }
}
