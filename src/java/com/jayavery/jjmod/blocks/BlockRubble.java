package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/** Rubble block. */
public class BlockRubble extends BlockFalling {

    public BlockRubble() {
        
        super(BlockMaterial.SOIL);
        BlockNew.setupBlock(this, "rubble", CreativeTabs.BUILDING_BLOCKS,
                0.5F, ToolType.SHOVEL);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return Items.AIR;
    }
}
