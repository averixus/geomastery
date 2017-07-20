/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/** Machete tool item. */
public class ItemMachete extends ItemTool {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.LEAVES, Blocks.LEAVES2});

    public ItemMachete(String name, ToolMaterial material) {

        super(1, -3.1F, material, EFFECTIVE_ON);
        ItemSimple.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(EToolType.MACHETE.toString(), 1);
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
}
