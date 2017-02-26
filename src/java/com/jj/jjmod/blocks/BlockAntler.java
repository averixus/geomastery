package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModItems;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/** Antler block. */
public class BlockAntler extends BlockBush {

    public BlockAntler() {

        BlockNew.setupBlock(this, "antler", null, 0, null);
    }
    
    @Override
    public Item getItemDropped(IBlockState state,
            Random rand, int fortune) {

        return ModItems.shovelAntler;
    }
}
