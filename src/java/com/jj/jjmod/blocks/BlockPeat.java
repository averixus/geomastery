package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockPeat extends BlockNew {

    public BlockPeat() {
        
        super(BlockMaterial.SOIL, "peat", 0.5F, ToolType.SHOVEL);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return ModItems.peatWet;
    }
}
