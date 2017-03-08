package com.jayavery.jjmod.packets;

import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.main.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BackpackPacketClient implements IMessage {

    /** The stakc in the backpack slot. */
    protected ItemStack stack;
    
    public BackpackPacketClient() {}
    
    public BackpackPacketClient(ItemStack stack) {
        
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
            IMessageHandler<BackpackPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(BackpackPacketClient message,
                MessageContext ctx) {
            
            Main.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(BackpackPacketClient message) {
            
            EntityPlayer player = Main.proxy.getClientPlayer();
            
            if (player.hasCapability(ModCaps.CAP_PLAYER, null)) {
                
                player.getCapability(ModCaps.CAP_PLAYER, null)
                        .putBackpack(message.stack);
            }
        }
    }
}
