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

/** Shovel tool item. */
public class ItemShovel extends ItemTool {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS,
            Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW,
            Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH});

    public ItemShovel(String name, ToolMaterial material) {

        super(2, -3.0F, material, EFFECTIVE_ON);
        ItemSimple.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(EToolType.SHOVEL.toString(), 1);
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
}
