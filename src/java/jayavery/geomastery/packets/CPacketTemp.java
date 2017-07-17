/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.capabilities.DefaultCapPlayer;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.ETempStage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client player temperature syncing. */
public class CPacketTemp implements IMessage {
    
    /** The temperature stage. */
    protected ETempStage stage;
    
    public CPacketTemp() {}
    
    public CPacketTemp(ETempStage stage) {
        
        this.stage = stage;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.stage = ETempStage.values()[buf.readInt()];
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.stage.ordinal());
    }
    
    public static class Handler
            implements IMessageHandler<CPacketTemp, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketTemp message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketTemp message) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            ((DefaultCapPlayer) player.getCapability(GeoCaps
                    .CAP_PLAYER, null)).processTempPacket(message.stage);
        }
    }
}
