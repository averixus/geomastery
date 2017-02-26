package com.jayavery.jjmod.items;

import com.jayavery.jjmod.entities.projectile.EntityArrowFlint;
import net.minecraft.entity.EntityLivingBase;
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
