package com.jayavery.jjmod.blocks;

import java.util.List;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.ToolType;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Wall block with no height or thickness variation (implementation: pole). */
public class BlockWallThin extends BlockWall {
    
    public BlockWallThin(BlockMaterial material, String name,
            float hardness, ToolType toolType, boolean isHeavy,
            int selfHeight, boolean supportsBeam) {
                
        super(material, name, hardness, toolType, false, null,
                isHeavy, selfHeight, supportsBeam);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
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
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_THIN);
        
        if (state.getValue(NORTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_THIN);
        }
        
        if (state.getValue(EAST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_THIN);
        }
        
        if (state.getValue(SOUTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_THIN);
        }
        
        if (state.getValue(WEST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_THIN);
        }
    }
}
