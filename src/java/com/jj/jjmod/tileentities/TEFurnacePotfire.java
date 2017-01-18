package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TEFurnacePotfire extends TEFurnaceConstantAbstract {

    public TEFurnacePotfire() {

        super(ModRecipes.POTFIRE);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {

        return new ContainerFurnace(player, this.worldObj, this);
    }

    @Override
    public int getFuelTime(ItemStack stack) {

        // CONFIG furnacePotfire fuel times

        if (stack == null) {

            return 0;
        }

        Item item = stack.getItem();
        return 0;
    }

    @Override
    public int getCookTime(ItemStack stack) {

        // CONFIG furnacePotfire item cook times

        return 400;
    }
}
