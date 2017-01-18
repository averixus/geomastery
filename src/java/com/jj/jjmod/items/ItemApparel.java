package com.jj.jjmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemApparel extends ItemArmor {

    public ItemApparel(String name, ArmorMaterial material,
            EntityEquipmentSlot slot) {
        
        super(material, (slot == EntityEquipmentSlot.LEGS ? 2 : 1), slot);
        ItemNew.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
}
