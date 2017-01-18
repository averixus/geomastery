package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;

public class ItemAxe extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2,
            Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN,
            Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON,
            Blocks.WOODEN_PRESSURE_PLATE, Blocks.LEAVES,
            ModBlocks.craftingCandlemaker, ModBlocks.craftingClayworks,
            ModBlocks.craftingTextiles, ModBlocks.craftingWoodworking,
            ModBlocks.drying, ModBlocks.woodApple, ModBlocks.woodBanana,
            ModBlocks.woodOrange, ModBlocks.woodPear});

    public ItemAxe(String name, ToolMaterial material) {

        super(3, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel("axe", 1);
    }
}
