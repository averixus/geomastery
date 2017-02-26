package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearSteel extends EntityProjectile {
    
    /** Damager done by this spear. */
    private static final double DAMAGE = 10.66D;
    
    /** Durability of this spear's item. */
    public int durability = 0;

    public EntitySpearSteel(World world) {

        super(world, DAMAGE);
    }

    public EntitySpearSteel(World world,
            EntityLivingBase thrower, int durability) {

        super(world, thrower, DAMAGE);
        this.durability = durability;
    }

    public EntitySpearSteel(World world, double x, double y, double z) {

        super(world, x, y, z, DAMAGE);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= ModItems.spearSteel.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(ModItems.spearSteel, 1, this.durability + 1);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (this.isDead) {

           EntitySpearSteel replace = new EntitySpearSteel(this.world,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.world.spawnEntity(replace);
       }
    }
}
