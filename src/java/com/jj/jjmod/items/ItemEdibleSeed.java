package com.jj.jjmod.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;

public class ItemEdibleSeed extends ItemSeedFood {

    public ItemEdibleSeed(String name, int hunger,
            float saturation, int stackSize, Block crop) {

        super(hunger, saturation, crop, Blocks.FARMLAND);
        ItemNew.setupItem(this, name, stackSize, CreativeTabs.FOOD);
    }
}
