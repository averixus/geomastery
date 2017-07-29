/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockStumpTest;
import jayavery.geomastery.blocks.BlockTree;
import jayavery.geomastery.blocks.BlockTreeTest;
import jayavery.geomastery.blocks.BlockTrunkTest;
import jayavery.geomastery.blocks.BlockTrunkTest.ETrunkAxis;
import jayavery.geomastery.entities.FallingTreeBlock;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketTrunkAngle;
import jayavery.geomastery.packets.CPacketTrunkBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

// TEST
public class TEStump extends TileEntity implements ITickable {

    /** Array of all possible offset BlockPos for tree trunks. */
    private static final BlockPos[] TRUNK_OFFSETS = {new BlockPos(-1, 0, -1), new BlockPos(-1, 1, -1), new BlockPos(-1, 0, 0), new BlockPos(-1, 1, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 1, 1), new BlockPos(0, 0, -1), new BlockPos(0, 1, -1), new BlockPos(1, 1, 1), new BlockPos(0, 1, 0), new BlockPos(0, 0, 1), new BlockPos(0, 1, 1), new BlockPos(1, 0, -1), new BlockPos(1, 1, -1), new BlockPos(1, 0, 0), new BlockPos(1, 1, 0), new BlockPos(1, 0, 1)};
    
    /** Upper limit for number of blocks which can be felled in one action. */
    private static final int HARD_MAX = 150;
    
    public float angle;
    public float prevAngle;
    public Map<BlockPos, IBlockState> blocks = Maps.newHashMap();
    
    @Override
    public void update() {
        

        if (this.world.isRemote) {

            return;
        }
        
        this.prevAngle = this.angle;
        this.angle = Math.min(this.angle * 1.1F, 90);

        for (BlockPos pos : this.blocks.keySet()) {
            
            this.world.setBlockToAir(pos);
        }
        
        if (this.angle >= 90) {
                        
          //  EnumFacing fall = this.world.getBlockState(this.pos).getValue(BlockFacing.FACING);
            System.out.println("placing fallen trunk blocks " + this);
            for (Entry<BlockPos, IBlockState> entry : this.blocks.entrySet()) {
                
                BlockPos offset = entry.getKey().subtract(this.pos);
                int x = offset.getX();
                int y = offset.getY();
                int z = offset.getZ();
                
                BlockPos rotated = new BlockPos(y + 1, -x, z);
                BlockPos result = rotated.add(this.pos);                
                IBlockState state = entry.getValue();
                Block block = state.getBlock();
                
                if (block instanceof BlockTree && ((BlockTree) block).hasFallen()) {
                    
                    state = ((BlockTree) block).getFallen().getDefaultState().withProperty(BlockTrunkTest.AXIS, ETrunkAxis.X_AXIS);
                }
                
                this.world.setBlockState(result, state);
            }
            System.out.println("setting trunk not falling " + this);
            IBlockState thisState = this.world.getBlockState(this.pos);
            this.world.setBlockState(this.pos, thisState.withProperty(BlockTree.FALLING, false));
        }

        Geomastery.NETWORK.sendToAll(new CPacketTrunkAngle(this.angle,
                this.prevAngle, this.pos));
    }
    
    public void fall(EnumFacing direction) {

        IBlockState thisState = this.world.getBlockState(this.pos);
        System.out.println("fall event " + this + " state is " + thisState);
        this.blocks = Maps.newHashMap();
        

            
        //    Thread thread = new Thread(() -> {
                
                BlockPos origin = this.pos.up();
            
                List<FallingTreeBlock> toFall = Lists.newArrayList();
                Set<BlockPos> checked = Sets.newHashSet();
                Queue<BlockPos> trunkQueue = new LinkedList<BlockPos>();
                Queue<BlockPos> leafQueue = new LinkedList<BlockPos>();
    
                if (this.world.getBlockState(origin).getBlock() instanceof BlockTree) {
    
                    trunkQueue.add(origin);
    
                } else {
    
                    leafQueue.add(origin);
                }
    
                while (!trunkQueue.isEmpty()) {
    
                    BlockPos nextPos = trunkQueue.remove();
                    IBlockState nextState = this.world.getBlockState(nextPos);
                    Block nextBlock = nextState.getBlock();
                    checked.add(nextPos);
    
                    if (nextBlock instanceof BlockTree) {
    
                        this.blocks.put(nextPos, nextState.getActualState(this.world, nextPos));
    
                        for (BlockPos offset : TRUNK_OFFSETS) {
    
                            BlockPos toAdd = nextPos.add(offset);
    
                            if (!checked.contains(toAdd) && !trunkQueue.contains(toAdd)) {
    
                                trunkQueue.add(toAdd);
                            }
                        }
    
                    } else if (nextBlock instanceof BlockLeaves) {
    
                        leafQueue.add(nextPos);
                    }
                }
    
                int max = Math.min(HARD_MAX, toFall.size() * 10);
    
                while (!leafQueue.isEmpty() && toFall.size() <= max) {
    
                    BlockPos nextPos = leafQueue.remove();
                    IBlockState nextState = this.world.getBlockState(nextPos);
                    Block nextBlock = nextState.getBlock();
                    checked.add(nextPos);
    
                    if (nextBlock instanceof BlockLeaves) {
    
                        this.blocks.put(nextPos, nextState);
    
                        for (EnumFacing facing : EnumFacing.VALUES) {
    
                            BlockPos toAdd = nextPos.offset(facing);

                            if (!checked.contains(toAdd) && !leafQueue.contains(toAdd)) {
    
                                leafQueue.add(toAdd);
                            }
                        }
                    }
                }
System.out.println("setting trunk falling " + this);
                this.angle = 0.1F;
              //  this.world.setBlockState(this.pos, thisState.withProperty(BlockStump.FALLING, true));
                System.out.println("sending blocks " + this.blocks);
                Geomastery.NETWORK.sendToAll(new CPacketTrunkBlocks(this.pos, this.blocks));
         //   });
            
       //     thread.setDaemon(true);
         //   thread.setPriority(Thread.MIN_PRIORITY);
        //    thread.start();
    }
    
    public void setState(float angle, float prevAngle) {

        this.angle = angle;
        this.prevAngle = prevAngle;
    }
    
    public void setBlocks(Map<BlockPos, IBlockState> blocks) {
        
        this.blocks = blocks;
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        
        return new AxisAlignedBB(this.getPos().add(-5, -5, -5), this.getPos().add(5, 5, 5));
    }
}
