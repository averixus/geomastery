package com.jj.jjmod.blocks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockCarcassRabbit extends BlockCarcass {

    public BlockCarcassRabbit() {
        
        super("carcass_rabbit", new ItemStack[] {new ItemStack(Items.RABBIT),
                new ItemStack(Items.RABBIT_HIDE),
                new ItemStack(Items.BONE)}, 1F);
    }
}
