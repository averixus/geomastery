package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearBronze extends EntityArrow {

    public int durability = 0;

    public EntitySpearBronze(World world) {

        super(world);
        this.setDamage(9.33);
    }

    public EntitySpearBronze(World world, EntityLivingBase thrower,
            int durability) {

        super(world, thrower);
        this.durability = durability;
        this.setDamage(9.33);
    }

    public EntitySpearBronze(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(9.33);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= ModItems.spearBronze.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(ModItems.spearBronze, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (this.isDead) {

           EntitySpearBronze replace = new EntitySpearBronze(this.worldObj,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.worldObj.spawnEntityInWorld(replace);
       }
    }
}
