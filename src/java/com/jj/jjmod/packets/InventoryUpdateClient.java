package com.jj.jjmod.packets;

import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.container.ContainerInventory.InvType;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class InventoryUpdateClient implements IMessage {

    private InvType type;
    private int slot;
    private ItemStack stack;

    public InventoryUpdateClient() {}

    public InventoryUpdateClient(InvType type, int slot, ItemStack stack) {

        this.type = type;
        this.slot = slot;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.type = InvType.values()[buf.readInt()];
        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.type.ordinal());
        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
    }

    public static class Handler
            implements IMessageHandler<InventoryUpdateClient, IMessage> {

        @Override
        public IMessage onMessage(InventoryUpdateClient message,
                MessageContext ctx) {

            Minecraft.getMinecraft().addScheduledTask(new Runnable() {

                public void run() {

                    processMessage(message);
                }
            });

            return null;
        }

        public void processMessage(InventoryUpdateClient message) {

            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            ContainerInventory inv =
                    (ContainerInventory) player.inventoryContainer;
            inv.setStack(message.type, message.slot, message.stack);
        }
    }
}
