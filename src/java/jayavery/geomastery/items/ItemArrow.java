/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.function.BiFunction;
import jayavery.geomastery.entities.projectile.EntityArrowBronze;
import jayavery.geomastery.entities.projectile.EntityArrowCopper;
import jayavery.geomastery.entities.projectile.EntityArrowFlint;
import jayavery.geomastery.entities.projectile.EntityArrowSteel;
import jayavery.geomastery.entities.projectile.EntityArrowWood;
import jayavery.geomastery.entities.projectile.EntityProjectile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Arrow items to fire custom projectiles. */
public abstract class ItemArrow extends net.minecraft.item.ItemArrow {
    
    private final BiFunction<World, EntityLivingBase, EntityProjectile> arrow;

    public ItemArrow(String name, BiFunction<World, EntityLivingBase,
            EntityProjectile> arrow) {
    
        ItemSimple.setupItem(this, name, 10, CreativeTabs.COMBAT);
        this.arrow = arrow;
    }
    
    /** Creates an EntityProjectile arrow of this type. */
    @Override
    public EntityProjectile createArrow(World world, ItemStack stack,
            EntityLivingBase shooter) {
        
        return this.arrow.apply(world, shooter);
    }
    
    public static class Wood extends ItemArrow {
        
        public Wood() {
            
            super("arrow_wood", EntityArrowWood::new);
        }
    }
    
    public static class Flint extends ItemArrow {
        
        public Flint() {
            
            super("arrow_flint", EntityArrowFlint::new);
        }
    }
    
    public static class Copper extends ItemArrow {
        
        public Copper() {
            
            super("arrow_copper", EntityArrowCopper::new);
        }
    }
    
    public static class Bronze extends ItemArrow {
        
        public Bronze() {
            
            super("arrow_bronze", EntityArrowBronze::new);
        }
    }
    
    public static class Steel extends ItemArrow {
        
        public Steel() {
            
            super("arrow_steel", EntityArrowSteel::new);
        }
    }
}
