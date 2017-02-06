package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowWood extends EntityProjectile {

    private static final double damage = 0.44D;
    
    public EntityArrowWood(World world) {

        super(world, damage);
    }

    public EntityArrowWood(World world, double x, double y, double z) {

        super(world, x, y, z, damage);
    }

    public EntityArrowWood(World world, EntityLivingBase shooter) {

        super(world, shooter, damage);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowWood);
    }
}
