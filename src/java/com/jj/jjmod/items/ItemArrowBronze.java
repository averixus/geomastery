package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowBronze;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Bronze arrow item. */
public class ItemArrowBronze extends ItemArrowAbstract {

    public ItemArrowBronze() {

        super("arrow_bronze");
    }

    @Override
    public EntityArrowBronze createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowBronze(world, shooter);
    }
}
