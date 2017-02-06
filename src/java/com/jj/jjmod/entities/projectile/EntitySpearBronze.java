package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearBronze extends EntityProjectile {

    public int durability = 0;
    
    private static final double damage = 9.33;

    public EntitySpearBronze(World world) {

        super(world, damage);
    }

    public EntitySpearBronze(World world, EntityLivingBase thrower,
            int durability) {

        super(world, thrower, damage);
        this.durability = durability;
    }

    public EntitySpearBronze(World world, double x, double y, double z) {

        super(world, x, y, z, damage);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability >= ModItems.spearBronze.getMaxDamage()) {
            
            return ItemStack.field_190927_a;
            
        } else {

            return new ItemStack(ModItems.spearBronze, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytrace) {
        
       super.onHit(raytrace);
       
       if (this.isDead) {

           EntitySpearBronze replace = new EntitySpearBronze(this.worldObj,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.worldObj.spawnEntityInWorld(replace);
       }
    }
}
