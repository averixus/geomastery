package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockCarcassCowpart extends BlockCarcass {

    public BlockCarcassCowpart() {
        
        super("carcass_cowpart", new ItemStack[]
                {new ItemStack(ModItems.beefRaw, 5),
                new ItemStack(ModItems.skinCow, 6),
                new ItemStack(Items.BONE, 5),
                new ItemStack(ModItems.tallow)}, 2F);
    }
}
