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

/** Packet for server->client player backpack slot syncing. */
public class CPacketBackpack implements IMessage {

    /** The stakc in the backpack slot. */
    protected ItemStack stack;
    
    public CPacketBackpack() {}
    
    public CPacketBackpack(ItemStack stack) {
        
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
            IMessageHandler<CPacketBackpack, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketBackpack message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketBackpack message) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            
            if (player.hasCapability(GeoCaps.CAP_PLAYER, null)) {
                
                player.getCapability(GeoCaps.CAP_PLAYER, null)
                        .putBackpack(message.stack);
            }
        }
    }
}
