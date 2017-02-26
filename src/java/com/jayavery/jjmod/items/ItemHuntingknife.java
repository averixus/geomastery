package com.jayavery.jjmod.items;

import java.util.Collections;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.creativetab.CreativeTabs;

/** Hunting knife tool item. */
public class ItemHuntingknife extends ItemToolAbstract {

    public ItemHuntingknife(String name, ToolMaterial material) {

        super(1F, -3.1F, material, Collections.emptySet());
        ItemJj.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.KNIFE.toString(), 1);
        this.efficiencyOnProperMaterial = 0.25F;
    }
}
