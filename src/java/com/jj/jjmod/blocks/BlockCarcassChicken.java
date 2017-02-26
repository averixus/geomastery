package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Chicken carcass block. */
public class BlockCarcassChicken extends BlockCarcassAbstract {
    
    public BlockCarcassChicken() {
        
        super("carcass_chicken", 1F, 1, () -> ModItems.carcassChicken);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos, long age) {
        
        ItemStack meat = new ItemStack(ModItems.chickenRaw, 2);
        meat.getCapability(ModCaps.CAP_DECAY, null).setBirthTime(age);
        
        spawnAsEntity(world, pos, meat);
        spawnAsEntity(world, pos, new ItemStack(Items.BONE));
        spawnAsEntity(world, pos, new ItemStack(Items.FEATHER));
    }
}
