package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowFlint extends EntityArrow {

    public EntityArrowFlint(World world) {

        super(world);
        this.setDamage(0.88);
    }

    public EntityArrowFlint(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(0.88);
    }

    public EntityArrowFlint(World world, EntityLivingBase shooter) {

        super(world, shooter);
        this.setDamage(0.88);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowFlint);
    }
}
