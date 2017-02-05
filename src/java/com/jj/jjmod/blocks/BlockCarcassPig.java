package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockCarcassPig extends BlockCarcass {

    public BlockCarcassPig() {
        
        super("carcass_pig", new ItemStack[]
                {new ItemStack(ModItems.porkRaw, 4),
                new ItemStack(ModItems.skinPig, 6),
                new ItemStack(Items.BONE, 4),
                new ItemStack(ModItems.tallow)}, 1F);
    }
}
