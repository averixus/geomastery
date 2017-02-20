package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBedLeaf extends BlockBedPlainAbstract {

    public BlockBedLeaf() {
        
        super("bed_leaf", 0.2F, 0.33F, () -> null, null);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return BlockNew.FOUR;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }

}
