package com.jayavery.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Double form of slab block. */
public class BlockSlabDouble extends BlockSlabSingle implements IBuildingBlock {
        
    public BlockSlabDouble(Material material, String name, float hardness,
            ToolType harvestTool, Supplier<Item> item) {
        
        super(material, name, hardness, harvestTool, item);
    }
    
    @Override
    public int quantityDropped(Random rand) {
        
        return 2;
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
        
        return true;
    }
    
    @Override
    public boolean supportsBeam() {
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
}
