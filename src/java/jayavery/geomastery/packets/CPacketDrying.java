/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEDrying;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client drying rack data syncing. */
public class CPacketDrying implements IMessage {

    /** Dry each value of the drying rack. */
    protected int dryEach;
    /** Dry spent value of the drying rack. */
    protected int drySpent;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CPacketDrying() {}
    
    public CPacketDrying(int dryEach,
            int drySpent, BlockPos pos) {
        
        this.dryEach = dryEach;
        this.drySpent = drySpent;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.dryEach = buf.readInt();
        this.drySpent = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.dryEach);
        buf.writeInt(this.drySpent);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler implements IMessageHandler<CPacketDrying, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketDrying message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketDrying message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TEDrying) {
                
                TEDrying tileDrying = (TEDrying) tileEntity;
                tileDrying.setProgressBars(message.dryEach, message.drySpent);
            }
        }
    }
}
