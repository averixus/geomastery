package com.jayavery.jjmod.main;

import java.util.List;
import com.jayavery.jjmod.blocks.BlockBed;
import com.jayavery.jjmod.blocks.BlockBed.EnumPartBed;
import com.jayavery.jjmod.capabilities.ICapPlayer;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.items.ItemJj;
import com.jayavery.jjmod.items.ItemShield;
import com.jayavery.jjmod.tileentities.TEBed;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Handler for player related events. */
public class PlayerEventHandler {

    /** Alters behaviour when player picks up an item. */
    @SubscribeEvent
    public void playerItemPickup(EntityItemPickupEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        
        if (player.capabilities.isCreativeMode ||
                !(player.inventoryContainer instanceof ContainerInventory)) {
            
            return;
        }
        
        ItemStack stack = event.getItem().getEntityItem();
        Item item = stack.getItem();
        
        // Special case for laid eggs
        if (item instanceof ItemEgg) {
            
            int count = stack.getCount();
            stack = ItemJj.newStack(ModItems.egg, count, player.world);
        }

        if (player.getCapability(ModCaps.CAP_PLAYER, null).canPickup(item)) {
            stack = ((ContainerInventory) player.inventoryContainer)
                    .add(stack);
        }

        if (stack.isEmpty()) {

            event.getItem().setDead();

        } else {
            
            event.getItem().setEntityItemStack(stack);
        }

        event.setCanceled(true);
    }

    /** Alters behaviour when player drops an item. */
    @SubscribeEvent
    public void itemToss(ItemTossEvent event) {
        
        EntityPlayer player = event.getPlayer();
        Item item = event.getEntityItem().getEntityItem().getItem();
        long time = player.world.getWorldTime();
        
        ContainerInventory.updateHand(player, EnumHand.MAIN_HAND);
        player.getCapability(ModCaps.CAP_PLAYER, null).addDelay(item, time);
        
    }

    /** Adds behaviour when player wakes up from a bed. */
    @SubscribeEvent
    public void playerWakeUp(PlayerWakeUpEvent event) {
        
        if (!event.shouldSetSpawn()) {

            return;
        }

        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = new BlockPos(player);
        World world = event.getEntityPlayer().world;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof BlockBed)) {

            return;
        }
        
        BlockPos posFoot = state.getValue(BlockBed.PART) == EnumPartBed.FOOT ?
                pos : pos.offset(state.getValue(BlockBed.FACING).getOpposite());
        
        BlockBed bed = (BlockBed) block;
        bed.onWakeup(world, posFoot, (TEBed) world.getTileEntity(posFoot));
        player.getCapability(ModCaps.CAP_PLAYER, null)
                .sleep(bed.getHealAmount());
    }
    
    /** Alters behaviour when the player takes damage. */
    @SubscribeEvent
    public void playerAttacked(LivingAttackEvent event) {
        
        if (!(event.getEntity() instanceof EntityPlayer)) {
            
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntity();
        DamageSource source = event.getSource();
        
        // Copy vanilla shield functionality to allow for custom shields
        if (!source.isUnblockable() && player.isActiveItemStackBlocking() &&
                player.getActiveItemStack().getItem() instanceof ItemShield) {
            
            Vec3d sourceVec = source.getDamageLocation();

            if (sourceVec != null) {
                
                Vec3d playerVec = player.getLook(1.0F);
                Vec3d attackVec = sourceVec.subtractReverse(new
                        Vec3d(player.posX, player.posY,
                        player.posZ)).normalize();
                attackVec = new Vec3d(attackVec.xCoord,
                        0.0D, attackVec.zCoord);

                if (attackVec.dotProduct(playerVec) < 0.0D &&
                        event.getAmount() >= 3) {
                    
                    EnumHand hand = player.getActiveHand();
                    player.getActiveItemStack().damageItem(1 +
                            MathHelper.floor(event.getAmount()), player);
                    
                    ContainerInventory.updateHand(player, hand);
                }
            }
        }
    }
    
    /** Adds yoke and backpack to player death drops. */
    @SubscribeEvent
    public void playerDrops(PlayerDropsEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        ICapPlayer capPlayer = player.getCapability(ModCaps.CAP_PLAYER, null);
        World world = player.world;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        List<EntityItem> drops = event.getDrops();
        drops.add(new EntityItem(world, x, y, z, capPlayer.removeBackpack()));
        drops.add(new EntityItem(world, x, y, z, capPlayer.removeYoke()));
    }
}
