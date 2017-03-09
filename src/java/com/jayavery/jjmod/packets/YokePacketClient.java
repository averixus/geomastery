package com.jayavery.jjmod.packets;

import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.main.Jjmod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class YokePacketClient implements IMessage {

    /** The stakc in the yoke slot. */
    protected ItemStack stack;
    
    public YokePacketClient() {}
    
    public YokePacketClient(ItemStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.stack = ByteBufUtils.readItemStack(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        ByteBufUtils.writeItemStack(buf, this.stack);
    }
    
    public static class Handler implements
            IMessageHandler<YokePacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(YokePacketClient message,
                MessageContext ctx) {
            
            Jjmod.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(YokePacketClient message) {
            
            EntityPlayer player = Jjmod.proxy.getClientPlayer();
            
            if (player.hasCapability(ModCaps.CAP_PLAYER, null)) {
                
                player.getCapability(ModCaps.CAP_PLAYER, null)
                        .putYoke(message.stack);
            }
        }
    }
}
