package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.packets.DecayPacketClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DecayContainerListener implements IContainerListener {
    
    private EntityPlayerMP player;
    
    public DecayContainerListener(EntityPlayerMP player) {
        
        this.player = player;
    }

    @Override
    public void sendSlotContents(Container container, int slot, ItemStack stack) {
        System.out.println("sending slot contents to listener, container " + container + " slot " + slot + " stack " + stack);
        if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
            System.out.println("sending packet");
            ModPackets.NETWORK.sendTo(new DecayPacketClient(slot, stack.getCapability(ModCapabilities.CAP_DECAY, null).serializeNBT()), this.player);
        }
        
    }
    
    @Override
    public void sendAllWindowProperties(Container container,
            IInventory inventory) {
        System.out.println("sending all packets, container " + container);
        for (Slot slot : container.inventorySlots) {
            
            ItemStack stack = slot.getStack();
            
            if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
                
                ModPackets.NETWORK.sendTo(new DecayPacketClient(slot.slotNumber, stack.getCapability(ModCapabilities.CAP_DECAY, null).serializeNBT()), this.player);
            }
        }
        
    }
    
    @Override
    public void updateCraftingInventory(Container containerToSend, NonNullList<
            ItemStack> itemsList) {

        // nothing
        
    }

    @Override
    public void sendProgressBarUpdate(Container containerIn, int varToUpdate,
            int newValue) {

        // nothing
        
    }



}
