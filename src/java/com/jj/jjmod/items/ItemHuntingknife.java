package com.jj.jjmod.items;

import java.util.Collections;
import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemTool;

/** Hunting knife tool item. */
public class ItemHuntingknife extends ItemToolAbstract {

    public ItemHuntingknife(String name, ToolMaterial material) {

        super(1F, -3.1F, material, Collections.emptySet());
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.KNIFE.toString(), 1);
        this.efficiencyOnProperMaterial = 0.25F;
    }
}
