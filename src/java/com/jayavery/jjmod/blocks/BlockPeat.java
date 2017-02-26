package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/** Peat block. */
public class BlockPeat extends BlockNew {

    public BlockPeat() {
        
        super(BlockMaterial.SOIL, "peat", 0.5F, ToolType.SHOVEL);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return ModItems.peatWet;
    }
}
