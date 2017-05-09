package com.jayavery.jjmod.blocks;

import java.util.List;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDoublingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Paving slab block. */
public class BlockSlab extends BlockBuilding implements IDoublingBlock {
    
    /** Whether this is a double block. */
    private final boolean isDouble;
    /** The item dropped by this block. */
    private final Supplier<Item> item;

    public BlockSlab(String name, boolean isDouble, Supplier<Item> item) {
        
        super(BlockMaterial.STONE_FURNITURE, name, null, 2F, ToolType.PICKAXE);
        this.isDouble = isDouble;
        this.item = item;
    }

    @Override
    public BlockWeight getWeight() {

        return BlockWeight.MEDIUM;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return true;
    }
    
    @Override
    public boolean isDouble() {
        
        return this.isDouble;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return state;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return this.isDouble() ? FULL_BLOCK_AABB : EIGHT;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {

        return Lists.newArrayList(new ItemStack(this.item.get(),
                this.isDouble() ? 2 : 1));
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this);
    }
}
