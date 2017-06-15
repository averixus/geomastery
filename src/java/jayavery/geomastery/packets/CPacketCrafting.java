/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TECraftingAbstract;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the crafting device durability bar on the Client. */
public class CPacketCrafting implements IMessage {

    /** Durability of this crafting device. */
    protected int durability;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CPacketCrafting() {}
    
    public CPacketCrafting(int durability, BlockPos pos) {
        
        this.durability = durability;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.durability = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.durability);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler implements
            IMessageHandler<CPacketCrafting, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketCrafting message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() ->
                    this.processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketCrafting message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new BlockPos(message.x,
                    message.y, message.z));
            
            if (tileEntity instanceof TECraftingAbstract<?>) {
                
                TECraftingAbstract<?> tileCrafting =
                        (TECraftingAbstract<?>) tileEntity;
                tileCrafting.setDurability(message.durability);
            }
        }
    }
}
