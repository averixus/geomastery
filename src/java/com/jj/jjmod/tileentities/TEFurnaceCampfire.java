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

        return new ContainerFurnace(player, this.world, this);
    }

    @Override
    public int getCookTime(ItemStack stack) {

        // CONFIG furnaceCampfire item cook times

        return 400;
    }
}
