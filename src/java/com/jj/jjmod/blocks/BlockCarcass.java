package com.jj.jjmod.blocks;

import java.util.ArrayList;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCarcass extends BlockNew {
    
    protected static final AxisAlignedBB HALF_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    public final ItemStack[] drops;

    public BlockCarcass(String name, ItemStack[] drops, float hardness) {

        super(BlockMaterial.CARCASS, name, CreativeTabs.FOOD,
                hardness, ToolType.KNIFE);
        
        this.drops = drops;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return HALF_BOUNDS;
    }
}
