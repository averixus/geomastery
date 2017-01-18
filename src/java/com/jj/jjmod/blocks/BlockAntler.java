package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModItems;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAntler extends BlockBush {

    private static final String NAME = "antler";

    public BlockAntler() {

        super();
        BlockNew.setupBlock(this, NAME, null, 0, null);
    }
    
    @Override
    public Item getItemDropped(IBlockState state,
            Random rand, int fortune) {

        return ModItems.shovelAntler;
    }
}
