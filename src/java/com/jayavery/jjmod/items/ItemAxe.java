package com.jayavery.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/** Axe tool item. */
public class ItemAxe extends ItemToolAbstract {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]
            {Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.LADDER,
            Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, Blocks.LEAVES,
            Blocks.LEAVES2});

    public ItemAxe(String name, ToolMaterial material) {

        super(3, -3.1F, material, EFFECTIVE_ON);
        ItemJj.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.AXE.toString(), 1);
    }
}
