package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Sheep Carcass block. */
public class BlockCarcassSheep extends BlockCarcass {
    
    public BlockCarcassSheep() {
        
        super("carcass_sheep", 1F, 2, () -> ModItems.carcassSheep);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos, long age) {
        
        ItemStack meat = new ItemStack(ModItems.muttonRaw, 3);
        meat.getCapability(ModCapabilities.CAP_DECAY, null).setBirthTime(age);
        
        spawnAsEntity(world, pos, meat);
        spawnAsEntity(world, pos, new ItemStack(ModItems.skinSheep, 4));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE, 3));
        spawnAsEntity(world, pos, new ItemStack(ModItems.tallow));
    }
}
