package com.jayavery.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

/** Shovel tool item. */
public class ItemShovel extends ItemToolAbstract {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS,
            Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW,
            Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH});

    public ItemShovel(String name, ToolMaterial material) {

        super(2, -3.0F, material, EFFECTIVE_ON);
        ItemJj.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.SHOVEL.toString(), 1);
    }
}
