package jayavery.geomastery.main;

import java.util.List;
import jayavery.geomastery.blocks.BlockBed;
import jayavery.geomastery.blocks.BlockBed.EnumPartBed;
import jayavery.geomastery.capabilities.DefaultCapPlayer;
import jayavery.geomastery.capabilities.ICapPlayer;
import jayavery.geomastery.capabilities.ProviderCapPlayer;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.items.ItemShield;
import jayavery.geomastery.items.ItemSimple;
import jayavery.geomastery.tileentities.TEBed;
import jayavery.geomastery.utilities.FoodStatsWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
    
        if (!(player.getFoodStats() instanceof FoodStatsWrapper)) {
            
            ReflectionHelper.setPrivateValue(EntityPlayer.class, player,
                    new FoodStatsWrapper(player),
                    "foodStats", "field_71100_bB");
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

        if (player.getCapability(GeoCaps.CAP_PLAYER, null)
                .canPickup(item)) {
            
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
        
        ContainerInventory.updateHand(player, EnumHand.MAIN_HAND);
        player.getCapability(GeoCaps.CAP_PLAYER, null)
                .addDelay(item, time);
        
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
        
        BlockPos posFoot = state.getValue(BlockBed.PART) == EnumPartBed.FOOT ?
                pos : pos.offset(state.getValue(BlockBed.FACING).getOpposite());
        
        BlockBed bed = (BlockBed) block;
        bed.onWakeup(world, posFoot, (TEBed) world.getTileEntity(posFoot));
        player.getCapability(GeoCaps.CAP_PLAYER, null)
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
        ICapPlayer capPlayer = player.getCapability(GeoCaps.CAP_PLAYER, null);
        World world = player.world;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        List<EntityItem> drops = event.getDrops();
        drops.add(new EntityItem(world, x, y, z, capPlayer.removeBackpack()));
        drops.add(new EntityItem(world, x, y, z, capPlayer.removeYoke()));
    }
}
