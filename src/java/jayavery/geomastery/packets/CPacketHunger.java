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
import jayavery.geomastery.utilities.FoodType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client player hunger syncing. */
public class CPacketHunger implements IMessage {
    
    /** The food type. */
    protected FoodType type;
    /** The hunger level. */
    protected int hunger;
    
    public CPacketHunger() {}
    
    public CPacketHunger(FoodType type, int hunger) {
        
        this.type = type;
        this.hunger = hunger;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.type = FoodType.values()[buf.readInt()];
        this.hunger = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.type.ordinal());
        buf.writeInt(this.hunger);
    }
    
    public static class Handler
            implements IMessageHandler<CPacketHunger, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketHunger message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketHunger message) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            ((DefaultCapPlayer) player.getCapability(GeoCaps.CAP_PLAYER, null))
                    .processFoodPacket(message.type, message.hunger);
        }
    }
}
