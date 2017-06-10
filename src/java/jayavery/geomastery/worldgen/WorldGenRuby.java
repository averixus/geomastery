package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for ruby lode. */
public class WorldGenRuby extends WorldGenStone {

    public WorldGenRuby(World world, Random rand) {

        super(world, rand, GeoBlocks.LODE_RUBY.getDefaultState(), 0, 256, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(29) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.2) {

            return true;
        }

        return false;
    }
}
