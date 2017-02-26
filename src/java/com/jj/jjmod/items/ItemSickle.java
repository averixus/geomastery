package com.jj.jjmod.items;

import java.util.Collections;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.creativetab.CreativeTabs;

/** Sickle tool item. */
public class ItemSickle extends ItemToolAbstract {

    public ItemSickle(String name, ToolMaterial material) {

        super(1F, -3.1F, material, Collections.emptySet());
        ItemJj.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.SICKLE.toString(), 1);
    }
}
