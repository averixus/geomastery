package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for bean blocks. */
public class WorldGenBean extends WorldGenCrop {

    public WorldGenBean(World world, Random rand) {
        
        super(world, rand, GeoBlocks.BEAN.getFullgrown(), 30, 5);
    }
}
