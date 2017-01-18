package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowCopper extends EntityArrow {

    public EntityArrowCopper(World world) {

        super(world);
        this.setDamage(1.33);
    }

    public EntityArrowCopper(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(1.33);
    }

    public EntityArrowCopper(World world, EntityLivingBase shooter) {

        super(world, shooter);
        this.setDamage(1.33);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowCopper);
    }
}
