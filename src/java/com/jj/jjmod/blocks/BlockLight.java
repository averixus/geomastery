package com.jj.jjmod.blocks;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for torch and candle blocks. */
public abstract class BlockLight extends BlockTorch {

    protected static final AxisAlignedBB FLAT_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.18D, 1.0D);
    
    /** Chance of extinguishing per update tick. */
    private float extinguish;

    public BlockLight(String name, int light, float extinguish) {

        super();
        this.lightValue = light;
        this.extinguish = extinguish;
        BlockNew.setupBlock(this, name, CreativeTabs.DECORATIONS, 0, null);
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        if (rand.nextFloat() <= this.extinguish) {
            
            world.playSound(pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.BLOCKS, 1, 1, false);
            world.setBlockToAir(pos);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world,
            BlockPos pos, Random rand) {

        // No particles
    }
}
