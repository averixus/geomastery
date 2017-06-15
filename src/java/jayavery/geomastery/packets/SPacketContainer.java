/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.GeoCaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the ContainerInventory on the Server. */
public class SPacketContainer implements IMessage {

    /** Index of the slot in the container. */
    protected int slot;
    /** The stack in the index slot. */
    protected ItemStack stack;
    /** The birth time of the decay capability if applicable. */
    protected long birthTime;

    public SPacketContainer() {}

    public SPacketContainer(int slot, ItemStack stack) {

        this.slot = slot;
        this.stack = stack;
       
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            this.birthTime = stack.getCapability(GeoCaps.CAP_DECAY,
                    null).getBirthTime();
            
        } else {
            
            this.birthTime = 0L;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
        this.birthTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
        buf.writeLong(this.birthTime);
    }

    public static class Handler
            implements IMessageHandler<SPacketContainer, IMessage> {

        @Override
        public IMessage onMessage(SPacketContainer message,
                MessageContext ctx) {

            ctx.getServerHandler().playerEntity.getServer()
                    .addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        public void processMessage(SPacketContainer message,
                MessageContext ctx) {

            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = message.stack;
            
            if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
                
                stack.getCapability(GeoCaps.CAP_DECAY, null)
                        .setBirthTime(message.birthTime);
            }

            player.inventoryContainer.inventorySlots.get(message.slot)
                    .putStack(stack);
        }
    }
}
