package com.jj.jjmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/** General superclass for new items and basic implementation. */
public class ItemNew extends Item {
    
    public ItemNew(String name, int stackSize, CreativeTabs tab) {
        
        setupItem(this, name, stackSize, tab);
    }
    
    public ItemNew(String name, int stackSize) {
        
        this(name, stackSize, CreativeTabs.MATERIALS);
    }
    
    public ItemNew(String name) {
        
        this(name, 1);
    }
    
    /** Applies constructor functions statically. */
    public static void setupItem(Item item, String name,
            int stackSize, CreativeTabs tab) {
        
        item.setRegistryName("item_" + name);
        item.setUnlocalizedName(item.getRegistryName().toString());
        item.setMaxStackSize(stackSize);
        item.setCreativeTab(tab);
    }
}
