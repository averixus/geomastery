package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockCarcassSheep extends BlockCarcass {
    
    public BlockCarcassSheep() {
        
        super("carcass_sheep", new ItemStack[]
                {new ItemStack(ModItems.muttonRaw, 3),
                new ItemStack(ModItems.skinSheep, 4),
                new ItemStack(Items.BONE, 3),
                new ItemStack(ModItems.tallow)}, 1F);
    }
}
