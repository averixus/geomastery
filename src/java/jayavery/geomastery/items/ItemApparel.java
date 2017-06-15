/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

/** Apparel and armour items. */
public class ItemApparel extends ItemArmor {

    public ItemApparel(String name, ArmorMaterial material,
            EntityEquipmentSlot slot, CreativeTabs tab) {
        
        super(material, (slot == EntityEquipmentSlot.LEGS ? 2 : 1), slot);
        ItemSimple.setupItem(this, name, 1, tab);
    }
}
