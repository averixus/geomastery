package com.jayavery.jjmod.items;

import java.util.Collections;
import net.minecraft.creativetab.CreativeTabs;

/** Sword tool item. */
public class ItemSword extends ItemToolAbstract {

    public ItemSword(String name, ToolMaterial material) {

        super(4, -3.1F, material, Collections.emptySet());
        ItemJj.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
}
