/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.entities.projectile;

import jayavery.geomastery.main.GeoItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/** Flint spear projectile entity. */
public class EntitySpearFlint extends EntityProjectile {
    
    /** Damager done by this spear. */
    private static final double DAMAGE = 6.66D;
    
    /** Durability of this spear's item. */
    private int durability = 0;

    public EntitySpearFlint(World world) {

        super(world, DAMAGE);
    }

    public EntitySpearFlint(World world,
            EntityLivingBase thrower, int durability) {

        super(world, thrower, DAMAGE);
        this.durability = durability;
    }

    public EntitySpearFlint(World world, double x, double y, double z) {

        super(world, x, y, z, DAMAGE);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= GeoItems.SPEAR_FLINT.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(GeoItems.SPEAR_FLINT, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (!this.world.isRemote && this.isDead) {

           EntitySpearFlint replace = new EntitySpearFlint(this.world,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.world.spawnEntity(replace);
       }
    }
}
