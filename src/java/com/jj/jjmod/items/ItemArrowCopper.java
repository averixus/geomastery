package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowCopper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Copper arrow item. */
public class ItemArrowCopper extends ItemArrowAbstract {

    public ItemArrowCopper() {

        super("arrow_copper");
    }

    @Override
    public EntityArrowCopper createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowCopper(world, shooter);
    }
}
