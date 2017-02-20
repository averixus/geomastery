package com.jj.jjmod.packets;

import com.jj.jjmod.init.ModCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DecayPacketServerAsk implements IMessage {
    
    protected int slot;
    
    public DecayPacketServerAsk() {}
    
    public DecayPacketServerAsk(int slot) {
        
        this.slot = slot;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.slot = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.slot);
    }
    
    public static class Handler implements IMessageHandler<DecayPacketServerAsk, IMessage> {
        
        @Override
        public IMessage onMessage(DecayPacketServerAsk message, MessageContext ctx) {
            System.out.println("receiving server ask packet");
            ItemStack stack = ctx.getServerHandler().playerEntity.openContainer.inventorySlots.get(message.slot).getStack();
            System.out.println("stack is " + stack);
            if (!stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
                System.out.println("doesn't have cap");
                return null;
            }
            System.out.println("has cap, sending client packet back");
            return new DecayPacketClient(message.slot, stack.getCapability(ModCapabilities.CAP_DECAY, null).serializeNBT());
        }
    }
}
