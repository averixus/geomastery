package com.jayavery.jjmod.packets;

import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TEBox;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the box lid angle on the Client. */
public class BoxPacketClient implements IMessage {
    
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
    
    public BoxPacketClient() {}
    
    public BoxPacketClient(float lidAngle, float prevLidAngle, BlockPos pos) {
        
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
            implements IMessageHandler<BoxPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(BoxPacketClient message, MessageContext ctx) {
            
            Jjmod.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(BoxPacketClient message) {
            
            World world = Jjmod.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TEBox) {
                
                TEBox tileBox = (TEBox) tileEntity;
                tileBox.setAngles(message.lidAngle, message.prevLidAngle);
            }
        }
    }
}
