package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.CapFoodstats;
import com.jj.jjmod.capabilities.DefaultCapFoodstats;
import com.jj.jjmod.items.ItemEdible.FoodType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FoodUpdateClient implements IMessage {
    
    protected int type;
    protected int hunger;
    
    public FoodUpdateClient() {}
    
    public FoodUpdateClient(FoodType type, int hunger) {
        
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
            implements IMessageHandler<FoodUpdateClient, IMessage> {
        
        @Override
        public IMessage onMessage(FoodUpdateClient message,
                MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(FoodUpdateClient message) {
            
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            ((DefaultCapFoodstats) player
                    .getCapability(CapFoodstats.CAP_FOODSTATS, null))
                    .processMessage(FoodType.values()[message.type],
                    message.hunger);
        }
    }

}
