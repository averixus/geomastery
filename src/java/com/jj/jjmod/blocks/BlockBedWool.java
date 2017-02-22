package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Wool bedroll block. */
public class BlockBedWool extends BlockBedBreakableAbstract {

    public BlockBedWool() {
        
        super("bed_wool", 2.0F, 0.66F, () -> ModItems.bedWool, null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return BlockNew.SIX;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return BlockNew.FOUR;
    }
}
