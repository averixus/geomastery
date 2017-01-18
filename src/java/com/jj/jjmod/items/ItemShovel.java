package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;

public class ItemShovel extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS,
            Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW,
            Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH});

    public ItemShovel(String name, ToolMaterial material) {

        super(2, -3.0F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel("shovel", 1);
    }
}
