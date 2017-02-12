package com.jj.jjmod.blocks;

import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Fence block. */
public class BlockFence extends net.minecraft.block.BlockFence
        implements IBuildingBlock {
    
    public BlockFence() {
        
        super(BlockMaterial.WOOD_FURNITURE, MapColor.WOOD);
        BlockNew.setupBlock(this, "fence",CreativeTabs.BUILDING_BLOCKS,
                2, ToolType.AXE);
    }

    @Override
    public boolean isLight() {

        return true;
    }

    @Override
    public boolean isHeavy() {

        return false;
    }

    @Override
    public boolean isDouble() {

        return false;
    }
    
    @Override
    public boolean supportsBeam() {
        
        return false;
    }
}
