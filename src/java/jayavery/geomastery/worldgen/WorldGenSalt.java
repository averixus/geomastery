package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for salt blocks. */
public class WorldGenSalt extends WorldGenStone {

    public WorldGenSalt(World world, Random rand) {
        
        super(world, rand, GeoBlocks.SALT.getDefaultState(), 20, 60, 1, 0.5);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(100) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
