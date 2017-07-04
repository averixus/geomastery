/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/** Axe tool item. */
public class ItemAxe extends ItemToolAbstract {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.LADDER,
            Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE});

    public ItemAxe(String name, ToolMaterial material) {

        super(3, -3.1F, material, EFFECTIVE_ON);
        ItemSimple.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.AXE.toString(), 1);
    }
}
