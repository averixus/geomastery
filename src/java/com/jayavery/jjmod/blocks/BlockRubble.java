package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/** Rubble block. */
public class BlockRubble extends BlockFalling implements IBuildingBlock {

    public BlockRubble() {
        
        super(BlockMaterial.SOIL);
        BlockNew.setupBlock(this, "rubble", CreativeTabs.BUILDING_BLOCKS,
                0.5F, ToolType.SHOVEL);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return ModItems.rubble;
    }

    @Override
    public boolean isLight() {

        return true;
    }

    @Override
    public boolean isHeavy() {

        return true;
    }

    @Override
    public boolean isDouble() {

        return true;
    }

    @Override
    public boolean supportsBeam() {

        return false;
    }

    @Override
    public boolean isShelter() {

        return true;
    }
}
