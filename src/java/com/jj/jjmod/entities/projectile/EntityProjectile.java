package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.DefaultCapInventory;
import com.jj.jjmod.container.ContainerInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityProjectile extends EntityArrow {
    
    public EntityProjectile(World world, double damage) {
        
        super(world);
        this.setDamage(damage);
    }
    
    public EntityProjectile(World world, EntityLivingBase thrower, double damage) {
        
        super(world, thrower);
        this.setDamage(damage);        
    }
    
    public EntityProjectile(World world, double x, double y, double z, double damage) {
        
        super(world, x, y, z);
        this.setDamage(damage);
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        
        if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
            
            boolean pickup = this.pickupStatus == EntityArrow.PickupStatus.ALLOWED || this.pickupStatus == EntityArrow.PickupStatus.CREATIVE_ONLY && player.capabilities.isCreativeMode;
            
            if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED && !player.capabilities.isCreativeMode) {
                
                ItemStack remaining = ((ContainerInventory) player.inventoryContainer).add(this.getArrowStack());
           
                if (!remaining.isEmpty()) {
                    
                    pickup = false;
                }
            }

            if (pickup) {
                
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
}
