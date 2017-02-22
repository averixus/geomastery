package com.jj.jjmod.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockLightTorch extends BlockLight {

    public BlockLightTorch(String name, float extinguish) {
        
        super(name, 13, extinguish);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        return facing == EnumFacing.UP ? BlockNew.CENTRE_TEN :
            BlockNew.BLIP[facing.getHorizontalIndex()];
    }

}
