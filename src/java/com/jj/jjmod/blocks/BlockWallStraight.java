package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Wall block which is always straight or crossed. */
public class BlockWallStraight extends BlockWall {
    
    public BlockWallStraight(BlockMaterial material, String name,
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
        
        state = state.withProperty(STRAIGHT,
                EnumStraight.getStraight(north, east, south, west));
        
        return state;
    }
}
