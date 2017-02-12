package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Cow part Carcass block. */
public class BlockCarcassCowpart extends BlockCarcass {

    public BlockCarcassCowpart() {
        
        super("carcass_cowpart", 2F);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos) {
        
        spawnAsEntity(world, pos, new ItemStack(ModItems.beefRaw, 5));
        spawnAsEntity(world, pos, new ItemStack(ModItems.skinCow, 6));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE, 5));
        spawnAsEntity(world, pos, new ItemStack(ModItems.tallow));
    }
}
