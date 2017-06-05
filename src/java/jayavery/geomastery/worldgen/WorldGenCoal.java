package jayavery.geomastery.worldgen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenrator for coal ore. */
public class WorldGenCoal extends WorldGenStone {

    public WorldGenCoal(World world, Random rand) {

        super(world, rand, Blocks.COAL_ORE.getDefaultState(), 5, 60, 20, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(10) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
