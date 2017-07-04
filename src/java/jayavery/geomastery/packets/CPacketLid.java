/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client box lid angle syncing. */
public class CPacketLid implements IMessage {
    
    /** Angle of the lid. */
    protected float lidAngle;
    /** Angle of the lid last tick. */
    protected float prevLidAngle;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CPacketLid() {}
    
    public CPacketLid(float lidAngle, float prevLidAngle, BlockPos pos) {
        
        this.lidAngle = lidAngle;
        this.prevLidAngle = prevLidAngle;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.lidAngle = buf.readFloat();
        this.prevLidAngle = buf.readFloat();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeFloat(this.lidAngle);
        buf.writeFloat(this.prevLidAngle);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler
            implements IMessageHandler<CPacketLid, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketLid message, MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketLid message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TEStorage) {
                
                TEStorage tileBox = (TEStorage) tileEntity;
                tileBox.setAngles(message.lidAngle, message.prevLidAngle);
            }
        }
    }
}
