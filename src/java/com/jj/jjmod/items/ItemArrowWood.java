package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowWood;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArrowWood extends ItemArrow {

    public ItemArrowWood() {

        super();
        ItemNew.setupItem(this, "arrow_wood", 10, CreativeTabs.COMBAT);
    }

    @Override
    public EntityArrowWood createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowWood(world, shooter);
    }
}
