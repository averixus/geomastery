package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TEFurnaceCookfire extends TEFurnaceConstantAbstract {

    public TEFurnaceCookfire() {

        super(ModRecipes.COOKFIRE);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {

        return new ContainerFurnace(player, this.worldObj, this);
    }

    @Override
    public int getCookTime(ItemStack stack) {

        // CONFIG furnacePotfire item cook times

        return 400;
    }
}
