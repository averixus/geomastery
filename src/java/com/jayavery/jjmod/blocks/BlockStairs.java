package com.jayavery.jjmod.blocks;

import java.util.List;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import com.sun.istack.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
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
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        EnumShape shape = state.getValue(SHAPE);

        int facing = state.getValue(FACING).getHorizontalIndex() + 1;
        
        if (shape == EnumShape.INNER_LEFT || shape == EnumShape.OUTER_LEFT) {
            
            facing++;
        } 
        
        facing %= 4;
        
        if (shape == EnumShape.INNER_LEFT || shape == EnumShape.INNER_RIGHT) {
            
            for (AxisAlignedBB box : BlockNew.STAIRS_INTERNAL[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
            
        } else if (shape == EnumShape.OUTER_LEFT ||
                shape == EnumShape.OUTER_RIGHT) {
            
            for (AxisAlignedBB box : BlockNew.STAIRS_EXTERNAL[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
            
        } else {
            
            for (AxisAlignedBB box : BlockNew.STAIRS_STRAIGHT[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
        }
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
    
    /** Stairs only exist on bottom half. */
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return super.getActualState(state, world, pos)
                .withProperty(HALF, EnumHalf.BOTTOM);
    }
    
    /** Check position and break if invalid. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.setBlockToAir(pos);
        }
    }
}
