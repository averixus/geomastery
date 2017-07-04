/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client player inventory slot syncing. */
public class CPacketContainer implements IMessage {

    /** Index of the slot in the container. */
    protected int slot;
    /** The stack in the index slot. */
    protected ItemStack stack;
    /** The birth time of the decay capablity if applicable. */
    protected long birthTime;

    public CPacketContainer() {}

    public CPacketContainer(int slot, ItemStack stack) {

        this.slot = slot;
        this.stack = stack;
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            this.birthTime = stack.getCapability(GeoCaps.CAP_DECAY,
                    null).getBirthTime();
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
            implements IMessageHandler<CPacketContainer, IMessage> {

        @Override
        public IMessage onMessage(CPacketContainer message,
                MessageContext ctx) {

            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }

        public void processMessage(CPacketContainer message) {

            EntityPlayer player = Geomastery.proxy.getClientPlayer();
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
