package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowFlint;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Flint arrow item. */
public class ItemArrowFlint extends ItemArrowAbstract {

    public ItemArrowFlint() {

        super("arrow_flint");
    }

    @Override
    public EntityArrowFlint createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowFlint(world, shooter);
    }
}
