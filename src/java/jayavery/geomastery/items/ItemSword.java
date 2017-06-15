/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Collections;
import net.minecraft.creativetab.CreativeTabs;

/** Sword tool item. */
public class ItemSword extends ItemToolAbstract {

    public ItemSword(String name, ToolMaterial material) {

        super(4, -3.1F, material, Collections.emptySet());
        ItemSimple.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
}
