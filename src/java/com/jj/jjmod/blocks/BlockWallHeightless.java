package com.jj.jjmod.blocks;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockWall.EnumPosition;
import com.jj.jjmod.blocks.BlockWall.EnumStraight;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Wall block with no height variation (implementations: rough, mud). */
public class BlockWallHeightless extends BlockWall {

    public BlockWallHeightless(BlockMaterial material, String name,
            float hardness, ToolType toolType, boolean isDouble,
            Supplier<Item> wall, boolean isHeavy,
            int selfHeight, boolean supportsBeam) {
        
        super(material, name, hardness, toolType, isDouble,
                wall, isHeavy, selfHeight, supportsBeam);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        boolean north = this.isValidHorizontal(world, pos, EnumFacing.NORTH);
        boolean east = this.isValidHorizontal(world, pos, EnumFacing.EAST);
        boolean south = this.isValidHorizontal(world, pos, EnumFacing.SOUTH);
        boolean west = this.isValidHorizontal(world, pos, EnumFacing.WEST);
        
        state = state.withProperty(NORTH, north);
        state = state.withProperty(EAST, east);
        state = state.withProperty(SOUTH, south);
        state = state.withProperty(WEST, west);
        state = state.withProperty(POSITION, EnumPosition.LONE);
        state = state.withProperty(STRAIGHT,
                EnumStraight.get(north, east, south, west));      
        
        return state;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
                
        if (this.isDouble) {
            
            return FULL_BLOCK_AABB;

        } else {

            switch (this.getActualState(state, world, pos).getValue(STRAIGHT)) {
                
                case NS:
                    return CENTRE_HALF[1];
                    
                case EW:
                    return CENTRE_HALF[0];
                    
                default:
                    return CENTRE_POST;
            }
        }
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, worldIn, pos);
        
        if (this.isDouble) {
            
            addCollisionBoxToList(pos, entityBox, list, FULL_BLOCK_AABB);
            return;
        }
            
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST);
        
        if (state.getValue(NORTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH);
        }
        
        if (state.getValue(EAST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST);
        }
        
        if (state.getValue(SOUTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH);
        }
        
        if (state.getValue(WEST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST);
        }
    }
}
