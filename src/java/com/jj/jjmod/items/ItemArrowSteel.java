package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowSteel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArrowSteel extends ItemArrow {

    public ItemArrowSteel() {

        super();
        ItemNew.setupItem(this, "arrow_steel", 10, CreativeTabs.COMBAT);
    }

    @Override
    public EntityArrowSteel createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowSteel(world, shooter);
    }
}
