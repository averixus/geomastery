package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Chicken Carcass block. */
public class BlockCarcassChicken extends BlockCarcass {
    
    public BlockCarcassChicken() {
        
        super("carcass_chicken", 1F, 1, () -> ModItems.carcassChicken);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos, int age) {
        
        ItemStack meat = new ItemStack(ModItems.chickenRaw, 2);
        meat.getCapability(ModCapabilities.CAP_DECAY, null).setAge(age);
        
        spawnAsEntity(world, pos, meat);
        spawnAsEntity(world, pos, new ItemStack(Items.BONE));
        spawnAsEntity(world, pos, new ItemStack(Items.FEATHER));
    }

}
