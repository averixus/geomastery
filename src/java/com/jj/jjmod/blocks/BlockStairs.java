package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Stairs block. */
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
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        Block block = world.getBlockState(pos.down()).getBlock();
        
        boolean natural = false;
        boolean built = false;
        
        natural = ModBlocks.LIGHT.contains(block) ||
                ModBlocks.HEAVY.contains(block);
        
        if (block instanceof IBuildingBlock) {
            
            IBuildingBlock builtBlock = (IBuildingBlock) block;
            built = builtBlock.isLight() || builtBlock.isHeavy();
        }
        
        return natural || built;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state.withProperty(HALF, EnumHalf.BOTTOM);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.setBlockToAir(pos);
        }
    }
}
