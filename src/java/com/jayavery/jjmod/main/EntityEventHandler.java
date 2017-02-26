package com.jayavery.jjmod.main;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.items.ItemJj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
        }
    }
}
