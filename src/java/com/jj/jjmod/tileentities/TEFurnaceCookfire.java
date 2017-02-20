package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnaceSingle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** TileEntity for cookfire furnace. */
public class TEFurnaceCookfire extends TEFurnaceConstantAbstract {

    public TEFurnaceCookfire() {

        super(ModRecipes.COOKFIRE);
    }
}
