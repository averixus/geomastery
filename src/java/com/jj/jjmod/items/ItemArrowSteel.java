package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowSteel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Steel arrow item. */
public class ItemArrowSteel extends ItemArrowAbstract {

    public ItemArrowSteel() {

        super("arrow_steel");
    }

    @Override
    public EntityArrowSteel createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowSteel(world, shooter);
    }
}
