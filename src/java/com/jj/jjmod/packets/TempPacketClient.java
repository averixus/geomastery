package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.DefaultCapPlayer;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.utilities.TempStage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the player temperature icon on the Client. */
public class TempPacketClient implements IMessage {
    
    protected int stage;
    
    public TempPacketClient() {}
    
    public TempPacketClient(TempStage stage) {
        
        this.stage = stage.ordinal();
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.stage = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.stage);
    }
    
    public static class Handler
    implements IMessageHandler<TempPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(TempPacketClient message,
                MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(TempPacketClient message) {
            
            EntityPlayer player = Minecraft.getMinecraft().player;
            ((DefaultCapPlayer) player.getCapability(ModCapabilities
                    .CAP_PLAYER, null))
                    .processTempMessage(TempStage.values()[message.stage]);
        }
    }
}
