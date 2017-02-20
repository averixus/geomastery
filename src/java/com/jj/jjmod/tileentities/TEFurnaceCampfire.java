package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnaceSingle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**TileEntity for campfire furnace. */
public class TEFurnaceCampfire extends TEFurnaceConstantAbstract {

    public TEFurnaceCampfire() {

        super(ModRecipes.CAMPFIRE);
    }
}
