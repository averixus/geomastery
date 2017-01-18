package com.jj.jjmod.blocks;

import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockStairs extends net.minecraft.block.BlockStairs {

    public BlockStairs(String name,
            IBlockState modelState, ToolType toolType) {
        
        super(modelState);
        BlockNew.setupBlock(this, name, CreativeTabs.BUILDING_BLOCKS,
                2F, toolType);
    }
    
    @Override
    public boolean doesSideBlockRendering(IBlockState state,
            IBlockAccess world, BlockPos pos, EnumFacing face) {
        
        if (face == EnumFacing.DOWN || face == EnumFacing.UP) {
            
            return false;
            
        }
        
        return super.doesSideBlockRendering(state, world, pos, face);
    }
}
