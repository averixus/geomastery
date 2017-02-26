package com.jayavery.jjmod.items;

import com.jayavery.jjmod.entities.projectile.EntityArrowSteel;
import net.minecraft.entity.EntityLivingBase;
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
