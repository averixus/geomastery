package com.jj.jjmod.items;

import net.minecraft.creativetab.CreativeTabs;

public class ItemShield extends net.minecraft.item.ItemShield {

    public ItemShield(String name, int durability) {

        super();
        this.setMaxDamage(durability);
        ItemNew.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
}
