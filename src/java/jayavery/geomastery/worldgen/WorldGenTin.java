package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for tin ore. */
public class WorldGenTin extends WorldGenStone {

    public WorldGenTin(World world, Random rand) {

        super(world, rand, GeoBlocks.oreTin.getDefaultState(), 30, 120, 20, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(2);
        int rand2 = this.rand.nextInt(2);

        return rand1 + rand2 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
