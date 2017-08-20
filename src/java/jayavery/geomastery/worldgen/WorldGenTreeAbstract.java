/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Collection;
import java.util.Random;
import jayavery.geomastery.blocks.BlockSeedling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** Abstract superclass for tree WorldGenerators. */
public abstract class WorldGenTreeAbstract extends WorldGenAbstract {
    
    /** Chance to generate per chunk. */
    private static final float CHANCE = 0.002F;
    
    /** Whether to send block updates for this generation. */
    protected boolean notify;
    /** Average spacing between trees. */
    protected int spread;
    /** Maximum number of trees generated per cluster. */
    protected int maxCluster;
    /** Type of seedling to generate. */
    protected BlockSeedling seedlingType;
    
    public WorldGenTreeAbstract(World world, Random rand, boolean isSapling,
            int spread, int maxCluster, BlockSeedling seedlingType) {
        
        super(world, rand);
        this.notify = isSapling;
        this.spread = spread;
        this.maxCluster = maxCluster;
        this.seedlingType = seedlingType;
    }
    
    /** Generates a single tree. */
    public abstract boolean generateTree(BlockPos pos); 
    
    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {
        
        Biome biome = this.world.getBiome(new
                BlockPos(xFromChunk, 0, zFromChunk));
        
        if (this.rand.nextFloat() <= CHANCE &&
                this.seedlingType.isPermitted(biome)) {
            
            int centreX = this.rand.nextInt(16) + xFromChunk;
            int centreZ = this.rand.nextInt(16) + zFromChunk;
            int x = centreX;
            int z = centreZ;
            int y = this.findValidDirt(x, z);
            
            int cluster = 0;
            int tries = 0;
            
            while (tries < this.maxCluster * 2 && cluster < this.maxCluster) {
                
                if (y != -1) {
                    
                    cluster = this.generateTree(new BlockPos(x, y, z)) ?
                            cluster + 1 : cluster;
                }
                
                tries++;
                
                x = centreX + this.rand.nextInt(this.spread);
                z = centreZ + this.rand.nextInt(this.spread);
                y = this.findValidDirt(x, z);
            }
        }
    }   
    
    /** Sets a blockstate with flags according to the phase of generation. */
    protected void setBlock(BlockPos pos, IBlockState state) {
        
        if (this.notify) {
            
            this.world.setBlockState(pos, state);
            
        } else {
            
            this.world.setBlockState(pos, state, 2);
        }
    }
    
    protected void layerCorners(BlockPos centre, int radius, Collection<BlockPos> leaves, ECornerAmount corners) {
        
        for (int x = -radius; x <= radius; x++) {
            
            for (int z = -radius; z <= radius; z++) {
                
                int sum = Math.abs(x) + Math.abs(z);
                
                if (sum == 2 * radius) {
                    
                    if (corners.placeOuter(this.rand)) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                    
                } else if (sum == (2 * radius) - 1) {
                    
                    if (corners.placeInner(this.rand)) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                    
                } else {
                    
                    leaves.add(centre.add(x, 0, z));
                }
            }
        }
    }
    
    protected void layerOutsides(BlockPos centre, int radius, Collection<BlockPos> leaves, int outsideChance) {
        
        for (int x = -radius; x <= radius; x++) {
            
            for (int z = -radius; z <= radius; z++) {
                
                int sum = Math.abs(x) + Math.abs(z);
                
                if (sum == radius) {
                    
                    if (this.rand.nextInt(outsideChance) == 0) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                    
                } else if (sum < radius) {
                    
                    leaves.add(centre.add(x, 0, z));
                }
            }
        }
    }

    protected void layerPoints(BlockPos centre, int radius, Collection<BlockPos> leaves, int pointChance) {
        
        for (int x = -radius; x <= radius; x++) {
            
            for (int z = -radius; z <= radius; z++) {
                
                int sum = Math.abs(x) + Math.abs(z);
                
                if (sum > radius) {
                    
                } else if (Math.abs(x) == radius || Math.abs(z) == radius) {
                    
                    if (this.rand.nextInt(pointChance) == 0) {
                        
                        leaves.add(centre.add(x, 0, z));
                    }
                    
                } else {
                    
                    leaves.add(centre.add(x, 0, z));
                }
            }
        }
    }
        
    protected enum ECornerAmount {
        
        ALL_PRESENT, OUTER_CHANCE, OUTER_REMOVED, INNER_CHANCE;
        
        public boolean placeOuter(Random rand) {
            
            return this == ALL_PRESENT || (this == OUTER_CHANCE && rand.nextInt(2) == 0);
        }
        
        public boolean placeInner(Random rand) {
            
            switch (this) {
                case ALL_PRESENT: case OUTER_CHANCE: default:
                    return true;
                case OUTER_REMOVED:
                    return true;
                case INNER_CHANCE:
                    return rand.nextInt(2) == 0;
            }
        }
    }
}
