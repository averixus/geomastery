package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.DefaultCapPlayer;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.utilities.FoodType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the player's Food levels on the Client. */
public class FoodPacketClient implements IMessage {
    
    /** The food type. */
    protected FoodType type;
    /** The hunger level. */
    protected int hunger;
    
    public FoodPacketClient() {}
    
    public FoodPacketClient(FoodType type, int hunger) {
        
        this.type = type;
        this.hunger = hunger;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.type = FoodType.values()[buf.readInt()];
        this.hunger = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.type.ordinal());
        buf.writeInt(this.hunger);
    }
    
    public static class Handler
            implements IMessageHandler<FoodPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(FoodPacketClient message,
                MessageContext ctx) {
            
            Main.proxy.addMinecraftRunnable(() -> processMessage(message));
            
            return null;
        }
        
        public void processMessage(FoodPacketClient message) {
            
            EntityPlayer player = Main.proxy.getClientPlayer();
            ((DefaultCapPlayer) player.getCapability(ModCaps.CAP_PLAYER, null))
                    .processFoodMessage(message.type, message.hunger);
        }
    }
}
