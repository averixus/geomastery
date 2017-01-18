package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemTool;

public class ItemMachete extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.LEAVES, ModBlocks.leafApple, ModBlocks.leafBanana,
            ModBlocks.leafOrange, ModBlocks.leafPear});

    public ItemMachete(String name, ToolMaterial material) {

        super(1, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.DECORATIONS);
        this.setHarvestLevel("machete", 1);
    }
}
