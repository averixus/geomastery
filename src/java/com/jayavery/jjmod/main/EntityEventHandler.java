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
    
    @SubscribeEvent
    public void getCollisionBoxes(GetCollisionBoxesEvent event) {
        
        if (!(event.getEntity() instanceof FallingTreeBlock) /*||
                !(((FallingSideways) event.getEntity()).blockState.getBlock()
                instanceof BlockLog)*/) {
            
            return;
        }
        
        World world = event.getWorld();
        FallingTreeBlock entity = (FallingTreeBlock) event.getEntity();
        List<AxisAlignedBB> list = event.getCollisionBoxesList();
        AxisAlignedBB box = event.getAabb();
        
        list.clear();
        firstMethod(world, entity, box, list);
        secondMethod(world, entity, box, list);
    }
        
    private static void firstMethod(World world, Entity entity, AxisAlignedBB box, List<AxisAlignedBB> list) {
        
        int i = MathHelper.floor(box.minX) - 1;
        int j = MathHelper.ceil(box.maxX) + 1;
        int k = MathHelper.floor(box.minY) - 1;
        int l = MathHelper.ceil(box.maxY) + 1;
        int i1 = MathHelper.floor(box.minZ) - 1;
        int j1 = MathHelper.ceil(box.maxZ) + 1;
        WorldBorder worldborder = world.getWorldBorder();
        boolean flag = entity != null && entity.isOutsideBorder();
        boolean flag1 = entity != null && world.func_191503_g(entity);
        IBlockState iblockstate = Blocks.STONE.getDefaultState();
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for (int k1 = i; k1 < j; ++k1)
            {
                for (int l1 = i1; l1 < j1; ++l1)
                {
                    boolean flag2 = k1 == i || k1 == j - 1;
                    boolean flag3 = l1 == i1 || l1 == j1 - 1;

                    if ((!flag2 || !flag3) && world.isBlockLoaded(blockpos$pooledmutableblockpos.setPos(k1, 64, l1)))
                    {
                        for (int i2 = k; i2 < l; ++i2)
                        {
                            if (!flag2 && !flag3 || i2 != l - 1)
                            {
                                if (entity != null && flag == flag1)
                                {
                                    entity.setOutsideBorder(!flag1);
                                }

                                blockpos$pooledmutableblockpos.setPos(k1, i2, l1);
                                IBlockState iblockstate1;

                                if (!worldborder.contains(blockpos$pooledmutableblockpos) && flag1)
                                {
                                    iblockstate1 = iblockstate;
                                }
                                else
                                {
                                    iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos);
                                }

                                if (!(iblockstate1.getBlock() instanceof BlockLeaves)) {
                                
                                    iblockstate1.addCollisionBoxToList(world, blockpos$pooledmutableblockpos, box, list, entity, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        finally
        {
            blockpos$pooledmutableblockpos.release();
        }
    }   
    
    private static void secondMethod(World world, Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> list) {
        
        List<Entity> list1 = world.getEntitiesWithinAABBExcludingEntity(entityIn, aabb.expandXyz(0.25D));

        for (int i = 0; i < list1.size(); ++i)
        {
            Entity entity = list1.get(i);

            if (!entityIn.isRidingSameEntity(entity))
            {
                AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox();

                if (axisalignedbb != null && axisalignedbb.intersectsWith(aabb))
                {
                    list.add(axisalignedbb);
                }

                axisalignedbb = entityIn.getCollisionBox(entity);

                if (axisalignedbb != null && axisalignedbb.intersectsWith(aabb))
                {
                    list.add(axisalignedbb);
                }
            }
        }
    }
}
