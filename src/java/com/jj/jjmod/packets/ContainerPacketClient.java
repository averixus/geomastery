package com.jj.jjmod.packets;

import com.jj.jjmod.utilities.InvLocation.InvType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the ContainerPlayer on the client. */
public class ContainerPacketClient implements IMessage {

    protected int slot;
    protected ItemStack stack;

    public ContainerPacketClient() {}

    public ContainerPacketClient(int slot, ItemStack stack) {

        this.slot = slot;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
    }

    public static class Handler
            implements IMessageHandler<ContainerPacketClient, IMessage> {

        @Override
        public IMessage onMessage(ContainerPacketClient message,
                MessageContext ctx) {

            Minecraft.getMinecraft().addScheduledTask(new Runnable() {

                @Override
                public void run() {

                    processMessage(message);
                }
            });

            return null;
        }

        public void processMessage(ContainerPacketClient message) {

            EntityPlayer player = Minecraft.getMinecraft().player;
            player.inventoryContainer.inventorySlots.get(message.slot)
                    .putStack(message.stack);
        }
    }
}
