/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.packets;

import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TETrunk;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TEST
/** Packet for server->client box lid angle syncing. */
public class CPacketTrunkBlocks implements IMessage {
    
    /** X co-ordinate. */
    protected int x;
    /** Y co-ordinate. */
    protected int y;
    /** Z co-ordinate. */
    protected int z;
    /** Blocks map. */
    protected Map<BlockPos, IBlockState> blocks;
    
    public CPacketTrunkBlocks() {}
    
    public CPacketTrunkBlocks(BlockPos pos, Map<BlockPos, IBlockState> blocks) {
        
        this.blocks = blocks;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.blocks = Maps.newHashMap();
        int length = buf.readInt();
        
        for (int i = 0; i < length; i++) {
            
            int x = buf.readInt();
            int y = buf.readInt();
            int z = buf.readInt();
            int blockId = buf.readInt();
            int blockMeta = buf.readInt();
            BlockPos pos = new BlockPos(x, y, z);
            IBlockState state = Block.getBlockById(blockId).getStateFromMeta(blockMeta);
            this.blocks.put(pos, state);
        }
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.blocks.size());
        
        for (Entry<BlockPos, IBlockState> entry : this.blocks.entrySet()) {
            
            BlockPos pos = entry.getKey();
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
            Block block = entry.getValue().getBlock();
            buf.writeInt(Block.getIdFromBlock(block));
            buf.writeInt(block.getMetaFromState(entry.getValue()));
        }
    }
    
    public static class Handler implements IMessageHandler<CPacketTrunkBlocks, IMessage> {
        
        @Override
        public IMessage onMessage(CPacketTrunkBlocks message, MessageContext ctx) {
            
            Geomastery.proxy.addClientRunnable(() -> processMessage(message));
            return null;
        }
        
        public void processMessage(CPacketTrunkBlocks message) {
            
            World world = Geomastery.proxy.getClientWorld();
            TileEntity tileEntity = world.getTileEntity(new
                    BlockPos(message.x, message.y, message.z));
            
            if (tileEntity instanceof TETrunk) {
                
                TETrunk tileTrunk = (TETrunk) tileEntity;
                tileTrunk.setBlocks(message.blocks);
            }
        }
    }
}
