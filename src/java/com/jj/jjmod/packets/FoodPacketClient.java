package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.CapPlayer;
import com.jj.jjmod.utilities.FoodType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FoodPacketClient implements IMessage {
    
    protected int type;
    protected int hunger;
    
    public FoodPacketClient() {}
    
    public FoodPacketClient(FoodType type, int hunger) {
        
        this.type = type.ordinal();
        this.hunger = hunger;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.type = buf.readInt();
        this.hunger = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.type);
        buf.writeInt(this.hunger);
    }
    
    public static class Handler
            implements IMessageHandler<FoodPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(FoodPacketClient message,
                MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(FoodPacketClient message) {
            
            EntityPlayer player = Minecraft.getMinecraft().player;
            player.getCapability(CapPlayer.CAP_PLAYER, null)
                    .processFoodMessage(FoodType.values()[message.type],
                    message.hunger);
        }
    }

}
