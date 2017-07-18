/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockCrop;
import jayavery.geomastery.blocks.BlockFruit;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.IBiomeCheck;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** WorldGenerators for crop blocks. */
public abstract class WorldGenCrop extends WorldGenAbstract {

    /** Chance of generating per chunk. */
    private static final float CHANCE = 0.002F;
    
    /** State of the crop block to generate. */
    protected IBlockState crop;
    /** Average spacing between generated blocks. */
    protected int spread;
    /** Maximum number of blocks generated per cluster. */
    protected int maxCluster;
    
    public WorldGenCrop(World world, Random rand,
            IBlockState crop, int spread, int maxCluster) {
        
        super(world, rand);
        this.crop = crop;
        this.spread = spread;
        this.maxCluster = maxCluster;
    }

    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        Biome biome = this.world.getBiome(new
                BlockPos(xFromChunk, 0, zFromChunk));

        if (this.rand.nextFloat() <= CHANCE &&
                ((IBiomeCheck) this.crop.getBlock()).isPermitted(biome)) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findValidGrass(x, z);
            
            int cluster = 0;
            int tries = 0;
            
            while (tries < this.maxCluster * 2 && cluster < this.maxCluster) {
                
                if (y != -1) {
                    
                    cluster = this.generateOne(new BlockPos(x, y, z)) ?
                            cluster + 1 : cluster;
                }
                
                tries++;
                
                x = centreX + this.rand.nextInt(this.spread);
                z = centreZ + this.rand.nextInt(this.spread);
                y = this.findValidGrass(x, z);
            }
        }
    }
    
    /** Attempts to generate a single block at the given position.
     * @return Whether it successfully generated. */
    protected boolean generateOne(BlockPos pos) {
        
        if (((BlockCrop) this.crop.getBlock())
                .canStay(this.world, pos)) {
        
            this.world.setBlockState(pos, this.crop);
            return true;
        
        } else {
            
            return false;
        }
    }
    
    /** Pumpkin. */
    public static class Pumpkin extends WorldGenCrop {

        public Pumpkin(World world, Random rand) {
            
            super(world, rand, GeoBlocks.PUMPKIN_CROP.getFullgrown(), 4, 3);
        }

        @Override
        protected boolean generateOne(BlockPos crop) {
            
            EnumFacing fruitOffset = EnumFacing.Plane.HORIZONTAL.random(this.rand);
            BlockPos fruit = crop.offset(fruitOffset);
            BlockPos ground = fruit.down();
            
            if (!this.world.isAirBlock(fruit) ||
                    this.world.getBlockState(ground).getBlock() != Blocks.GRASS) {
                
                return false;
            }
            
            this.world.setBlockState(crop, this.crop);
            this.world.setBlockState(fruit, GeoBlocks.PUMPKIN_FRUIT.getDefaultState()
                    .withProperty(BlockFruit.FACING, fruitOffset.getOpposite()));
            return true;
        }
    }
    
    /** Melon. */
    public static class Melon extends WorldGenCrop {

        public Melon(World world, Random rand) {
            
            super(world, rand, GeoBlocks.MELON_CROP.getFullgrown(), 6, 4);
        }

        @Override
        protected boolean generateOne(BlockPos crop) {
            
            EnumFacing fruitOffset = EnumFacing.Plane.HORIZONTAL.random(this.rand);
            BlockPos fruit = crop.offset(fruitOffset);
            BlockPos ground = fruit.down();
            
            if (!this.world.isAirBlock(fruit) ||
                    this.world.getBlockState(ground).getBlock() != Blocks.GRASS) {
                
                return false;
            }
            
            this.world.setBlockState(crop, this.crop);
            this.world.setBlockState(fruit, GeoBlocks.MELON_FRUIT.getDefaultState()
                    .withProperty(BlockFruit.FACING, fruitOffset.getOpposite()));
            return true;
        }
    }
    
    /** Wheat. */
    public static class Wheat extends WorldGenCrop {

        public Wheat(World world, Random rand) {
            
            super(world, rand, GeoBlocks.WHEAT.getFullgrown(), 10, 7);
        }
    }
    
    /** Tomato. */
    public static class Tomato extends WorldGenCrop {

        public Tomato(World world, Random rand) {
            
            super(world, rand, GeoBlocks.TOMATO.getFullgrown(), 20, 7);
        }
    }
    
    /** Potato. */
    public static class Potato extends WorldGenCrop {

        public Potato(World world, Random rand) {
            
            super(world, rand, GeoBlocks.POTATO.getFullgrown(), 20, 5);
        }
    }
    
    /** Pepper. */
    public static class Pepper extends WorldGenCrop {

        public Pepper(World world, Random rand) {
            
            super(world, rand, GeoBlocks.PEPPER.getFullgrown(), 30, 3);
        }
    }
    
    /** Cotton. */
    public static class Cotton extends WorldGenCrop {

        public Cotton(World world, Random rand) {
            
            super(world, rand, GeoBlocks.COTTON.getFullgrown(), 30, 5);
        }
    }
    
    /** Chickpea. */
    public static class Chickpea extends WorldGenCrop {

        public Chickpea(World world, Random rand) {
            
            super(world, rand, GeoBlocks.CHICKPEA.getFullgrown(), 20, 4);
        }
    }
    
    /** Carrot. */
    public static class Carrot extends WorldGenCrop {

        public Carrot(World world, Random rand) {
            
            super(world, rand, GeoBlocks.CARROT.getFullgrown(), 20, 4);
        }
    }
    
    /** Berry. */
    public static class Berry extends WorldGenCrop {

        public Berry(World world, Random rand) {
            
            super(world, rand, GeoBlocks.BERRY.getFullgrown(), 10, 8);
        }
    }
    
    /** Beetroot. */
    public static class Beetroot extends WorldGenCrop {

        public Beetroot(World world, Random rand) {
            
            super(world, rand, GeoBlocks.BEETROOT.getFullgrown(), 20, 3);
        }
    }
    
    /** Bean. */
    public static class Bean extends WorldGenCrop {

        public Bean(World world, Random rand) {
            
            super(world, rand, GeoBlocks.BEAN.getFullgrown(), 30, 5);
        }
    }
    
    /** Hemp. */
    public static class Hemp extends WorldGenCrop {

        public Hemp(World world, Random rand) {
            
            super(world, rand, GeoBlocks.HEMP.getFullgrown(), 30, 3);
        }
    }
}
