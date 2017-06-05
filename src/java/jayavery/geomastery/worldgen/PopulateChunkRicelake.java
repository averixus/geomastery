package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** Class to populate a chunk with a rice lake. */
public class PopulateChunkRicelake extends WorldGenAbstract {
        
    /** Water block state. */
    private static final IBlockState water = Blocks.FLOWING_WATER.getDefaultState();
    /** Rice base block state. */
    private static final IBlockState base = GeoBlocks.riceBase.getDefaultState();
    /** Rice top block state. */
    private static final IBlockState top = GeoBlocks.riceTop.getDefaultState();

    public PopulateChunkRicelake(World world, Random rand) {
        
        super(world, rand);
    }

    @Override
    public void generateChunk(int xChunk, int zChunk) {
        
        // Rice lake setup
        BlockPos start = new BlockPos((xChunk * 16) + this.rand.nextInt(16),
                256, (zChunk * 16) + this.rand.nextInt(16));        
        BlockPos position;
        
        for (position = start; position.getY() > 5 &&
                this.world.isAirBlock(position); position = position.down()) {}

        // Vanilla generation
        if (position.getY() <= 4) {
            
            return;
            
        } else {
            
            position = position.down(4);
            boolean[] aboolean = new boolean[2048];
            int i = this.rand.nextInt(4) + 4;

            for (int j = 0; j < i; ++j) {
                
                double d0 = this.rand.nextDouble() * 6.0D + 3.0D;
                double d1 = this.rand.nextDouble() * 4.0D + 2.0D;
                double d2 = this.rand.nextDouble() * 6.0D + 3.0D;
                double d3 = this.rand.nextDouble() *
                        (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = this.rand.nextDouble() *
                        (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = this.rand.nextDouble() *
                        (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int l = 1; l < 15; ++l) {
                    
                    for (int i1 = 1; i1 < 15; ++i1) {
                        
                        for (int j1 = 1; j1 < 7; ++j1) {
                            
                            double d6 = (l - d3) / (d0 / 2.0D);
                            double d7 = (j1 - d4) / (d1 / 2.0D);
                            double d8 = (i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 1.0D) {
                                
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            for (int k1 = 0; k1 < 16; ++k1) {
                
                for (int l2 = 0; l2 < 16; ++l2) {
                    
                    for (int k = 0; k < 8; ++k) {
                        
                        boolean flag = !aboolean[(k1 * 16 + l2) * 8 + k] &&
                                (k1 < 15 && aboolean[((k1 + 1) * 16 + l2) *
                                8 + k] || k1 > 0 &&
                                aboolean[((k1 - 1) * 16 + l2) * 8 + k] ||
                                l2 < 15 && aboolean[(k1 * 16 + l2 + 1) *
                                8 + k] || l2 > 0 &&
                                aboolean[(k1 * 16 + (l2 - 1)) * 8 + k] ||
                                k < 7 && aboolean[(k1 * 16 + l2) *
                                8 + k + 1] || k > 0 &&
                                aboolean[(k1 * 16 + l2) * 8 + (k - 1)]);

                        if (flag) {
                            
                            Material material = this.world
                                    .getBlockState(position.add(k1, k, l2))
                                    .getMaterial();

                            if (k >= 4 && material.isLiquid()) {
                                
                                return;
                            }

                            if (k < 4 && !material.isSolid() && this.world
                                    .getBlockState(position.add(k1, k, l2))
                                    .getBlock() != water.getBlock()) {
                                
                                return;
                            }
                        }
                    }
                }
            }
            
            // Rice lake generation using vanilla loops
            ArrayList<BlockPos> possibles = new ArrayList<BlockPos>();

            for (int l1 = 0; l1 < 16; ++l1) {
                
                for (int i3 = 0; i3 < 16; ++i3) {
                    
                    for (int i4 = 0; i4 < 8; ++i4) {
                        
                        if (aboolean[(l1 * 16 + i3) * 8 + i4]) {                            
                            
                            BlockPos target = position.add(l1, i4, i3);
                            
                            if (i4 >= 4) {
                                
                                if (this.world.getBlockState(target) !=
                                        top && this.world.getBlockState(target)
                                        != base) {
                                    
                                    this.world.setBlockState(target,
                                            Blocks.AIR.getDefaultState(), 2);
                                }
                                
                            } else {
                                    
                                this.world.setBlockState(target, water, 2);
                                Block below = this.world
                                        .getBlockState(target.down())
                                        .getBlock();
                                
                                if (below == Blocks.DIRT ||
                                        below == Blocks.GRASS) {
                                    
                                    possibles.add(target);
                                }
                            }
                        }
                    }
                }
            }
            
            int riceCount = 0;
            int riceMax = this.rand.nextInt(6) + 1;
            
            while (riceCount <= riceMax && possibles.size() > 0) {
                
                BlockPos rice = possibles.remove(this.rand
                        .nextInt(possibles.size()));
                
                if (this.world.canSeeSky(rice.up()) &&
                        this.world.isAirBlock(rice.up())) {
                    
                    this.world.setBlockState(rice, base, 2);
                    this.world.setBlockState(rice.up(), top, 2);
                    riceCount++;
                }
            }

            // Vanilla generation
            for (int i2 = 0; i2 < 16; ++i2) {
                
                for (int j3 = 0; j3 < 16; ++j3) {
                    
                    for (int j4 = 4; j4 < 8; ++j4) {
                        
                        if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                            
                            BlockPos blockpos = position.add(i2, j4 - 1, j3);

                            if (this.world.getBlockState(blockpos)
                                    .getBlock() == Blocks.DIRT &&
                                    this.world.getLightFor(EnumSkyBlock.SKY,
                                    position.add(i2, j4, j3)) > 0) {
                                
                                Biome biome = this.world.getBiome(blockpos);

                                if (biome.topBlock.getBlock() ==
                                        Blocks.MYCELIUM) {
                                    
                                    this.world.setBlockState(blockpos,
                                            Blocks.MYCELIUM.getDefaultState(),
                                            2);
                                    
                                } else {
                                    
                                    this.world.setBlockState(blockpos,
                                            Blocks.GRASS.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }

            for (int k2 = 0; k2 < 16; ++k2) {
                
                for (int l3 = 0; l3 < 16; ++l3) {
                    
                    if (this.world.canBlockFreezeWater(position
                            .add(k2, 4, l3))) {
                        
                        this.world.setBlockState(position.add(k2, 4, l3),
                                Blocks.ICE.getDefaultState(), 2);
                    }
                }
            }
        }
    }
}