/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import jayavery.geomastery.entities.projectile.EntityProjectile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/** Functional interface for EntityProjectile constructor. */
@FunctionalInterface
public interface IProjectileFactory {
    
    public EntityProjectile create(World world, EntityLivingBase thrower,
            int durability);
}
