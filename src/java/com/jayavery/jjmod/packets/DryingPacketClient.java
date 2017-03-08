package com.jayavery.jjmod.packets;

import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.tileentities.TEDrying;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the drying rack progress bar on the Client. */
public class DryingPacketClient implements IMessage {

    /** Dry each value of the drying rack. */
    protected int dryEach;
    /** Dry spent value of the drying rack. */
    protected int drySpent;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** X co-ordinate. */
    protected int z;
    
    public DryingPacketClient() {}
    
    public DryingPacketClient(int dryEach,
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
    
    public static class Handler
            implements IMessageHandler<DryingPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(DryingPacketClient message,
                MessageContext ctx) {
            
            Main.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(DryingPacketClient message) {
            
            World world = Main.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TEDrying) {
                
                TEDrying tileDrying = (TEDrying) tileEntity;
                tileDrying.setProgressBars(message.dryEach, message.drySpent);
            }
        }
    }
}
