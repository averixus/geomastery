package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketYoke implements IMessage {

    /** The stakc in the yoke slot. */
    protected ItemStack stack;
    
    public CPacketYoke() {}
    
    public CPacketYoke(ItemStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.stack = ByteBufUtils.readItemStack(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        ByteBufUtils.writeItemStack(buf, this.stack);
    }
    
    public static class Handler implements
            IMessageHandler<CPacketYoke, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketYoke message,
                MessageContext ctx) {
            
            Geomastery.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketYoke message) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            
            if (player.hasCapability(GeoCaps.CAP_PLAYER, null)) {
                
                player.getCapability(GeoCaps.CAP_PLAYER, null)
                        .putYoke(message.stack);
            }
        }
    }
}
