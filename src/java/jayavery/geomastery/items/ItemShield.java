/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Custom shield item. */
public class ItemShield extends net.minecraft.item.ItemShield {

    public ItemShield(String name, int durability) {

        super();
        this.setMaxDamage(durability);
        ItemSimple.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
    
    // Bypasses vanilla shield naming
    @SideOnly(Side.CLIENT) @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        return I18n.format(this.getUnlocalizedName() + ".name").trim();
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
}
