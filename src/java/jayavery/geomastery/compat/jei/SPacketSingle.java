/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to transfer one slot in filling containers. */
public class SPacketSingle implements IMessage {
    
    /** The input slot to move into. */
    int space;
    /** The inventory slot to take from. */
    int input;
    /** Whether to transfer the whole stack. */
    boolean max;
    
    public SPacketSingle() {}
    
    public SPacketSingle(int space, int input, boolean max) {
        
        this.space = space;
        this.input = input;
        this.max = max;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.space = buf.readInt();
        this.input = buf.readInt();
        this.max = buf.readBoolean();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.space);
        buf.writeInt(this.input);
        buf.writeBoolean(this.max);
    }
    
    public static class Handler implements
            IMessageHandler<SPacketSingle, IMessage> {
        
        @Override
        public IMessage onMessage(SPacketSingle message,
                MessageContext ctx) {
            
            ctx.getServerHandler().playerEntity.getServer()
                    .addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }
        
        public void processMessage(SPacketSingle message,
                MessageContext ctx) {
            
            Container container = ctx.getServerHandler()
                    .playerEntity.openContainer;
            Slot space = container.getSlot(message.space);
            Slot input = container.getSlot(message.input);
            
            if (message.max) {
                
                space.putStack(input.getStack().copy());
                input.decrStackSize(Integer.MAX_VALUE);
                
            } else {
                
                ItemStack newIn = input.getStack().copy();
                newIn.setCount(1);
                space.putStack(newIn);
                input.decrStackSize(1);
            }
        }
    }
}