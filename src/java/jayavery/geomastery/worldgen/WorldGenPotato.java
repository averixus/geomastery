package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for potato crops. */
public class WorldGenPotato extends WorldGenCrop {

    public WorldGenPotato(World world, Random rand) {
        
        super(world, rand, GeoBlocks.POTATO.getFullgrown(), 20, 5);
    }
}
