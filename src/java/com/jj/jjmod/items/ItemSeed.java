package com.jj.jjmod.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

/** Seed item. */
public class ItemSeed extends ItemSeeds {

    public ItemSeed(String name, int stackSize, Block crop) {

        super(crop, Blocks.FARMLAND);
        ItemJj.setupItem(this, name, stackSize, CreativeTabs.MATERIALS);
    }
}
