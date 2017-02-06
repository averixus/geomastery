package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.CapTemperature;
import com.jj.jjmod.capabilities.DefaultCapTemperature;
import com.jj.jjmod.capabilities.DefaultCapTemperature.EnumTempIcon;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TemperatureUpdateClient implements IMessage {
    
    protected int icon;
    
    public TemperatureUpdateClient() {}
    
    public TemperatureUpdateClient(int icon) {
        
        this.icon = icon;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.icon = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.icon);
    }
    
    public static class Handler implements IMessageHandler<TemperatureUpdateClient, IMessage> {
        
        @Override
        public IMessage onMessage(TemperatureUpdateClient message, MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(TemperatureUpdateClient message) {
            
            EntityPlayer player = Minecraft.getMinecraft().player;
            player.getCapability(CapTemperature.CAP_TEMPERATURE, null).processMessage(message.icon);
        }
    }
}
