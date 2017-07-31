/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.container.ContainerInventory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for client->server instruction to replace player container. */
public class SPacketContainer implements IMessage {

    public SPacketContainer() {}
    
    @Override
    public void fromBytes(ByteBuf buf) {}
    
    @Override
    public void toBytes(ByteBuf buf) {}
    
    public static class Handler
            implements IMessageHandler<SPacketContainer, IMessage> {
        
        @Override
        public IMessage onMessage(SPacketContainer msg, MessageContext ctx) {
            
            ctx.getServerHandler().player.getServer()
                    .addScheduledTask(() -> setInventory(ctx));
            return null;
        }
        
        public void setInventory(MessageContext ctx) {
            
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.inventoryContainer = new ContainerInventory(player);
            player.openContainer = player.inventoryContainer;
            player.inventoryContainer.addListener(player);
            player.inventoryContainer.detectAndSendChanges();
        }
    }
}
