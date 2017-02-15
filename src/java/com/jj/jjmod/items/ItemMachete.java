package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/** Machete tool item. */
public class ItemMachete extends ItemToolAbstract {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON =
            Sets.newHashSet(new Block[] {Blocks.LEAVES, Blocks.LEAVES2});

    public ItemMachete(String name, ToolMaterial material) {

        super(1, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.DECORATIONS);
        this.setHarvestLevel(ToolType.MACHETE.toString(), 1);
    }
}
