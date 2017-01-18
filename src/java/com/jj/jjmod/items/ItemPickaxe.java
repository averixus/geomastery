package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;

public class ItemPickaxe extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE,
            Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
            Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK,
            Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE,
            Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE,
            Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE,
            Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE,
            Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB,
            Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE,
            ModBlocks.furnaceClay, ModBlocks.furnaceStone,
            ModBlocks.craftingKnapping, ModBlocks.craftingMason,
            ModBlocks.craftingForge, ModBlocks.lodeAmethyst,
            ModBlocks.lodeFireopal, ModBlocks.lodeRuby, ModBlocks.lodeSapphire,
            ModBlocks.oreCopper, ModBlocks.oreSilver, ModBlocks.oreTin});

    public ItemPickaxe(String name, ToolMaterial material) {

        super(2.5F, -2.8F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel("pickaxe", 1);
    }
}
