package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.block.Block ;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for growable harvestable Crop blocks. */
public abstract class BlockCropHarvestable extends BlockCrop {
    
    /** Maximum permitted height for this Crop. */
    protected int maxHeight;

    public BlockCropHarvestable(String name, int maxHeight,
            Supplier<Item> cropRef, Function<Random, Integer> yieldRef,
            float growthRate, float hardness) {
        
        super(name, cropRef, yieldRef, growthRate, hardness, null);
        this.maxHeight = maxHeight;
    }
    
    @Override
    protected void grow(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        if (state.getValue(AGE) == 7) {
            
            this.growUp(world, pos);
        }
        
        super.grow(world, pos, state, rand);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
            return false;
        }
        
        int age = state.getValue(AGE);
        
        if (age != 7) {
            
            return false;
            
        } else {
            
            IBlockState newState = state.withProperty(AGE, 0);
            world.setBlockState(pos, newState);
            
            if (!world.isRemote) {
                
                spawnItem(world, pos, this.cropRef.get());
            }
            
            return true;
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
    
    @Override
    protected boolean canStay(World world, BlockPos pos, IBlockState state) {
        
        BlockPos downPos = pos.down();
        Block downBlock = world.getBlockState(downPos).getBlock();
        return super.canStay(world, pos, state) || downBlock == this;
    }
    
    /** Attempts to grow another block of this Crop above. */
    protected void growUp(World world, BlockPos pos) {
        
        if (!world.isAirBlock(pos.up())) {
            
            return;
        }
        
        int height;
        
        for (height = 1; world.getBlockState(pos.down(height))
                .getBlock() == this; height++) {}
        
        if (height < this.maxHeight) {
            
            world.setBlockState(pos.up(), this.getDefaultState());
        }
    }
}
