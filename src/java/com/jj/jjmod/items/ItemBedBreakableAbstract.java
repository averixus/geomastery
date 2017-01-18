package com.jj.jjmod.items;

import com.jj.jjmod.blocks.BlockBedAbstract;
import com.jj.jjmod.tileentities.TEBed;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBedBreakableAbstract extends ItemBedAbstract {

    public ItemBedBreakableAbstract(String name,
            Block bedBlock, int maxUses) {

        super(name, bedBlock);
        this.setMaxDamage(maxUses);
    }

    @Override
    public void placeBed(World world, BlockPos foot, BlockPos head,
            EnumFacing facing, int damage) {

        IBlockState stateFoot = this.bedBlock.getDefaultState();
        stateFoot = stateFoot.withProperty(BlockBedAbstract.OCCUPIED, false);
        stateFoot = stateFoot.withProperty(BlockBedAbstract.FACING, facing);
        stateFoot = stateFoot.withProperty(BlockBedAbstract.PART,
                BlockBedAbstract.EnumPartBed.FOOT);

        IBlockState stateHead = stateFoot.withProperty(BlockBedAbstract.PART,
                BlockBedAbstract.EnumPartBed.HEAD);

        world.setBlockState(foot, stateFoot);
        world.setBlockState(head, stateHead);

        TEBed tileBed = (TEBed) world.getTileEntity(foot);
        tileBed.setDropDamage(damage);
    }
}
