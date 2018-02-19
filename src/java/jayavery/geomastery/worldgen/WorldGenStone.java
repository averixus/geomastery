/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import com.google.common.base.Predicate;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** WorldGenerators replacing stone blocks. */
public abstract class WorldGenStone extends WorldGenAbstract {

    /** Predicate for blocks this generator replaces. */
    protected Predicate<IBlockState> predicate = BlockMatcher.forBlock(Blocks.STONE);
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
    
    /** @return Whether a single block in a vein should be generated or not.
     * Default implementation is always true. */
    protected boolean shouldGenBlock() {
        
        return true;
    }

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
    
    /** Generates a single vein centred at the given position.
     * Mostly copied from WorldGenMinable. */
    protected void generateVein(int centreX, int centreY, int centreZ) {

        int size = this.getVeinSize();

        float angle = this.rand.nextFloat() * (float) Math.PI;
        double posX = centreX + 8 + MathHelper.sin(angle) * size / 8.0F;
        double negX = centreX + 8 - MathHelper.sin(angle) * size / 8.0F;
        double posZ = centreZ + 8 + MathHelper.cos(angle) * size / 8.0F;
        double negZ = centreZ + 8 - MathHelper.cos(angle) * size / 8.0F;
        double oneY = centreY + this.rand.nextInt(3) - 2;
        double twoY = centreY + this.rand.nextInt(3) - 2;

        for (int count = 0; count < size; ++count) {
            
            float frVein = (float) count / (float) size;
            double frX = posX + (negX - posX) * frVein;
            double frY = oneY + (twoY - oneY) * frVein;
            double frZ = posZ + (negZ - posZ) * frVein;
            double frRand = this.rand.nextDouble() * size / 16.0D;
            double frHor = (MathHelper.sin((float) Math.PI *
                    frVein) + 1.0F) * frRand + 1.0D;
            double frVert = (MathHelper.sin((float) Math.PI *
                    frVein) + 1.0F) * frRand + 1.0D;
            int minX = MathHelper.floor(frX - frHor / 2.0D);
            int minY = MathHelper.floor(frY - frVert / 2.0D);
            int minZ = MathHelper.floor(frZ - frHor / 2.0D);
            int maxX = MathHelper.floor(frX + frHor / 2.0D);
            int maxY = MathHelper.floor(frY + frVert / 2.0D);
            int maxZ = MathHelper.floor(frZ + frHor / 2.0D);

            for (int x = minX; x <= maxX; ++x) {
                
                double diX = (x + 0.5D - frX) / (frHor / 2.0D);

                if (diX * diX < 1.0D) {
                    
                    for (int y = minY; y <= maxY; ++y) {
                        
                        double diY = (y + 0.5D - frY) / (frVert / 2.0D);

                        if (diX * diX + diY * diY < 1.0D) {
                            
                            for (int z = minZ; z <= maxZ; ++z) {
                                
                                double diZ = (z + 0.5D - frZ) / (frHor / 2.0D);

                                if (diX * diX + diY * diY + diZ * diZ < 1.0D) {
                                    
                                    BlockPos blockpos = new BlockPos(x, y, z);
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
    
    /** Clay. */
    public static class Clay extends WorldGenStone {

        public Clay(World world, Random rand) {
            
            super(world, rand, Blocks.CLAY.getDefaultState(), 40, 80, 3, 1);
            this.predicate = (s) -> s != null && (s.getBlock() == Blocks.STONE
                    || s.getBlock() == Blocks.DIRT
                    || s.getBlock() == Blocks.GRASS);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(20) + 1;
        }
    }
    
    /** Chalk. */
    public static class Chalk extends WorldGenStone {

        public Chalk(World world, Random rand) {
            
            super(world, rand, GeoBlocks.CHALK.getDefaultState(),
                    30, 256, 1, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(12) + 1;
        }
    }
    
    /** Salt. */
    public static class Salt extends WorldGenStone {

        public Salt(World world, Random rand) {
            
            super(world, rand, GeoBlocks.SALT.getDefaultState(),
                    20, 60, 1, 0.5);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(100) + 1;
        }
    }
    
    /** Tin ore. */
    public static class Tin extends WorldGenStone {

        public Tin(World world, Random rand) {

            super(world, rand, GeoBlocks.ORE_TIN.getDefaultState(),
                    30, 120, 80, 1);
        }

        @Override
        protected int getVeinSize() {

            int rand1 = this.rand.nextInt(2);
            int rand2 = this.rand.nextInt(2);

            return rand1 + rand2 + 1;
        }
    }
    
    /** Silver ore. */
    public static class Silver extends WorldGenStone {

        public Silver(World world, Random rand) {

            super(world, rand, GeoBlocks.ORE_SILVER.getDefaultState(),
                    10, 60, 10, 1);
        }

        @Override
        protected int getVeinSize() {

            int rand1 = this.rand.nextInt(2);
            int rand2 = this.rand.nextInt(2);
            int rand3 = this.rand.nextInt(2);

            return rand1 + rand2 + rand3 + 1;
        }
    }
    
    /** Iron ore. */
    public static class Iron extends WorldGenStone {

        public Iron(World world, Random rand) {

            super(world, rand, Blocks.IRON_ORE.getDefaultState(),
                    15, 50, 60, 1);
        }

        @Override
        protected int getVeinSize() {

            int rand1 = this.rand.nextInt(3);
            int rand2 = this.rand.nextInt(3);
            int rand3 = this.rand.nextInt(3);

            return rand1 + rand2 + rand3 + 1;
        }
    }
    
    /** Gold ore. */
    public static class Gold extends WorldGenStone {

        public Gold(World world, Random rand) {

            super(world, rand, Blocks.GOLD_ORE.getDefaultState(), 0, 40, 10, 1);
        }

        @Override
        protected int getVeinSize() {

            int rand1 = this.rand.nextInt(2);
            int rand2 = this.rand.nextInt(2);
            int rand3 = this.rand.nextInt(2);

            return rand1 + rand2 + rand3;
        }
    }
    
    /** Copper ore. */
    public static class Copper extends WorldGenStone {

        public Copper(World world, Random rand) {

            super(world, rand, GeoBlocks.ORE_COPPER.getDefaultState(),
                    40, 120, 20, 1);
        }

        @Override
        protected int getVeinSize() {

            int rand1 = this.rand.nextInt(5);
            int rand2 = this.rand.nextInt(5);

            return rand1 + rand2 + 1;
        }
    }
    
    /** Coal ore. */
    public static class Coal extends WorldGenStone {

        public Coal(World world, Random rand) {

            super(world, rand, Blocks.COAL_ORE.getDefaultState(), 5, 60, 20, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(10) + 1;
        }
    }
    
    /** Fireopal lode. */
    public static class Fireopal extends WorldGenStone {

        public Fireopal(World world, Random rand) {

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
    
    /** Emerald lode. */
    public static class Emerald extends WorldGenStone {

        public Emerald(World world, Random rand) {

            super(world, rand, Blocks.EMERALD_ORE.getDefaultState(),
                    0, 30, 1, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(39) + 1;
        }

        @Override
        protected boolean shouldGenBlock() {

            if (this.rand.nextFloat() < 0.1) {

                return true;
            }

            return false;
        }
    }

    /** Diamond lode. */
    public static class Diamond extends WorldGenStone {

        public Diamond(World world, Random rand) {

            super(world, rand, Blocks.DIAMOND_ORE.getDefaultState(),
                    0, 15, 1, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(79) + 1;
        }

        @Override
        protected boolean shouldGenBlock() {

            if (this.rand.nextFloat() < 0.05) {

                return true;
            }

            return false;
        }
    }
    
    /** Amethyst lode. */
    public static class Amethyst extends WorldGenStone {

        public Amethyst(World world, Random rand) {

            super(world, rand, GeoBlocks.LODE_AMETHYST.getDefaultState(),
                    90, 256, 20, 1);
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
    
    /** Sapphire lode. */
    public static class Sapphire extends WorldGenStone {

        public Sapphire(World world, Random rand) {

            super(world, rand, GeoBlocks.LODE_SAPPHIRE.getDefaultState(),
                    80, 120, 30, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(2) + 1;
        }
    }
    
    /** Ruby lode. */
    public static class Ruby extends WorldGenStone {

        public Ruby(World world, Random rand) {

            super(world, rand, GeoBlocks.LODE_RUBY.getDefaultState(),
                    0, 256, 1, 1);
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
    
    /** Redstone lode. */
    public static class Redstone extends WorldGenStone {

        public Redstone(World world, Random rand) {

            super(world, rand, Blocks.REDSTONE_ORE.getDefaultState(),
                    5, 45, 2, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(39) + 1;
        }

        @Override
        protected boolean shouldGenBlock() {

            if (this.rand.nextFloat() < 0.2) {

                return true;
            }

            return false;
        }
    }
    
    /** Lapis lode. */
    public static class Lapis extends WorldGenStone {

        public Lapis(World world, Random rand) {

            super(world, rand, Blocks.LAPIS_ORE.getDefaultState(),
                    60, 70, 1, 1);
        }

        @Override
        protected int getVeinSize() {

            return this.rand.nextInt(19) + 1;
        }

        @Override
        protected boolean shouldGenBlock() {

            if (this.rand.nextFloat() < 0.3) {

                return true;
            }

            return false;
        }
    }
}
