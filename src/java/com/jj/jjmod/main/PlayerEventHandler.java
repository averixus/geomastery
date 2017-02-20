package com.jj.jjmod.main;

import com.jj.jjmod.blocks.BlockBedAbstract;
import com.jj.jjmod.blocks.BlockBedAbstract.EnumPartBed;
import com.jj.jjmod.blocks.BlockBedBreakableAbstract;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.items.ItemShield;
import com.jj.jjmod.tileentities.TEBed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Handler for player related events. */
public class PlayerEventHandler {

    /** Alters behaviour when player picks up an item. */
    @SubscribeEvent
    public void playerItemPickup(EntityItemPickupEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        
        if (player.capabilities.isCreativeMode) {
            
            return;
        }
        
        ItemStack stack = event.getItem().getEntityItem();
        ItemStack remaining = stack;
        System.out.println("playerItemPickup " + stack); 
        if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
            System.out.println(" with cap age " + stack.getCapability(ModCapabilities.CAP_DECAY, null).getBirthTime());
        } else {
            System.out.println(" with no capdecay");
        }
        remaining = ((ContainerInventory) player.inventoryContainer)
                .add(remaining);

        if (remaining.isEmpty()) {

            event.getItem().setDead();

        } else {
            
            event.getItem().setEntityItemStack(remaining);
        }

        event.setCanceled(true);
    }

    /** Alters behaviour when player drops an item. */
    @SubscribeEvent
    public void itemToss(ItemTossEvent event) {
        
        ContainerInventory.updateHand(event.getPlayer(), EnumHand.MAIN_HAND);
    }

    /** Adds behaviour when player wakes up from a bed. */
    @SubscribeEvent
    public void playerWakeUp(PlayerWakeUpEvent event) {

        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = new BlockPos(player);
        World world = event.getEntityPlayer().world;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof BlockBedAbstract)) {

            return;
        }

        EnumPartBed part = state.getValue(BlockBedAbstract.PART);
        EnumFacing facing = state.getValue(BlockHorizontal.FACING);

        BlockPos posFoot;
        BlockPos posHead;

        if (part == EnumPartBed.FOOT) {

            posFoot = pos;
            posHead = pos.offset(facing);

        } else {

            posHead = pos;
            posFoot = pos.offset(facing.getOpposite());
        }

        // Leaf nest breaks after one use
        if (block == ModBlocks.bedLeaf) {

            world.setBlockToAir(posFoot);
            world.setBlockToAir(posHead);
        }

        // Breakable beds take damage
        if (block instanceof BlockBedBreakableAbstract) {

            TileEntity tileEntity = world.getTileEntity(posFoot);

            if (tileEntity instanceof TEBed) {

                TEBed tileBed = (TEBed) tileEntity;
                tileBed.addUse();

                if (tileBed.isBroken()) {

                    world.setBlockToAir(posFoot);
                    world.setBlockToAir(posHead);
                    world.removeTileEntity(posFoot);
                }
            }
        }
        
        player.getCapability(ModCapabilities.CAP_PLAYER, null)
                .sleep(((BlockBedAbstract) block).getHealAmount());
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
        if (!source.isUnblockable() && player.isActiveItemStackBlocking()) {
            
            Vec3d sourceVec = source.getDamageLocation();

            if (sourceVec != null) {
                
                Vec3d playerVec = player.getLook(1.0F);
                Vec3d attackVec = sourceVec.subtractReverse(new
                        Vec3d(player.posX, player.posY,
                        player.posZ)).normalize();
                attackVec = new Vec3d(attackVec.xCoord,
                        0.0D, attackVec.zCoord);

                if (attackVec.dotProduct(playerVec) < 0.0D &&
                        event.getAmount() >= 3 &&
                        player.getActiveItemStack().getItem()
                        instanceof ItemShield) {
                    
                    EnumHand hand = player.getActiveHand();
                    player.getActiveItemStack().damageItem(1 +
                            MathHelper.floor(event.getAmount()), player);
                    
                    ContainerInventory.updateHand(player, hand);
                }
            }
        }
    }
}
