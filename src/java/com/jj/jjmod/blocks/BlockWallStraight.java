package com.jj.jjmod.blocks;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Wall block which is always straight or crossed (implementation: log). */
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
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        EnumStraight straight = this.getActualState(state, world, pos)
                .getValue(STRAIGHT);
        
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST);
        
        if (straight != EnumStraight.EW) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH);
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH);
        }
        
        if (straight != EnumStraight.NS) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST);
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST);
        }
    }
}
