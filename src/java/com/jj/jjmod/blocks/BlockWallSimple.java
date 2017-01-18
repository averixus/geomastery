package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.blocks.BlockWall.EnumStraight;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockWallSimple extends BlockWall {

    public BlockWallSimple(BlockMaterial material, String name, float hardness,
            ToolType toolType, boolean isDouble, Supplier<Item> wall,
            boolean isHeavy, int selfHeight) {
        
        super(material, name, hardness, toolType, isDouble, wall, isHeavy, selfHeight);
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
        state = state.withProperty(STRAIGHT, EnumStraight.get(north, east, south, west));      
        
        return state;
    }

}
