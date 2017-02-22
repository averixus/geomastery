package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.DefaultCapPlayer;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.utilities.TempStage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the player temperature icon on the Client. */
public class TempPacketClient implements IMessage {
    
    /** The temperature stage. */
    protected TempStage stage;
    
    public TempPacketClient() {}
    
    public TempPacketClient(TempStage stage) {
        
        this.stage = stage;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.stage = TempStage.values()[buf.readInt()];
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.stage.ordinal());
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
            ((DefaultCapPlayer) player.getCapability(ModCaps
                    .CAP_PLAYER, null)).processTempMessage(message.stage);
        }
    }
}
