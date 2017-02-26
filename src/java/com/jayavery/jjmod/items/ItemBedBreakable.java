package com.jayavery.jjmod.items;

import com.jayavery.jjmod.blocks.BlockBedAbstract;
import com.jayavery.jjmod.tileentities.TEBed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Bed item for beds with limited durability. */
public class ItemBedBreakable extends ItemBedAbstract {

    public ItemBedBreakable(String name, Block bedBlock, int maxUses) {

        super(name, bedBlock);
        this.setMaxDamage(maxUses);
    }

    @Override
    protected void placeBed(World world, BlockPos foot, BlockPos head,
            EnumFacing facing, int usesLeft) {

        IBlockState stateFoot = this.bedBlock.getDefaultState();
        stateFoot = stateFoot.withProperty(BlockBedAbstract.OCCUPIED, false);
        stateFoot = stateFoot.withProperty(BlockHorizontal.FACING, facing);
        stateFoot = stateFoot.withProperty(BlockBedAbstract.PART,
                BlockBedAbstract.EnumPartBed.FOOT);

        IBlockState stateHead = stateFoot.withProperty(BlockBedAbstract.PART,
                BlockBedAbstract.EnumPartBed.HEAD);

        world.setBlockState(foot, stateFoot);
        world.setBlockState(head, stateHead);

        TEBed tileBed = (TEBed) world.getTileEntity(foot);
        tileBed.setUsesLeft(usesLeft);
    }
}
