package com.jj.jjmod.packets;

import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.utilities.InvLocation.InvType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the ContainerInventory on the client. */
public class ContainerPacketClient implements IMessage {

    /** Index of the slot in the container. */
    protected int slot;
    /** The stack in the index slot. */
    protected ItemStack stack;
    /** The birth time of the decay capablity if applicable. */
    protected long birthTime;

    public ContainerPacketClient() {}

    public ContainerPacketClient(int slot, ItemStack stack) {

        this.slot = slot;
        this.stack = stack;
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
            
            this.birthTime = stack.getCapability(ModCaps.CAP_DECAY,
                    null).getBirthTime();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
        this.birthTime = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
        buf.writeLong(this.birthTime);
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
            ItemStack stack = message.stack;
            
            if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
                
                stack.getCapability(ModCaps.CAP_DECAY, null)
                .setBirthTime(message.birthTime);
            }
            System.out.println("processing container update, slot " + message.slot + " stack " + stack);
            player.inventoryContainer.inventorySlots.get(message.slot)
                    .putStack(stack);
        }
    }
}
