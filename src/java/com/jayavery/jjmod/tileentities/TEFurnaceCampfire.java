package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.container.ContainerFurnaceSingle;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.init.ModRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**TileEntity for campfire furnace. */
public class TEFurnaceCampfire extends TEFurnaceSingleAbstract {

    public TEFurnaceCampfire() {

        super(ModRecipes.CAMPFIRE);
    }
}
