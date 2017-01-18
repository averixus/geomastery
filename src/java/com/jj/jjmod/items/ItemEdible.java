package com.jj.jjmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemEdible extends ItemFood {

    public ItemEdible(String name, int hunger, float saturation, int stackSize) {
        
        super(hunger, saturation, false);
        ItemNew.setupItem(this, name, stackSize, CreativeTabs.FOOD);
    }
}
