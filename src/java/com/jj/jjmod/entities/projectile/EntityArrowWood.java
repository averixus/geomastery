package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowWood extends EntityArrow {

    public EntityArrowWood(World world) {

        super(world);
        this.setDamage(0.44);
    }

    public EntityArrowWood(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(0.44);
    }

    public EntityArrowWood(World world, EntityLivingBase shooter) {

        super(world, shooter);
        this.setDamage(0.44);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowWood);
    }
}
