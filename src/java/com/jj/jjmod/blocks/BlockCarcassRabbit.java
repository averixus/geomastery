package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Rabbit Carcass block. */
public class BlockCarcassRabbit extends BlockCarcass {

    public BlockCarcassRabbit() {
        
        super("carcass_rabbit", 1F);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos) {
        
        spawnAsEntity(world, pos, new ItemStack(ModItems.rabbitRaw));
        spawnAsEntity(world, pos, new ItemStack(Items.RABBIT_HIDE));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE));
    }
}
