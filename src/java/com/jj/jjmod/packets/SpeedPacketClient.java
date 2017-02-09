package com.jj.jjmod.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpeedPacketClient implements IMessage {
    
    protected float speed;
    
    public SpeedPacketClient() {}
    
    public SpeedPacketClient(float speed) {
        
        this.speed = speed;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.speed = buf.readFloat();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeFloat(this.speed);
    }
    
    public static class Handler implements IMessageHandler<SpeedPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(SpeedPacketClient message, MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(SpeedPacketClient message) {
            
            EntityPlayer player = Minecraft.getMinecraft().player;
            player.capabilities.setPlayerWalkSpeed(message.speed);
        }
    }

}
