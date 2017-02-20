package com.jj.jjmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

/** Custom shield item. */
public class ItemShield extends net.minecraft.item.ItemShield {

    public ItemShield(String name, int durability) {

        super();
        this.setMaxDamage(durability);
        ItemJj.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
    
    /** Bypass vanilla shield naming. */
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        return I18n.translateToLocal(this.getUnlocalizedName()).trim();
    }
}
