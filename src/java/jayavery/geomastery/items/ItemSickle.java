/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Collections;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/** Sickle tool item. */
public class ItemSickle extends ItemTool {

    public ItemSickle(String name, ToolMaterial material) {

        super(1F, -3.1F, material, Collections.emptySet());
        ItemSimple.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(EToolType.SICKLE.toString(), 1);
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
}
