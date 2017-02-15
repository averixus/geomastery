package com.jj.jjmod.items;

import java.util.Collections;
import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

/** Sword tool item. */
public class ItemSword extends ItemToolAbstract {

    public ItemSword(String name, ToolMaterial material) {

        super(4, -3.1F, material, Collections.emptySet());
        ItemNew.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
}
