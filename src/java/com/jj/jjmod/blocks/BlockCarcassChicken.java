package com.jj.jjmod.blocks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockCarcassChicken extends BlockCarcass {
    
    public BlockCarcassChicken() {
        
        super("carcass_chicken", new ItemStack[]
                {new ItemStack(Items.CHICKEN, 2),
                new ItemStack(Items.BONE), new ItemStack(Items.FEATHER)}, 1F);
    }

}
