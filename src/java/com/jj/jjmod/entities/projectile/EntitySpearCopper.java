package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearCopper extends EntityProjectile {

    public int durability = 0;
    
    private static final double damage = 8D;

    public EntitySpearCopper(World world) {

        super(world, damage);
    }

    public EntitySpearCopper(World world, EntityLivingBase thrower,
            int durability) {

        super(world, thrower, damage);
        this.durability = durability;
    }

    public EntitySpearCopper(World world, double x, double y, double z) {

        super(world, x, y, z, damage);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= ModItems.spearCopper.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(ModItems.spearCopper, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (this.isDead) {

           EntitySpearCopper replace = new EntitySpearCopper(this.world,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.world.spawnEntity(replace);
       }
    }
}
