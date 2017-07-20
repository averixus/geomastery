/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the furnace progress bars on the Client. */
public class CPacketFurnace implements IMessage {

    /** Fuel left value of the furnace. */
    protected int fuelLeft;
    /** Fuel each value of the furnace. */
    protected int fuelEach;
    /** Cook spent value of the furnace. */
    protected int cookSpent;
    /** Cook each value of the furnace. */
    protected int cookEach;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-odinate. */
    protected int z;
    
    public CPacketFurnace() {}
    
    public CPacketFurnace(int fuelLeft, int fuelEach,
            int cookSpent, int cookEach, BlockPos pos) {
        
        this.fuelLeft = fuelLeft;
        this.fuelEach = fuelEach;
        this.cookSpent = cookSpent;
        this.cookEach = cookEach;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.fuelLeft = buf.readInt();
        this.fuelEach = buf.readInt();
        this.cookSpent = buf.readInt();
        this.cookEach = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.fuelLeft);
        buf.writeInt(this.fuelEach);
        buf.writeInt(this.cookSpent);
        buf.writeInt(this.cookEach);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler implements
            IMessageHandler<CPacketFurnace, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketFurnace message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() ->
                    this.processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketFurnace message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new BlockPos(message.x,
                    message.y, message.z));
            
            if (tileEntity instanceof TEFurnaceAbstract) {
                
                TEFurnaceAbstract<?> tileFurnace = (TEFurnaceAbstract<?>)
                        tileEntity;
                tileFurnace.setProgressBars(message.fuelLeft,
                        message.fuelEach, message.cookSpent, message.cookEach);
            }
        }
    }
}
