package com.jj.jjmod.packets;

import com.jj.jjmod.tileentities.TEBeam;
import com.jj.jjmod.tileentities.TEBeam.EnumFloor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FloorUpdateClient implements IMessage {
    
    private EnumFloor floor;
    private int x;
    private int y;
    private int z;
    
    public FloorUpdateClient() {}
    
    public FloorUpdateClient(EnumFloor floor, BlockPos pos) {
        
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
    
    public static class Handler implements IMessageHandler<FloorUpdateClient, IMessage> {
        
        @Override
        public IMessage onMessage(FloorUpdateClient message, MessageContext ctx) {
            
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                
                @Override
                public void run() {
                    
                    processMessage(message);
                }
            });
            
            return null;
        }
        
        public void processMessage(FloorUpdateClient message) {
            
            World world = Minecraft.getMinecraft().theWorld;
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TEBeam) {
                
                TEBeam tileBeam = (TEBeam) tileEntity;
                tileBeam.applyFloor(message.floor);
                Minecraft.getMinecraft().theWorld
                        .markBlockRangeForRenderUpdate(message.x, message.y,
                        message.z, message.x + 1,
                        message.y + 1, message.z + 1);
            }
        }
    }
}
