package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Cotton bedroll block. */
public class BlockBedCotton extends BlockBedBreakableAbstract {

    public BlockBedCotton() {
        
        super("bed_cotton", 2.0F, 0.66F, () -> ModItems.bedCotton, null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return BlockNew.TWO;
    }
}
