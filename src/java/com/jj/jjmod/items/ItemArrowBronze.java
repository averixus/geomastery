package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowBronze;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArrowBronze extends ItemArrow {

    public ItemArrowBronze() {

        super();
        ItemNew.setupItem(this, "arrow_bronze", 10, CreativeTabs.COMBAT);
    }

    @Override
    public EntityArrowBronze createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowBronze(world, shooter);
    }
}
