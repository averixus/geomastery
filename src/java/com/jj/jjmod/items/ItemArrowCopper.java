package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntityArrowCopper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArrowCopper extends ItemArrow {

    public ItemArrowCopper() {

        super();
        ItemNew.setupItem(this, "arrow_copper", 10, CreativeTabs.COMBAT);
    }

    @Override
    public EntityArrowCopper createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {

        return new EntityArrowCopper(world, shooter);
    }
}
