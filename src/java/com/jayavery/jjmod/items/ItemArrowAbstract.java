package com.jayavery.jjmod.items;

import com.jayavery.jjmod.entities.projectile.EntityProjectile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Abstract superclass for arrow items to fire custom projectiles. */
public abstract class ItemArrowAbstract extends ItemArrow {

    public ItemArrowAbstract(String name) {
    
        ItemJj.setupItem(this, name, 10, CreativeTabs.COMBAT);
    }
    
    /** Creates an EntityProjectile arrow of this type. */
    @Override
    public abstract EntityProjectile createArrow(World world, ItemStack stack,
            EntityLivingBase shooter);
}
