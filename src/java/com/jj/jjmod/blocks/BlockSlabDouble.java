package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

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
}
