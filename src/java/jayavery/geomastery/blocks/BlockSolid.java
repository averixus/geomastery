/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Simple solid block. */
public class BlockSolid extends BlockBuilding {
    
    private final Supplier<Item> item;
    private int maxDropped;
    private BlockWeight weight;

    public BlockSolid(Material material, String name, float hardness,
            BlockWeight weight, Supplier<Item> item,
            int maxDropped, ToolType tool) {

        super(material, name, CreativeTabs.BUILDING_BLOCKS, hardness, tool);
        this.item = item;
        this.maxDropped = maxDropped;
        this.weight = weight;
    }

    @Override
    public boolean isValid(World world, BlockPos pos) {

        return true;
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return this.weight;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return state;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        Random rand = world instanceof World ?
                ((World) world).rand : new Random();
        return Lists.newArrayList(new ItemStack(this.item.get(),
                rand.nextInt(this.maxDropped) + 1));
    }
}
