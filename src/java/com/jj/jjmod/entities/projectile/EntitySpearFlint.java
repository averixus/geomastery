package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearFlint extends EntityArrow {

    public int durability = 0;

    public EntitySpearFlint(World world) {

        super(world);
        this.setDamage(6.66);
    }

    public EntitySpearFlint(World world,
            EntityLivingBase thrower, int durability) {

        super(world, thrower);
        this.durability = durability;
        this.setDamage(6.66);
    }

    public EntitySpearFlint(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(6.66);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= ModItems.spearFlint.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(ModItems.spearFlint, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (this.isDead) {

           EntitySpearFlint replace = new EntitySpearFlint(this.worldObj,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.worldObj.spawnEntityInWorld(replace);
       }
    }
}
