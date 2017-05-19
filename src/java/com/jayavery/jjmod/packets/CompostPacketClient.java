package com.jayavery.jjmod.packets;

import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECompost;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CompostPacketClient implements IMessage {
    
    /** Input fullness of the compost heap. */
    protected int input;
    /** Stack size of output. */
    protected int output;
    /** Compost balance of the heap. */
    protected int balance;
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    
    public CompostPacketClient() {}
    
    public CompostPacketClient(int input, int output,
            int balance, BlockPos pos) {
        
        this.input = input;
        this.output = output;
        this.balance = balance;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.input = buf.readInt();
        this.output = buf.readInt();
        this.balance = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.input);
        buf.writeInt(this.output);
        buf.writeInt(this.balance);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static class Handler
    implements IMessageHandler<CompostPacketClient, IMessage> {
        
        @Override
        public IMessage onMessage(CompostPacketClient message,
                MessageContext ctx) {
            
            Jjmod.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CompostPacketClient message) {
            
            World world = Jjmod.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new BlockPos(message.x,
                    message.y, message.z));
            
            if (tileEntity instanceof TECompost) {
                
                TECompost tileCompost = (TECompost) tileEntity;
                tileCompost.setValues(message.input,
                        message.output, message.balance);
            }
        }
    }
}
