package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;

public class ItemSword extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON =
            Sets.newHashSet(new Block[] {});

    public ItemSword(String name, ToolMaterial material) {

        super(4, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.COMBAT);
    }
}
