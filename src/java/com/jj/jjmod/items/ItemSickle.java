package com.jj.jjmod.items;

import java.util.Collections;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/** Sickle tool item. */
public class ItemSickle extends ItemToolAbstract {

    public ItemSickle(String name, ToolMaterial material) {

        super(1F, -3.1F, material, Collections.emptySet());
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.SICKLE.toString(), 1);
    }
}
