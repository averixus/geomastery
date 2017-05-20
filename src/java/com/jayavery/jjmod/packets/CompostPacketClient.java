package com.jayavery.jjmod.packets;

import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECompost;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update compost heap values on the client. */
public class CompostPacketClient implements IMessage {
    
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
    
    public CompostPacketClient() {}
    
    public CompostPacketClient(int input, int compostSpent, int balance,
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
                tileCompost.setValues(message.input, message.compostSpent,
                        message.balance, message.output);
            }
        }
    }
}
