package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Bed block with no durablity limit. */
public class BlockBedPlain extends BlockBedAbstract {

    public BlockBedPlain(String name, float hardness,
            Supplier<Item> itemRef, boolean isFlat, ToolType harvestTool) {

        super(name, hardness, itemRef, isFlat, harvestTool);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> drops = new ArrayList<ItemStack>();
        
        if (state.getValue(PART) == EnumPartBed.FOOT) {
            
            drops.add(new ItemStack(this.itemRef.get()));
        }
        
        return drops;
    }
}
