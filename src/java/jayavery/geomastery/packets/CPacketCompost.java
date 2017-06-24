/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TECompost;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet for server->client compost heap data syncing. */
public class CPacketCompost implements IMessage {
    
    /** Input fullness of the compost heap. */
    protected int input;
    /** Ticks spent on current input. */
    protected int compostSpent;
    /** Compost balance of the heap. */
    protected int balance;
    /** Current output stack. */
    protected ItemStack output;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CPacketCompost() {}
    
    public CPacketCompost(int input, int compostSpent, int balance,
            ItemStack output, BlockPos pos) {
        
        this.input = input;
        this.compostSpent = compostSpent;
        this.balance = balance;
        this.output = output;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.input = buf.readInt();
        this.compostSpent = buf.readInt();
        this.balance = buf.readInt();
        this.output = ByteBufUtils.readItemStack(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.input);
        buf.writeInt(this.compostSpent);
        buf.writeInt(this.balance);
        ByteBufUtils.writeItemStack(buf, this.output);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler
            implements IMessageHandler<CPacketCompost, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketCompost message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketCompost message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new BlockPos(message.x,
                    message.y, message.z));
            
            if (tileEntity instanceof TECompost) {
                
                TECompost tileCompost = (TECompost) tileEntity;
                tileCompost.setValues(message.input, message.compostSpent,
                        message.balance, message.output);
            }
        }
    }
}
