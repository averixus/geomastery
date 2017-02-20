package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for bed blocks with no durablity limit. */
public abstract class BlockBedPlainAbstract extends BlockBedAbstract {

    public BlockBedPlainAbstract(String name, float hardness, float healAmount,
            Supplier<Item> itemRef, ToolType harvestTool) {

        super(name, hardness, healAmount, itemRef, harvestTool);
    }
    
    @Override
    protected void drop(World world, BlockPos pos, TEBed bed) {
        
        spawnAsEntity(world, pos, new ItemStack(this.itemRef.get()));
    }
}
