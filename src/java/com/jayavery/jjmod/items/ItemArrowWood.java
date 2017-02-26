package com.jayavery.jjmod.items;

import com.jayavery.jjmod.entities.projectile.EntityArrowWood;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Wood arrow item. */
public class ItemArrowWood extends ItemArrowAbstract {

    public ItemArrowWood() {

        super("arrow_wood");
    }

    @Override
    public EntityArrowWood createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowWood(world, shooter);
    }
}
