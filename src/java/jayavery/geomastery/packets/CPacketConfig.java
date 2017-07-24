/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEDrying;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** Packet to set the client's config values from those on the server. */
public class CPacketConfig implements IMessage {

    /** Hide vanilla from JEI. */
    protected boolean hideVanilla;
    /** Add vanilla crafting table recipe. */
    protected boolean addCrafting;
    /** Apply temperature damage. */
    protected boolean temperature;
    /** Apply speed effects. */
    protected boolean speed;
    /** Use food types. */
    protected boolean food;
    /** Apply inventory restrictions. */
    protected boolean inventory;
    
    public CPacketConfig() {
        
        this.hideVanilla = GeoConfig.compatibility.hideVanilla;
        this.addCrafting = GeoConfig.compatibility.addCrafting;
        this.temperature = GeoConfig.gameplay.temperature;
        this.speed = GeoConfig.gameplay.speed;
        this.food = GeoConfig.gameplay.food;
        this.inventory = GeoConfig.gameplay.inventory;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.hideVanilla = buf.readBoolean();
        this.addCrafting = buf.readBoolean();
        this.temperature = buf.readBoolean();
        this.speed = buf.readBoolean();
        this.food = buf.readBoolean();
        this.inventory = buf.readBoolean();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeBoolean(this.hideVanilla);
        buf.writeBoolean(this.addCrafting);
        buf.writeBoolean(this.temperature);
        buf.writeBoolean(this.speed);
        buf.writeBoolean(this.food);
        buf.writeBoolean(this.inventory);
    }
    
    public static class Handler implements IMessageHandler<CPacketConfig, IMessage> {

        @Override
        public IMessage onMessage(CPacketConfig message,
                MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketConfig message) {
            
            GeoConfig.compatibility.hideVanilla = message.hideVanilla;
            GeoConfig.compatibility.addCrafting = message.addCrafting;
            GeoConfig.gameplay.temperature = message.temperature;
            GeoConfig.gameplay.speed = message.speed;
            GeoConfig.gameplay.food = message.food;
            GeoConfig.gameplay.inventory = message.inventory;
        }
    }
}
