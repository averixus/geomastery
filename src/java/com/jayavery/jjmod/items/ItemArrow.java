package com.jayavery.jjmod.items;

import java.util.function.BiFunction;
import com.jayavery.jjmod.entities.projectile.EntityArrowBronze;
import com.jayavery.jjmod.entities.projectile.EntityArrowCopper;
import com.jayavery.jjmod.entities.projectile.EntityArrowFlint;
import com.jayavery.jjmod.entities.projectile.EntityArrowSteel;
import com.jayavery.jjmod.entities.projectile.EntityArrowWood;
import com.jayavery.jjmod.entities.projectile.EntityProjectile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** Arrow items to fire custom projectiles. */
public abstract class ItemArrow extends net.minecraft.item.ItemArrow {
    
    private final BiFunction<World, EntityLivingBase, EntityProjectile> arrow;

    public ItemArrow(String name, BiFunction<World, EntityLivingBase,
            EntityProjectile> arrow) {
    
        ItemJj.setupItem(this, name, 10, CreativeTabs.COMBAT);
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
