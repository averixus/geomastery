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
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TEStump;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TEST
/** Packet for server->client trunk falling syncing. */
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
            
            BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            Block block = Block.getBlockById(buf.readInt());
            IBlockState state;

            if (block instanceof BlockTree) {
                
                state = ((BlockTree) block).deserialiseActualState(buf);
                
            } else {
                
                state = block.getStateFromMeta(buf.readInt());
            }
            
            this.blocks.put(pos, state);
        }
        
        System.out.println("reading message pos " + this.x + ", " + this.y + ", " + this.z);
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        
        System.out.println("writing message pos " + this.x + ", " + this.y + ", " + this.z);
        
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.blocks.size());
        
        for (Entry<BlockPos, IBlockState> entry : this.blocks.entrySet()) {
            
            BlockPos pos = entry.getKey();
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
            IBlockState state = entry.getValue();
            Block block = state.getBlock();
            buf.writeInt(Block.getIdFromBlock(block));
            
            if (block instanceof BlockTree) {
                
                ((BlockTree) block).serialiseActualState(state, buf);
                
            } else {
                
                buf.writeInt(block.getMetaFromState(state));
            }
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
            
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            
            IBlockState state = world.getBlockState(pos);
            
            if (((BlockTree) state.getBlock()).hasAlternate()) {
                
                state = ((BlockTree) state.getBlock()).getAlternateState(state);
                world.setBlockState(pos, state);
            }
            
      /*      if (!state.getValue(BlockStumpTest.FALLING)) {
                
                world.setBlockState(pos, state.withProperty(BlockStumpTest.FALLING, true));
            }*/
            System.out.println("block at message pos " + state);
            TileEntity tileEntity = world.getTileEntity(pos);
            System.out.println("processing message for te at " + pos + " " + tileEntity);
            if (tileEntity instanceof TEStump) {
                
                TEStump tileTrunk = (TEStump) tileEntity;
                tileTrunk.setBlocks(message.blocks);
            }
        }
    }
}
