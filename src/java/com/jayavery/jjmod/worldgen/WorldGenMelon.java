package com.jayavery.jjmod.worldgen;

import java.util.Random;
import com.jayavery.jjmod.blocks.BlockFruit;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for Melon crops. */
public class WorldGenMelon extends WorldGenCrop {

    public WorldGenMelon(World world, Random rand) {
        
        super(world, rand, ModBlocks.melonCrop.getFullgrown(), 6, 4);
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
        this.world.setBlockState(fruit, ModBlocks.melonFruit.getDefaultState()
                .withProperty(BlockFruit.FACING, fruitOffset.getOpposite()));
        return true;
    }
}
