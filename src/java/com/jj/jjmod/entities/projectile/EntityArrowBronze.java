package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowBronze extends EntityProjectile {

    private static final double damage = 1.55D;
    
    public EntityArrowBronze(World world) {

        super(world, damage);
    }

    public EntityArrowBronze(World world, double x, double y, double z) {

        super(world, x, y, z, damage);
    }

    public EntityArrowBronze(World world, EntityLivingBase shooter) {

        super(world, shooter, damage);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowBronze);
    }
}
