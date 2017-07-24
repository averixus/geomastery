/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TETrunk;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client box lid angle syncing. */
public class CPacketTrunkAngle implements IMessage {
    
    /** Angle of the lid. */
    protected float angle;
    /** Angle of the lid last tick. */
    protected float prevAngle;
    /** Whether the trunk is falling. */
    protected boolean isFalling;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CPacketTrunkAngle() {}
    
    public CPacketTrunkAngle(float angle, float prevAngle, boolean isFalling, BlockPos pos) {
        
        this.angle = angle;
        this.prevAngle = prevAngle;
        this.isFalling = isFalling;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.angle = buf.readFloat();
        this.prevAngle = buf.readFloat();
        this.isFalling = buf.readBoolean();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeFloat(this.angle);
        buf.writeFloat(this.prevAngle);
        buf.writeBoolean(this.isFalling);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler implements IMessageHandler<CPacketTrunkAngle, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketTrunkAngle message, MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketTrunkAngle message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TETrunk) {
                
                TETrunk tileTrunk = (TETrunk) tileEntity;
                tileTrunk.setState(message.angle, message.prevAngle, message.isFalling);
            }
        }
    }
}
