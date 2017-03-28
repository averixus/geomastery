package com.jayavery.jjmod.main;

import java.util.List;
import com.jayavery.jjmod.entities.FallingTreeBlock;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.items.ItemJj;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Handler for entity related events. */
public class EntityEventHandler {

    /** Alters drops for vanilla entities. */
    @SubscribeEvent
    public void livingDrops(LivingDropsEvent event) {

        Entity entity = event.getEntity();
        World world = entity.world;

        if (entity.world.isRemote) {

            return;
        }

        if (entity instanceof EntityPig) {

            event.getDrops().clear();
            entity.entityDropItem(ItemJj
                    .newStack(ModItems.carcassPig, 1, world), 0);

        } else if (entity instanceof EntityCow) {

            event.getDrops().clear();
            entity.entityDropItem(ItemJj
                    .newStack(ModItems.carcassCowpart, 4, world), 0);
            
        } else if (entity instanceof EntitySheep) {

            event.getDrops().clear();
            entity.entityDropItem(ItemJj
                    .newStack(ModItems.carcassSheep, 1, world), 0);

        } else if (entity instanceof EntityChicken) {

            event.getDrops().clear();
            entity.entityDropItem(ItemJj
                    .newStack(ModItems.carcassChicken, 1, world), 0);

        } else if (entity instanceof EntityRabbit) {

            event.getDrops().clear();
            entity.entityDropItem(ItemJj
                    .newStack(ModItems.carcassRabbit, 1, world), 0);
            
        } else if (entity instanceof EntityMob) {
            
            event.getDrops().clear();
        }
    }
    
    /** Reduces animal spawns by half. */
    @SubscribeEvent
    public void checkSpawn(CheckSpawn event) {

        EntityLivingBase entity  = event.getEntityLiving();
        boolean canSpawn = true;
        
        if (entity instanceof EntityAnimal) {
            
            if (entity.world.rand.nextInt(2) == 0) {
                
                canSpawn = false;
            }
        }

        event.setResult(canSpawn ? Result.DEFAULT : Result.DENY);
    }
    
    /** Allows falling trunks to pass through leaves. */
    @SubscribeEvent
    public void getCollisionBoxes(GetCollisionBoxesEvent event) {
        
        if (event.getEntity() instanceof FallingTreeBlock.Trunk) {

            ((FallingTreeBlock.Trunk) event.getEntity())
                    .getCollisionBoxes(event.getWorld(), event.getAabb(),
                    event.getCollisionBoxesList());
        }
    }
}
