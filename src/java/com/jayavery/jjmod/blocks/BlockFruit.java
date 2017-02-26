package com.jayavery.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import com.jayavery.jjmod.items.ItemJj;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Fruit block. */
public class BlockFruit extends BlockNew {
    
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    /** Supplier for the fruit item. */
    private Supplier<Item> item;
    
    public BlockFruit(String name, Supplier<Item> item) {
        
        super(BlockMaterial.FRUIT, name, CreativeTabs.DECORATIONS,
                0.2F, ToolType.SICKLE);
        this.item = item;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess blockAccess, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        if (!(blockAccess instanceof World)) {
            
            return items;
        }
        
        World world = (World) blockAccess;
        
        items.add(ItemJj.newStack(this.item.get(), 1, world));
        return items;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.getHorizontal(meta));
    }
}
