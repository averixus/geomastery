package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.capabilities.DefaultCapPlayer;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.FoodType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to update the player's Food levels on the Client. */
public class CPacketFood implements IMessage {
    
    /** The food type. */
    protected FoodType type;
    /** The hunger level. */
    protected int hunger;
    
    public CPacketFood() {}
    
    public CPacketFood(FoodType type, int hunger) {
        
        this.type = type;
        this.hunger = hunger;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.type = FoodType.values()[buf.readInt()];
        this.hunger = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.type.ordinal());
        buf.writeInt(this.hunger);
    }
    
    public static class Handler
            implements IMessageHandler<CPacketFood, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketFood message,
                MessageContext ctx) {
            
            Geomastery.proxy.addMinecraftRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketFood message) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            ((DefaultCapPlayer) player.getCapability(GeoCaps.CAP_PLAYER, null))
                    .processFoodPacket(message.type, message.hunger);
        }
    }
}
