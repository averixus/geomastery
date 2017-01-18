package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;

public class ItemSickle extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.WHEAT, Blocks.POTATOES, Blocks.CARROTS, Blocks.BEETROOTS,
            ModBlocks.riceBase, ModBlocks.riceTop, ModBlocks.chickpea,
            ModBlocks.cotton, ModBlocks.bean, ModBlocks.berry,
            ModBlocks.tomato, ModBlocks.hemp});

    public ItemSickle(String name, ToolMaterial material) {

        super(1F, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel("sickle", 1);
    }
}
