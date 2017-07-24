/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.List;
import jayavery.geomastery.blocks.BlockBed;
import jayavery.geomastery.blocks.BlockBed.EPartBed;
import jayavery.geomastery.capabilities.DefaultCapPlayer;
import jayavery.geomastery.capabilities.ICapPlayer;
import jayavery.geomastery.capabilities.ProviderCapPlayer;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.items.ItemShield;
import jayavery.geomastery.items.ItemSimple;
import jayavery.geomastery.packets.CPacketConfig;
import jayavery.geomastery.packets.SPacketContainer;
import jayavery.geomastery.tileentities.TEBed;
import jayavery.geomastery.utilities.FoodStatsWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/** Handler for player related events. */
public class PlayerEvents {

    /** Initial sync of ICapPlayer. */
    @SubscribeEvent
    public void playerJoin(EntityJoinWorldEvent event) {
       
        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();
            player.getCapability(GeoCaps.CAP_PLAYER, null).syncAll();
        }
    }
    
    /** Attaches ICapPlayer to the Player. */
    @SubscribeEvent
    public void playerCapabilities(AttachCapabilitiesEvent<Entity> event) {

        if (!(event.getObject() instanceof EntityPlayer)) {

            return;
        }

        EntityPlayer player = (EntityPlayer) event.getObject();
        
        if (!(player.hasCapability(GeoCaps.CAP_PLAYER, null))) {

            event.addCapability(GeoCaps.CAP_PLAYER_ID,
                    new ProviderCapPlayer(new DefaultCapPlayer(player)));
        }
    }
    
    /** Tick Capabilities and check additional player settings. */
    @SubscribeEvent
    public void playerTick(PlayerTickEvent event) {

        if (event.phase != Phase.START) {
            
            return;
        }
        
        EntityPlayer player = event.player;
        player.getCapability(GeoCaps.CAP_PLAYER, null).tick();
        
        if (player.inventoryContainer instanceof ContainerPlayer &&
                !player.capabilities.isCreativeMode &&
                !(player instanceof EntityPlayerMP)) {
            
            Geomastery.LOG.info("Replacing {}'s inventory container",
                    player.getName());
            player.inventoryContainer = new ContainerInventory(player);
            player.openContainer = player.inventoryContainer;
            Geomastery.NETWORK.sendToServer(new SPacketContainer());
            
                        
        } else if (player.inventoryContainer instanceof ContainerInventory &&
                player.capabilities.isCreativeMode) {
            
            player.inventoryContainer = new ContainerPlayer(player.inventory,
                    !player.world.isRemote, player);
            player.openContainer = player.inventoryContainer;
        }
    
        if (GeoConfig.gameplay.food &&
                !(player.getFoodStats() instanceof FoodStatsWrapper)) {
            
            Geomastery.LOG.info("Replacing {}'s vanilla foodstats",
                    player.getName());
            ReflectionHelper.setPrivateValue(EntityPlayer.class, player,
                    new FoodStatsWrapper(player),
                    "foodStats", "field_71100_bB");
        }
    }
    
    /** Drops excess items in case of using vanilla containers. */
    @SubscribeEvent
    public void containerClose(PlayerContainerEvent.Close event) {
        
        EntityPlayer player = event.getEntityPlayer();
        int size = player.getCapability(GeoCaps.CAP_PLAYER, null)
                .getInventorySize();
        
        for (int i = size; i < player.inventory.mainInventory.size(); i++) {
            
            ItemStack stack = player.inventory.mainInventory.get(i);
            player.inventory.mainInventory.set(i, ItemStack.EMPTY);
            
            if (!stack.isEmpty()) {
                
                player.dropItem(stack, true);
            }
        }
    }
    
    /** Alters behaviour when player picks up an item. */
    @SubscribeEvent
    public void playerItemPickup(EntityItemPickupEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        
        ItemStack stack = event.getItem().getEntityItem();
        Item item = stack.getItem();
        
        // Special case for laid eggs
        if (item instanceof ItemEgg) {
            
            int count = stack.getCount();
            stack = ItemSimple.newStack(GeoItems.EGG, count, player.world);
        }

        if (player.getCapability(GeoCaps.CAP_PLAYER, null).canPickup(item)) {
            
            stack = ContainerInventory.add(player, stack);
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
        
        player.getCapability(GeoCaps.CAP_PLAYER, null).addDelay(item, time);
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

        if (!(block instanceof BlockBed) || world.isRemote) {

            return;
        }
        
        BlockPos posFoot = state.getValue(BlockBed.PART) == EPartBed.FOOT ?
                pos : pos.offset(state.getValue(BlockBed.FACING).getOpposite());
        
        BlockBed bed = (BlockBed) block;
        bed.onWakeup(world, posFoot, (TEBed) world.getTileEntity(posFoot));
        
        if (GeoConfig.gameplay.food) {
            
            player.getCapability(GeoCaps.CAP_PLAYER, null)
                .sleep(bed.getHealAmount());
        }
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
                    
                    player.getActiveItemStack().damageItem(1 +
                            MathHelper.floor(event.getAmount()), player);
                }
            }
        }
    }
    
    /** Adds yoke and backpack to player death drops. */
    @SubscribeEvent
    public void playerDrops(PlayerDropsEvent event) {
        
        EntityPlayer player = event.getEntityPlayer();
        ICapPlayer capPlayer = player.getCapability(GeoCaps.CAP_PLAYER, null);
        World world = player.world;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        List<EntityItem> drops = event.getDrops();
        drops.add(new EntityItem(world, x, y, z, capPlayer.removeBackpack()));
        drops.add(new EntityItem(world, x, y, z, capPlayer.removeYoke()));
    }
    
    
    @SubscribeEvent
    public void playerLogin(PlayerLoggedInEvent event) {
        
        if (event.player instanceof EntityPlayerMP) {
            
            Geomastery.NETWORK.sendTo(new CPacketConfig(),
                    (EntityPlayerMP) event.player);
        }
    }
}
