package com.jj.jjmod.main;

import com.jj.jjmod.blocks.BlockBedAbstract;
import com.jj.jjmod.blocks.BlockBedAbstract.EnumPartBed;
import com.jj.jjmod.blocks.BlockBedBreakable;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
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

public class PlayerEventHandler {

 /** --------------------------- PLAYER EVENTS -------------------------- */
    
  /*  @SubscribeEvent
    public void playerJoin(EntityJoinWorldEvent event) {
       
        
        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();
            
            player.getCapability(CapTemperature.CAP_TEMPERATURE, null)
                    .sendMessage();
            player.getCapability(CapFoodstats.CAP_FOODSTATS, null)
                    .sendMessage();
        }
    }
    
    @SubscribeEvent
    public void playerCapabilities(AttachCapabilitiesEvent<Entity> event) {

        if (!(event.getObject() instanceof EntityPlayer)) {

            return;
        }

        EntityPlayer player = (EntityPlayer) event.getObject();
        
        if (!(player.hasCapability(CapInventory.CAP_INVENTORY, null))) {

            event.addCapability(CapInventory.ID,
                    new ProviderCapInventory(new DefaultCapInventory(player)));
        }
        
        if (!(player.hasCapability(CapTemperature.CAP_TEMPERATURE, null))) {
            
            event.addCapability(CapTemperature.ID,
                    new ProviderCapTemperature(new
                    DefaultCapTemperature(player)));
        }
        
        if (!(player.hasCapability(CapFoodstats.CAP_FOODSTATS, null))) {
            
            event.addCapability(CapFoodstats.ID, new ProviderCapFoodstats(new
                    DefaultCapFoodstats(player)));
        }
    }

    @SubscribeEvent
    public void playerTick(PlayerTickEvent event) {
        
        if (event.phase == Phase.START) {
            
            return;
        }

        EntityPlayer player = event.player;        
        player.getCapability(CapTemperature.CAP_TEMPERATURE, null).update();
        player.getCapability(CapInventory.CAP_INVENTORY, null).update();
                
        if (player.inventoryContainer instanceof ContainerPlayer &&
                !player.capabilities.isCreativeMode) {
            
            player.inventoryContainer =
                    new ContainerInventory(player, player.world);
            player.openContainer = player.inventoryContainer;
            
        } else if (player.inventoryContainer instanceof ContainerInventory &&
                player.capabilities.isCreativeMode) {
            
            player.inventoryContainer = new ContainerPlayer(player.inventory,
                    !player.world.isRemote, player);
            player.openContainer = player.inventoryContainer;

        }
        
        if (player.inventoryContainer instanceof ContainerInventory) {
            
            for (Slot slot : player.inventoryContainer.inventorySlots) {
                
                ItemStack stack = slot.getStack();
                
                if (stack.getItem() instanceof ItemEdibleDecayable) {
                    
                    if (stack.getCapability(CapDecay.CAP_DECAY, null).updateAndRot()) {
                        
                        slot.putStack(new ItemStack(ModItems.rot));
                    }
                }
            }
        }

        if (!(player.getFoodStats() instanceof FoodStatsWrapper)) {
            
            ReflectionHelper.setPrivateValue(EntityPlayer.class, player,
                    new FoodStatsWrapper(player), "foodStats");
        }
    }*/
    
    @SubscribeEvent
    public void playerItemPickup(EntityItemPickupEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        
        if (player.capabilities.isCreativeMode) {
            
            return;
        }
        
        ItemStack stack = event.getItem().getEntityItem();
        Item item = stack.getItem();
        ItemStack remaining = stack;
        
        remaining = ((ContainerInventory) player.inventoryContainer).add(remaining);

        if (remaining.isEmpty()) {

            event.getItem().setDead();

        } else {
            
            event.getItem().setEntityItemStack(remaining);
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public void itemToss(ItemTossEvent event) {
        
        if (!event.getPlayer().capabilities.isCreativeMode) {

            ((ContainerInventory) event.getPlayer().inventoryContainer)
                    .sendUpdateHighlight();
        }
    }

    @SubscribeEvent
    public void playerWakeUp(PlayerWakeUpEvent event) {

        BlockPos pos = new BlockPos(event.getEntityPlayer());
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

        if (block == ModBlocks.bedLeaf) {

            world.setBlockToAir(posFoot);
            world.setBlockToAir(posHead);
        }

        if (block instanceof BlockBedBreakable) {

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
    }
    
    @SubscribeEvent
    public void playerAttacked(LivingAttackEvent event) {
        
        if (!(event.getEntity() instanceof EntityPlayer)) {
            
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntity();
        DamageSource source = event.getSource();
        
        if (!source.isUnblockable() && player.isActiveItemStackBlocking()) {
            
            System.out.println("can block");
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
                    
                    if (player.getActiveItemStack().isEmpty()) {
                        
                        if (hand == EnumHand.MAIN_HAND) {
                            
                            player.setItemStackToSlot(
                                    EntityEquipmentSlot.MAINHAND,
                                    ItemStack.EMPTY);
                            
                        } else {
                            
                            player.setItemStackToSlot(
                                    EntityEquipmentSlot.OFFHAND,
                                    ItemStack.EMPTY);
                        }
                    }
                    
                    if (hand == EnumHand.MAIN_HAND) {
                        
                        ((ContainerInventory) player.inventoryContainer)
                                .sendUpdateHighlight();
                    
                    } else {
                        
                        ((ContainerInventory) player.inventoryContainer)
                            .sendUpdateOffhand();
                    }
                }
            }
        }
    }
}
