/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEBeam;
import jayavery.geomastery.tileentities.TEBeam.EnumFloor;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update changed beam floors on the Client. */
public class CPacketFloor implements IMessage {
    
    /** The floor type of the beam. */
    protected EnumFloor floor;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CPacketFloor() {}
    
    public CPacketFloor(EnumFloor floor, BlockPos pos) {
        
        this.floor = floor;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.floor = EnumFloor.values()[buf.readInt()];
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.floor.ordinal());
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler
            implements IMessageHandler<CPacketFloor, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketFloor message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketFloor message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TEBeam) {
                
                TEBeam tileBeam = (TEBeam) tileEntity;
                tileBeam.applyFloor(message.floor);
                world.markBlockRangeForRenderUpdate(message.x, message.y,
                        message.z, message.x + 1,
                        message.y + 1, message.z + 1);
            }
        }
    }
}
