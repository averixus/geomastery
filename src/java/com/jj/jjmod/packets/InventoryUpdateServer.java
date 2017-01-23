package com.jj.jjmod.packets;

import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.DefaultCapInventory;
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

public class InventoryUpdateServer implements IMessage {

    protected InvType type;
    protected int slot;
    protected ItemStack stack;

    public InventoryUpdateServer() {}

    public InventoryUpdateServer(InvType type, int slot, ItemStack stack) {

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
            implements IMessageHandler<InventoryUpdateServer, IMessage> {

        @Override
        public IMessage onMessage(InventoryUpdateServer message,
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

        public void processMessage(InventoryUpdateServer message,
                MessageContext ctx) {

            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ContainerInventory inv =
                    (ContainerInventory) player.inventoryContainer;
            inv.setStack(message.type, message.slot, message.stack);
        }
    }
}
