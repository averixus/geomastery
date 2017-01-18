package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemTool;

public class ItemHuntingknife extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON =
            Sets.newHashSet(new Block[] {});

    public ItemHuntingknife(String name, ToolMaterial material) {

        super(1F, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel("knife", 1);
        this.efficiencyOnProperMaterial = 0.25F;
    }
}
