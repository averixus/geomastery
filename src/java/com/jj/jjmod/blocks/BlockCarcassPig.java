package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Pig carcass block. */
public class BlockCarcassPig extends BlockCarcassAbstract {

    public BlockCarcassPig() {
        
        super("carcass_pig", 1F, 2, () -> ModItems.carcassPig);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos, long age) {
        
        ItemStack meat = new ItemStack(ModItems.porkRaw, 4);
        meat.getCapability(ModCaps.CAP_DECAY, null).setBirthTime(age);
        
        spawnAsEntity(world, pos, meat);
        spawnAsEntity(world, pos, new ItemStack(ModItems.skinPig, 5));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE, 4));
        spawnAsEntity(world, pos, new ItemStack(ModItems.tallow));
    }
}
