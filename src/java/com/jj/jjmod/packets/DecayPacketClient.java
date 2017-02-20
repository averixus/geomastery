package com.jj.jjmod.packets;

import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DecayPacketClient implements IMessage {

    protected int slot;
    protected NBTTagCompound capabilityTags;
    
    public DecayPacketClient() {}
    
    public DecayPacketClient(int slot, NBTTagCompound capabilityTags) {
        
        this.slot = slot;
        this.capabilityTags = capabilityTags;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.slot = buf.readInt();
        this.capabilityTags = ByteBufUtils.readTag(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.slot);
        ByteBufUtils.writeTag(buf, this.capabilityTags);
    }
    
    public static class Handler implements IMessageHandler<DecayPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(DecayPacketClient message, MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(DecayPacketClient message) {
            
            EntityPlayer player = Minecraft.getMinecraft().player;
            Container container = player.openContainer;
            
            if (container instanceof ContainerPlayer && !player.capabilities.isCreativeMode) {
                System.out.println("forcing client side container to change quickly");
                player.inventoryContainer = new ContainerInventory(player, player.world);
                player.openContainer = player.inventoryContainer;
                container = player.openContainer;
            }
            
            ItemStack inSlot = container.inventorySlots.get(message.slot).getStack();
            System.out.println("processing message for stack " + inSlot + " slot index " + message.slot + " cap tags shold be " + message.capabilityTags + " in container " + container);
            if (inSlot.hasCapability(ModCapabilities.CAP_DECAY, null)) {
                System.out.println("reading tags in process message");
                inSlot.getCapability(ModCapabilities.CAP_DECAY, null).deserializeNBT(message.capabilityTags);
            }
        }
    }
}
