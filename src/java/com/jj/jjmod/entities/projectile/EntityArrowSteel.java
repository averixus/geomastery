package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityArrowSteel extends EntityArrow {

    public EntityArrowSteel(World world) {

        super(world);
        this.setDamage(1.77);
    }

    public EntityArrowSteel(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(1.77);
    }

    public EntityArrowSteel(World world, EntityLivingBase shooter) {

        super(world, shooter);
        this.setDamage(1.77);
    }

    @Override
    protected ItemStack getArrowStack() {

        return new ItemStack(ModItems.arrowSteel);
    }
    
    @Override
    public void onUpdate() {
        
        super.onUpdate();
    }
}
