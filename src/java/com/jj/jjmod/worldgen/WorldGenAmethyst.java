package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for Amethyst Lode. */
public class WorldGenAmethyst extends WorldGenStone {

    public WorldGenAmethyst(World world, Random rand) {

        super(world, rand, ModBlocks.lodeAmethyst.getDefaultState(),
                90, 256, 2, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(4);
        int rand2 = this.rand.nextInt(4);
        int rand3 = this.rand.nextInt(4);
        int rand4 = this.rand.nextInt(4);

        return rand1 + rand2 + rand3 + rand4 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.9) {

            return true;
        }

        return false;
    }
}
