/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.entities.projectile;

import jayavery.geomastery.container.ContainerInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Abstract superclass for Arrow and Spear entities. */
public abstract class EntityProjectile extends EntityArrow {
    
    /** Velocity modifier for war bow. */
    public static final float BOW_MOD = 3F;
    /** Velocity modifier for crude bow. */
    public static final float CRUDE_MOD = 2F;
    /** Velocity modifier for spears. */
    public static final float SPEAR_MOD = 1.8F;
    
    public EntityProjectile(World world, double damage) {
        
        super(world);
        this.setDamage(damage);
    }
    
    public EntityProjectile(World world,
            EntityLivingBase thrower, double damage) {
        
        super(world, thrower);
        this.setDamage(damage);        
    }
    
    public EntityProjectile(World world, double x,
            double y, double z, double damage) {
        
        super(world, x, y, z);
        this.setDamage(damage);
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        
        if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
            
            boolean pickup = this.pickupStatus ==
                    EntityArrow.PickupStatus.ALLOWED ||
                    (this.pickupStatus ==
                    EntityArrow.PickupStatus.CREATIVE_ONLY &&
                    player.capabilities.isCreativeMode);
            
            if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED &&
                    !player.capabilities.isCreativeMode) {
                           
                if (!ContainerInventory.add(player,
                        this.getArrowStack()).isEmpty()) {
                    
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
