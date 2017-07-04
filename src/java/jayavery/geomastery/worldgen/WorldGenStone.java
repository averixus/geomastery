/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import com.google.common.base.Predicate;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Abstract superclass for WorldGenerators replacing Stone blocks. */
public abstract class WorldGenStone extends WorldGenAbstract {

    /** Predicate for blocks this generator replaces. */
    protected Predicate<IBlockState> predicate =
            BlockMatcher.forBlock(Blocks.STONE);
    /** State of the block to generate. */
    protected IBlockState state;
    /** Minimum y co-ordinate to generate at. */
    protected int minHeight;
    /** Maximum y co-ordinate to generate at. */
    protected int maxHeight;
    /** Number of veins to generate per chunk. */
    protected int veinCount;
    /** Chance of generating each vein. */
    protected double veinChance;

    public WorldGenStone(World world, Random rand, IBlockState state,
            int minHeight, int maxHeight, int veinCount, double veinChance) {

        super(world, rand);
        this.state = state;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.veinCount = veinCount;
        this.veinChance = veinChance;
    }
    
    /** @return The size of a single vein of this block. */
    protected abstract int getVeinSize();

    /** @return Whether a single block in a vein should be generated or not. */
    protected abstract boolean shouldGenBlock();

    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {

        if (this.rand.nextDouble() >= this.veinChance) {
            
            return;
        }
        
        for (int i = 0; i < this.veinCount; i++) {

            // Weighted random y co-ordinate
            int range = this.maxHeight - this.minHeight;
            double randomise = range / 2;
            double rand1 = this.rand.nextDouble() * randomise;
            double rand2 = this.rand.nextDouble() * randomise;
            double rand3 = this.rand.nextDouble() * randomise;
            double result = (rand1 + rand2 + rand3) - Math
                    .max(Math.max(rand1, rand2), rand3);

            // Co-ordinates
            int y = (int) (result + this.minHeight);
            int x = this.rand.nextInt(16) + xFromChunk;
            int z = this.rand.nextInt(16) + zFromChunk;

            // Generate
            this.generateVein(x, y, z);
        }
    }
    
    /** Generates a single vein centred at the given position. */
    protected void generateVein(int x, int y, int z) {

        int veinSize = this.getVeinSize();

        float f = this.rand.nextFloat() * (float) Math.PI;
        double d0 = x + 8 + MathHelper
                .sin(f) * veinSize / 8.0F;
        double d1 = x + 8 - MathHelper
                .sin(f) * veinSize / 8.0F;
        double d2 = z + 8 + MathHelper
                .cos(f) * veinSize / 8.0F;
        double d3 = z + 8 - MathHelper
                .cos(f) * veinSize / 8.0F;
        double d4 = y + this.rand.nextInt(3) - 2;
        double d5 = y + this.rand.nextInt(3) - 2;

        for (int i = 0; i < veinSize; ++i) {
            
            float f1 = (float) i / (float) veinSize;
            double d6 = d0 + (d1 - d0) * f1;
            double d7 = d4 + (d5 - d4) * f1;
            double d8 = d2 + (d3 - d2) * f1;
            double d9 = this.rand.nextDouble() * veinSize / 16.0D;
            double d10 = (MathHelper
                    .sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper
                    .sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor(d6 - d10 / 2.0D);
            int k = MathHelper.floor(d7 - d11 / 2.0D);
            int l = MathHelper.floor(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1) {
                
                double d12 = (l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    
                    for (int i2 = k; i2 <= j1; ++i2) {
                        
                        double d13 = (i2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            
                            for (int j2 = l; j2 <= k1; ++j2) {
                                
                                double d14 = (j2 + 0.5D - d8) /
                                        (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                                    
                                    BlockPos blockpos =
                                            new BlockPos(l1, i2, j2);
                                    IBlockState state =
                                            this.world.getBlockState(blockpos);
                                    
                                    if (state.getBlock().isReplaceableOreGen(
                                            state, this.world,
                                            blockpos, this.predicate)) {

                                        if (this.shouldGenBlock()) {

                                            this.world.setBlockState(blockpos,
                                                    this.state, 2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
