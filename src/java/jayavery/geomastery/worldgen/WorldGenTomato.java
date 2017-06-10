package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for tomato crops. */
public class WorldGenTomato extends WorldGenCrop {

    public WorldGenTomato(World world, Random rand) {
        
        super(world, rand, GeoBlocks.TOMATO.getFullgrown(), 20, 7);
    }
}
