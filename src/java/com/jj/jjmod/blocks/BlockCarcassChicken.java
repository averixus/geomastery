package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Chicken Carcass block. */
public class BlockCarcassChicken extends BlockCarcass {
    
    public BlockCarcassChicken() {
        
        super("carcass_chicken", 1F);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos) {
        
        spawnAsEntity(world, pos, new ItemStack(ModItems.chickenRaw, 2));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE));
        spawnAsEntity(world, pos, new ItemStack(Items.FEATHER));
    }

}
