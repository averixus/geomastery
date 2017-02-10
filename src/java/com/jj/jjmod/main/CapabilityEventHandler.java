package com.jj.jjmod.main;

import com.jj.jjmod.capabilities.DefaultCapPlayer;
import com.jj.jjmod.capabilities.ProviderCapPlayer;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.items.ItemEdibleDecayable;
import com.jj.jjmod.utilities.FoodStatsWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/** Handler for capability related events. */
public class CapabilityEventHandler {
    
    /** Initial sync of ICapPlayer. */
    @SubscribeEvent
    public void playerJoin(EntityJoinWorldEvent event) {
       
        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();
            player.getCapability(ModCapabilities.CAP_PLAYER, null).syncAll();
        }
    }
    
    /** Attaches ICapPlayer to the Player. */
    @SubscribeEvent
    public void playerCapabilities(AttachCapabilitiesEvent<Entity> event) {

        if (!(event.getObject() instanceof EntityPlayer)) {

            return;
        }

        EntityPlayer player = (EntityPlayer) event.getObject();
        
        if (!(player.hasCapability(ModCapabilities.CAP_PLAYER, null))) {

            event.addCapability(ModCapabilities.CAP_PLAYER_ID,
                    new ProviderCapPlayer(new DefaultCapPlayer(player)));
        }
    }
    
    /** Tick Capabilities and check additional player settings. */
    @SubscribeEvent
    public void playerTick(PlayerTickEvent event) {
        
        if (event.phase != Phase.START) {
            
            return;
        }
        
        event.player.getCapability(ModCapabilities.CAP_PLAYER, null).tick();
        
        checkContainer(event.player);
        checkFoodstats(event.player);
        inventoryDecay(event.player);
    }
    
    /** Sets the player container to correct for the gamemode. */
    private static void checkContainer(EntityPlayer player) {
        
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
    }
    
    /** Sets the player's FoodStats to FoodStatsWrapper. */
    private static void checkFoodstats(EntityPlayer player) {
        
        if (!(player.getFoodStats() instanceof FoodStatsWrapper)) {
            
            ReflectionHelper.setPrivateValue(EntityPlayer.class, player,
                    new FoodStatsWrapper(player), "foodStats");
        }
    }
    
    /** Ticks all ICapDecay items in the player inventory. */
    private static void inventoryDecay(EntityPlayer player) {
        
        if (player.inventoryContainer instanceof ContainerInventory) {
            
            for (Slot slot : player.inventoryContainer.inventorySlots) {
                
                ItemStack stack = slot.getStack();
                
                if (stack.getItem() instanceof ItemEdibleDecayable) {
                    
                    if (stack.getCapability(ModCapabilities.CAP_DECAY, null)
                            .updateAndRot()) {
                        
                        slot.putStack(new ItemStack(ModItems.rot));
                    }
                }
            }
        }
    }
}
