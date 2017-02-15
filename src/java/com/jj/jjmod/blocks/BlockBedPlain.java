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

/** Bed block with no durablity limit. */
public class BlockBedPlain extends BlockBedAbstract {

    public BlockBedPlain(String name, float hardness, float healAmount,
            Supplier<Item> itemRef, boolean isFlat, ToolType harvestTool) {

        super(name, hardness, healAmount, itemRef, isFlat, harvestTool);
    }
    
    @Override
    protected void drop(World world, BlockPos pos, TEBed bed) {
        
        BlockNew.spawnItem(world, pos, this.itemRef.get());
    }
}
