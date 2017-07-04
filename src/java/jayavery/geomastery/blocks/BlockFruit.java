/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import jayavery.geomastery.items.ItemSimple;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.ToolType;
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
    private final Supplier<Item> fruit;
    /** Supplier for the seed item. */
    private final Supplier<Item> seed;
    
    public BlockFruit(String name, Supplier<Item> item, Supplier<Item> seed) {
        
        super(BlockMaterial.FRUIT, name, CreativeTabs.DECORATIONS,
                0.2F, ToolType.SICKLE);
        this.fruit = item;
        this.seed = seed;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess blockAccess, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        if (!(blockAccess instanceof World)) {
            
            return items;
        }
        
        World world = (World) blockAccess;
        
        items.add(ItemSimple.newStack(this.fruit.get(), 1, world));
        items.add(ItemSimple.newStack(this.seed.get(), 1, world));
        return items;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING);
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
