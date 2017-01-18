package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.worldgen.abstracts.WorldGenCrop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenMelon extends WorldGenCrop {

    public WorldGenMelon(World world, Random rand) {
        
        super(world, rand, ModBlocks.melon.getDefaultState(), 10, 10);
    }

    @Override
    public boolean generateOne(BlockPos crop) {
        
        BlockPos fruit = crop.offset(EnumFacing.Plane.HORIZONTAL.random(this.rand));
        BlockPos ground = fruit.down();
        
        if (!this.world.isAirBlock(fruit) || this.world.getBlockState(ground).getBlock() != Blocks.GRASS) {
            
            return false;
        }
        
        this.world.setBlockState(crop, this.crop);
        this.world.setBlockState(fruit, Blocks.MELON_BLOCK.getDefaultState());
        return true;
    }
}
