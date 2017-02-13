package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;

/** Fruit block. */
public class BlockFruit extends BlockNew {
    
    public static final PropertyDirection STEM =
            PropertyDirection.create("stem", EnumFacing.Plane.HORIZONTAL);
    
    /** Supplier for the fruit item. */
    private Supplier<Item> item;
    
    public BlockFruit(String name, Supplier<Item> item) {
        
        super(BlockMaterial.FRUIT, name, CreativeTabs.DECORATIONS,
                0.2F, ToolType.SICKLE);
        this.item = item;
    }
    
    @Override
    public Item getItemDropped(IBlockState state,
            Random rand, int fortune) {
        
        return this.item.get();
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {STEM});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(STEM).getHorizontalIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(STEM,
                EnumFacing.getHorizontal(meta));
    }
}
