package com.jj.jjmod.blocks;

import com.jj.jjmod.items.ItemNew;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockOldLog;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTest extends BlockOldLog {
    
    public BlockTest() {
        
        BlockNew.setupBlock(this, "test", CreativeTabs.BUILDING_BLOCKS, 1, ToolType.PICKAXE);
    }
}
