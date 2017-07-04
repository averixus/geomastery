/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.entities.projectile;

import jayavery.geomastery.main.GeoItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Wooden arrow projectile entity. */
public class EntityArrowWood extends EntityProjectile {

    /** Damager done by this arrow. */
    private static final double DAMAGE = 0.44D;
    
    public EntityArrowWood(World world) {

        super(world, DAMAGE);
    }

    public EntityArrowWood(World world, double x, double y, double z) {

        super(world, x, y, z, DAMAGE);
    }

    public EntityArrowWood(World world, EntityLivingBase shooter) {

        super(world, shooter, DAMAGE);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(GeoItems.ARROW_WOOD);
    }
}
