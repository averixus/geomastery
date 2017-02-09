package com.jj.jjmod.packets;

import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.utilities.InvLocation.InvType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ContainerPacketServer implements IMessage {

  //  protected InvType type;
    protected int slot;
    protected ItemStack stack;

    public ContainerPacketServer() {}

    public ContainerPacketServer(int slot, ItemStack stack) {

     //   this.type = type;
        this.slot = slot;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

    //    this.type = InvType.values()[buf.readInt()];
        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

    //    buf.writeInt(this.type.ordinal());
        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
    }

    public static class Handler
            implements IMessageHandler<ContainerPacketServer, IMessage> {

        @Override
        public IMessage onMessage(ContainerPacketServer message,
                MessageContext ctx) {

            ctx.getServerHandler().playerEntity.getServer()
                    .addScheduledTask(new Runnable() {

                        @Override
                        public void run() {

                            processMessage(message, ctx);
                        }
                    });

            return null;
        }

        public void processMessage(ContainerPacketServer message,
                MessageContext ctx) {

            EntityPlayer player = ctx.getServerHandler().playerEntity;
            player.inventoryContainer.inventorySlots.get(message.slot).putStack(message.stack);
         //   inv.setStack(message.type, message.slot, message.stack);
        }
    }
}
