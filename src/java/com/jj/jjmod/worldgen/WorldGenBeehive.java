package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.blocks.BlockBeehive;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenAbstract;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenBeehive extends WorldGenAbstract {
    
    private float chance = 0.001F;

    public WorldGenBeehive(World world, Random rand) {
        
        super(world, rand);
    }

    @Override
    public void generateChunk(int xFromChunk, int zFromChunk) {

        if (this.rand.nextFloat() <= this.chance) {
            
            int x = 0;
            int z = 0;
            int y = -1;
            int tries = 0;
            
            while (y == -1 && tries < 100) {
                
                tries++;
                x = this.rand.nextInt(16) + xFromChunk;
                z = this.rand.nextInt(16) + zFromChunk;
                y = this.findValidLog(x, z);
            }
            
            if (y != -1) {
                
                BlockPos hive = new BlockPos(x, y, z);
                IBlockState state = ModBlocks.beehive.getDefaultState();
                
                for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                        
                    state = state.withProperty(BlockBeehive.FACING, facing);
                    
                    if (ModBlocks.beehive.canStay(this.world, hive, state)) {

                        this.world.setBlockState(hive, state);
                        return;
                    }
                }
            }
        }
    }

}
