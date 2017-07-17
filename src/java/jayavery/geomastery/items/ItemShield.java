/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

/** Custom shield item. */
public class ItemShield extends net.minecraft.item.ItemShield {

    public ItemShield(String name, int durability) {

        super();
        this.setMaxDamage(durability);
        ItemSimple.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
    
    /** Bypass vanilla shield naming. */
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        return I18n.translateToLocal(this
                .getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
}
