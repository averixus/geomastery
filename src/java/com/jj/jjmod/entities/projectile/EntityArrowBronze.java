package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowBronze extends EntityProjectile {

    /** Damager done by this arrow. */
    private static final double DAMAGE = 1.55D;
    
    public EntityArrowBronze(World world) {

        super(world, DAMAGE);
    }

    public EntityArrowBronze(World world, double x, double y, double z) {

        super(world, x, y, z, DAMAGE);
    }

    public EntityArrowBronze(World world, EntityLivingBase shooter) {

        super(world, shooter, DAMAGE);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowBronze);
    }
}
