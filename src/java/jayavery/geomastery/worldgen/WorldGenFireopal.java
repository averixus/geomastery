package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for Fire Opal Lode. */
public class WorldGenFireopal extends WorldGenStone {

    public WorldGenFireopal(World world, Random rand) {

        super(world, rand, GeoBlocks.LODE_FIREOPAL.getDefaultState(),
                5, 15, 2, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(15) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.3) {

            return true;
        }

        return false;
    }
}
