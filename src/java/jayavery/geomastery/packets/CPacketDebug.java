/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import java.util.List;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import jayavery.geomastery.capabilities.DefaultCapPlayer;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client player debug info syncing. */
public class CPacketDebug implements IMessage {

    /** The list of information. */
    protected List<String> debug;
    
    public CPacketDebug() {}
    
    public CPacketDebug(List<String> debug) {
        
        this.debug = debug;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        int size = buf.readInt();
        this.debug = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            
            this.debug.add(ByteBufUtils.readUTF8String(buf));
        }
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.debug.size());
        for (int i = 0; i < this.debug.size(); i++) {
            
            ByteBufUtils.writeUTF8String(buf, this.debug.get(i));
        }
    }
    
    public static class Handler
            implements IMessageHandler<CPacketDebug, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketDebug message, MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketDebug message) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            ((DefaultCapPlayer) player.getCapability(GeoCaps.CAP_PLAYER, null))
                    .processDebugPacket(message.debug);
        }
    }
}
