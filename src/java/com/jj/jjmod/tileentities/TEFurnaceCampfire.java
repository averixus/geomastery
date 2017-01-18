package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TEFurnaceCampfire extends TEFurnaceConstantAbstract {

    public TEFurnaceCampfire() {

        super(ModRecipes.CAMPFIRE);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {

        return new ContainerFurnace(player, this.worldObj, this);
    }

    @Override
    public int getFuelTime(ItemStack stack) {

        // CONFIG furnaceCampfire fuel times

        if (stack == null) {

            return 0;
        }

        Item item = stack.getItem();

        if (item == Items.STICK) {

            return 200;
        }

        if (item == ModItems.pole) {

            return 500;
        }

        if (item == ModItems.log) {

            return 2000;
        }

        if (item == ModItems.thicklog) {

            return 2000;
        }

        return 0;
    }

    @Override
    public int getCookTime(ItemStack stack) {

        // CONFIG furnaceCampfire item cook times

        return 400;
    }
}
