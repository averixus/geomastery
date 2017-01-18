package com.jj.jjmod.entities.projectile;

import com.jj.jjmod.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearSteel extends EntityArrow {

    public int durability = 0;

    public EntitySpearSteel(World world) {

        super(world);
        this.setDamage(10.66);
    }

    public EntitySpearSteel(World world,
            EntityLivingBase thrower, int durability) {

        super(world, thrower);
        this.durability = durability;
        this.setDamage(10.66);
    }

    public EntitySpearSteel(World world, double x, double y, double z) {

        super(world, x, y, z);
        this.setDamage(10.66);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= ModItems.spearSteel.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(ModItems.spearSteel, 1, this.durability + 1);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (this.isDead) {

           EntitySpearSteel replace = new EntitySpearSteel(this.worldObj,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.worldObj.spawnEntityInWorld(replace);
       }
    }
}
